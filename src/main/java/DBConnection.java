import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public DBConnection() {
    }
    public Connection getDBConnection () {
        String url = "jdbc:postgresql://localhost:5432/product_management_db";
        String username= "product_manager_user";
        String password = "123456";
         try {
             return DriverManager.getConnection(url, username, password);}
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
