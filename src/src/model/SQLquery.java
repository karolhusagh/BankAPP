package model;

import java.sql.*;
import model.Client;

public class SQLquery {
    private static Connection bankConnection = DatabaseConnection.getConnection();


    //powinno byc login
    public static String getPasswd(String match) {

        String accountInfoStatement = "SELECT password from Bank where first_name = ?";

        try {
            PreparedStatement preparedStatement = bankConnection.prepareStatement(accountInfoStatement);

            preparedStatement.setString(1, match);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return resultSet.getString("password");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //powinno byc login
    public static Client SelectCustomer(String login){
        try {
        String selectCustomers = "SELECT * FROM Bank where account_number = ?";

        PreparedStatement preparedStatement = bankConnection.prepareStatement(selectCustomers);

        preparedStatement.setString(1, login);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return new Client(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3) ,
                resultSet.getString(4) ,resultSet.getString(5),resultSet.getBigDecimal(6));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
