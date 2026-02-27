import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class oracle_connection {
    public static Connection getOracleConnection()
            throws ClassNotFoundException, SQLException{
        String hostName = "localhost";
        String sid = "oracle";
        String userName = "username";
        String password = "password";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1521:" +sid;
    Connection conn =DriverManager.getConnection(connectionURL, userName, password);    
    return conn;
    }
}