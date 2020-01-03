package controller;

import model.DatabaseConnection;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;


public class CreateClient {
    private DatabaseConnection connection = new DatabaseConnection();
    private  String firstName, lastName, id, password;
    private BigDecimal balance;
    int accountNumber;

    public CreateClient(String firstName, String lastName, String id, String password, int accountNumber, BigDecimal balance){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
        this.balance = balance;


        accountNumber = getAccountNumber();
        doesClientExist(id);




        addClientInfo(firstName,lastName,id,password, accountNumber, balance );
        }


    private void addClientInfo(String firstName, String lastName, String id,String password, int accountNumber, BigDecimal balance) {
        try
        {
            Connection sqlConnection = connection.DatabaseConnection();

            String createClientStatement = "INSERT INTO Bank (first_name, last_name, id, password, account_number, balance) values (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatementClient = sqlConnection.prepareStatement(createClientStatement))
            {
                preparedStatementClient.setString(1, firstName);
                preparedStatementClient.setString(2, lastName);
                preparedStatementClient.setString(3, id);
                preparedStatementClient.setString(4, password);
                preparedStatementClient.setInt(5, accountNumber);
                preparedStatementClient.setBigDecimal(6, balance);

                preparedStatementClient.execute();

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private boolean doesClientExist(String id)
    {
        Connection bankConnection = connection.DatabaseConnection();

        try
        {
            String checkClient = "SELECT id FROM Bank";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(checkClient);

            ResultSet checkResultSet = preparedStatement.executeQuery();

            while(checkResultSet.next())
            {
                if(checkResultSet.getString(1).equals(id))
                {
                    JOptionPane.showMessageDialog(null, "ID already exists");
                    return true;
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private int getAccountNumber()
    {
        Connection bankConnection = connection.DatabaseConnection();

        int largestAccountNumber = 0;

        try
        {
            String largestAccNum = "SELECT MAX(account_number) from bank";

            PreparedStatement preparedStatement = bankConnection.prepareStatement(largestAccNum);

            ResultSet resultlargestAcctNum = preparedStatement.executeQuery();

            while(resultlargestAcctNum.next())
            {
                largestAccountNumber = resultlargestAcctNum.getInt(1);
                //System.out.println("Largest account found: " + largestAccountNumber);
                return largestAccountNumber + 1;
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return largestAccountNumber + 1;
    }
}
