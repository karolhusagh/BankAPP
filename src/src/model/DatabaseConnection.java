package model;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

class DatabaseConnection {
    static private Connection connection = null;

    static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:bank.db");
                return connection;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return connection;
    }
}
