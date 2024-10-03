package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    protected Connection connection;

    public DBContext() {
        try {
//            String url = "jdbc:sqlserver://danama.database.windows.net:1433;database=DANAMA;user=dangchau@danama;password=Tungdang1411;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            connection = DriverManager.getConnection(url);
            String url = "jdbc:sqlserver://ADMIN;databaseName=DANAMA;encrypt=false;trustServerCertificate=false;loginTimeout=30;";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }

    //Run file to test database connection
    public static void main(String[] args) {
        DBContext db = new DBContext();
        System.out.println(db.connection);
    }
}
