package DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDao {

    public String doLogin(Connection conn, String userName, String password) throws SQLException {

        String query = "SELECT user_id, role FROM user WHERE username = ? AND password = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, password);


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    int userId = rs.getInt("user_id");
                    System.out.println("You have logged in as: " + role + "Your User ID is: "+ userId);
                    return role; 
                }
            }

        }

        System.out.println("Invalid username or password!");
        return null; 
    }
}
