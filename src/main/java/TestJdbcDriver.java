/*import java.sql.*;

public class TestJdbcDriver {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/shop",
                    "root",
                    "Planas4!"
            );
            System.out.println("Connected to database.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not found.");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }
}*/
