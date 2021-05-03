package sample.test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class gConnect {
    public static Connection getConnection() throws SQLException {
        String connectionUrl = "jdbc:sqlserver://DESKTOP-5E0NPQE\\PHUONGSS;databaseName=dbBayes;user=sa;password=123";
        Connection con = DriverManager.getConnection(connectionUrl);
        return con;
    }
    public static void  testConnection(){
        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://DESKTOP-5E0NPQE\\PHUONGSS;databaseName=dbBayes;user=sa;password=123";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT TOP 10 * FROM test";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString("id") + " " + rs.getString("id"));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void testConnection2(){
        String connectionUrl = "jdbc:sqlserver://DESKTOP-5E0NPQE\\PHUONGSS;databaseName=dbBayes;user=sa;password=123";

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT TOP 10 * FROM test";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString("id") + " " + rs.getString("id"));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
