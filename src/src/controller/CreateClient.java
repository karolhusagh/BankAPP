package controller;

import model.DatabaseConnection;
import view.AccessAccount;

import java.math.BigDecimal;
import java.sql.*;


public class CreateClient {
    private DatabaseConnection bankConnection = new DatabaseConnection();
    private static int ACCOUNT_NUMBER;
    private  String firstName, lastName, id, password;
    private BigDecimal balance;

    public CreateClient(String firstName, String lastName, String id, String password, int accountNumber, BigDecimal balance){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
        this.balance = balance;

        ACCOUNT_NUMBER = ++accountNumber;

        addClientInfo(firstName,lastName,id,password, accountNumber, balance );
        }

    private void addClientInfo(String firstName, String lastName, String id,String password, int accountNumber, BigDecimal balance) {
        try
        {
            Connection sqlConnection = bankConnection.DatabaseConnection();

            String createClientStatement = "INSERT INTO Bank (first_name, last_name, id, password, account_number, balance) values (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatementClient = sqlConnection.prepareStatement(createClientStatement))
            {
                preparedStatementClient.setString(1, firstName);
                preparedStatementClient.setString(2, lastName);
                preparedStatementClient.setString(3, id);
                preparedStatementClient.setString(4, password);
                preparedStatementClient.setInt(5, ACCOUNT_NUMBER);
                preparedStatementClient.setBigDecimal(6, balance);

                preparedStatementClient.execute();

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
