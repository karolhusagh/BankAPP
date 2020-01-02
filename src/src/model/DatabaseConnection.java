package model;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
     static Connection connection = null;


    public Connection DatabaseConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:bank.db");
            //JOptionPane.showMessageDialog(null, "Connected");
            return connection;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }

}
