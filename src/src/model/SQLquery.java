package model;

import controller.LoginSystem;
import model.Client;
import model.DatabaseConnection;

import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLquery {
    private static Connection bankConnection = DatabaseConnection.getConnection();

    public static String getPassword(String match) {

        String accountInfoStatement = "SELECT password from Bank WHERE id = ?";

        try {
            PreparedStatement preparedStatement = bankConnection.prepareStatement(accountInfoStatement);

            preparedStatement.setString(1, match);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isClosed()) {
                return null;
            } else {
                resultSet.next();
                return resultSet.getString("password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getAccount(String id) {

        String accountInfoStatement = "SELECT account_number from Bank WHERE id = ?";

        try {
            PreparedStatement preparedStatement = bankConnection.prepareStatement(accountInfoStatement);

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return resultSet.getInt("account_number");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Client selectCustomer(Integer login) {
        try {
            String selectCustomers = "SELECT * FROM Bank WHERE account_number = ?";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(selectCustomers);

            preparedStatement.setInt(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return new Client(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getInt(5), resultSet.getBigDecimal(6));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void addTransaction(BigDecimal amount, String operation, Integer account_number) {

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);

        String transaction = "INSERT INTO Transactions (account_number,tran_type, tran_value, tran_date ) values(?,?,?,?)";


        try {

            PreparedStatement preparedStatementTransaction = bankConnection.prepareStatement(transaction);

            preparedStatementTransaction.setInt(1, account_number);
            preparedStatementTransaction.setString(2, operation);
            preparedStatementTransaction.setBigDecimal(3, amount);
            preparedStatementTransaction.setString(4, currentTime);

            preparedStatementTransaction.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static void addClient(String firstName, String lastName, String id, String password, BigDecimal balance) {
        if (doesClientExist(id)) return;

        try {
            String createClientStatement = "INSERT INTO Bank (first_name, last_name, id, password, balance) values (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatementClient = bankConnection.prepareStatement(createClientStatement)) {
                preparedStatementClient.setString(1, firstName);
                preparedStatementClient.setString(2, lastName);
                preparedStatementClient.setString(3, id);
                preparedStatementClient.setString(4, LoginSystem.getMd5(password));
                preparedStatementClient.setBigDecimal(5, balance);

                preparedStatementClient.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean doesClientExist(String id) {
        try {
            String checkClient = "SELECT id FROM Bank";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(checkClient);

            ResultSet checkResultSet = preparedStatement.executeQuery();

            while (checkResultSet.next()) {
                if (checkResultSet.getString(1).equals(id)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static void update(Client client) {

        String updateStatement = "UPDATE Bank SET first_name = ?, last_name = ?, password = ?, balance = ? WHERE account_number = ?";


        try {

            PreparedStatement preparedStatementUpdate = bankConnection.prepareStatement(updateStatement);

            preparedStatementUpdate.setString(1, client.getFirstName());
            preparedStatementUpdate.setString(2, client.getLastName());
            preparedStatementUpdate.setString(3, client.getPassword());
            preparedStatementUpdate.setBigDecimal(4, client.getBalance());
            preparedStatementUpdate.setInt(5, client.getAccountNumber());

            preparedStatementUpdate.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static DefaultTableModel ShowHistory(DefaultTableModel defaultTableModel, int id) {


        String transactions = "SELECT * FROM Transactions WHERE account_number = ?";
        Object[] columnData = new Object[5];
        try {
            PreparedStatement preparedStatement = bankConnection.prepareStatement(transactions);
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                columnData[0] = resultSet.getInt(1);
                columnData[1] = resultSet.getInt(2);
                columnData[2] = resultSet.getString(3);
                columnData[3] = resultSet.getBigDecimal(4);
                columnData[4] = resultSet.getString(5);
                defaultTableModel.addRow(columnData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultTableModel;

    }

    static void ChangePassword(String password, Client client) {
        String updateStatement = "UPDATE Bank SET password = ? WHERE account_number = ?";

        try {
            PreparedStatement preparedStatementUpdate = bankConnection.prepareStatement(updateStatement);

            preparedStatementUpdate.setString(1, LoginSystem.getMd5(password));
            System.out.println(client.getAccountNumber());
            preparedStatementUpdate.setInt(2, client.getAccountNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void transaction(Integer toNumber, BigDecimal amount) {

        String updateStatement = "UPDATE Bank SET balance = ? WHERE account_number = ?";

        try {
            PreparedStatement preparedStatementUpdate = bankConnection.prepareStatement(updateStatement);

            preparedStatementUpdate.setBigDecimal(1, getBalance(toNumber).add(amount));
            preparedStatementUpdate.setInt(2, toNumber);

            preparedStatementUpdate.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static BigDecimal getBalance(Integer number) {
        String accountInfoStatement = "SELECT balance from Bank WHERE account_number = ?";

        try {
            PreparedStatement preparedStatement = bankConnection.prepareStatement(accountInfoStatement);

            preparedStatement.setInt(1, number);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return resultSet.getBigDecimal("balance");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getBalance(6));
        transaction(4, BigDecimal.valueOf(100));
    }
}