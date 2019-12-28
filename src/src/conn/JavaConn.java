package conn;
import javax.swing.*;
import java.sql.*;

public class JavaConn {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;

    public Connection connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:BankAPP.db");
            JOptionPane.showMessageDialog(null, "Connected");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }

    public static void main(String[] args) {
        JavaConn javaConn = new JavaConn();
        javaConn.connectDB();
    }

}
