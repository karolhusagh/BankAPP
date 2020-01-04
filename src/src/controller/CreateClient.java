package controller;

import model.DatabaseConnection;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;


public class CreateClient {
    private Connection connection = DatabaseConnection.getConnection();

    public void AddClient(String firstName, String lastName, String id, String password, BigDecimal balance) {
        if(doesClientExist(id)) return;

        try {
            String createClientStatement = "INSERT INTO Bank (first_name, last_name, id, password, balance) values (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatementClient = connection.prepareStatement(createClientStatement)) {
                preparedStatementClient.setString(1, firstName);
                preparedStatementClient.setString(2, lastName);
                preparedStatementClient.setString(3, id);
                preparedStatementClient.setString(4, password);
                preparedStatementClient.setBigDecimal(5, balance);

                preparedStatementClient.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean doesClientExist(String id) {
        try {
            String checkClient = "SELECT id FROM Bank";

            PreparedStatement preparedStatement = connection.prepareStatement(checkClient);

            ResultSet checkResultSet = preparedStatement.executeQuery();

            while (checkResultSet.next()) {
                if (checkResultSet.getString(1).equals(id)) {
                    JOptionPane.showMessageDialog(null, "ID already exists");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
