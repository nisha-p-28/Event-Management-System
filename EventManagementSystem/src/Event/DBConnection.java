package Event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/eventdb"; 
    private static final String USER = "root"; // your MySQL username
    private static final String PASSWORD = "9876543210"; // your MySQL password

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

//        if (conn != null) {
//            System.out.println("✅ Database connected successfully!");
//        } else {
//            System.out.println("❌ Database connection failed!");
//        }

        return conn;
    }
}
