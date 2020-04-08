import java.sql.*;
public class connectExample {
    public static void main(String args []){

        // Load the PostgreSQl JDBC Driver
        // register JDBC driver, optional, since java 1.6

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Connect to the database

        // auto close connection
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://comp421.cs.mcgill.ca:5432/", "cs421g37", "v447hm7d")) {

            if (conn != null) {
                System.out.println("Connected to the database!");

            } else {
                System.out.println("Failed to make connection!");

            }

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM Customer");

            while(rs.next()){
                Long phone_number = rs.getLong(1);
                String cname = rs.getString(2);
                String email_address = rs.getString(3);

                System.out.println("Customer " + cname + " has phone number " + phone_number + " and email address " + email_address);
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}