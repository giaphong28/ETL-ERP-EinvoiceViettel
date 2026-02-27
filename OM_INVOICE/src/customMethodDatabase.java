
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 *
 * @author ADMIN
 */
public class customMethodDatabase {

    public static int getNumberOfRecord(String key_col_data,
            String key_table, String key_col_name)
            throws Exception {
        int countValue = 0;
        try {
            String column_name
                    = "Count(*)"//transactionUuid
                    ;
            String table_name = key_table;
            String filter_statement = " where"
                    + " " + key_col_name + " = \"" + key_col_data + "\"";

            //Construct connection
            Connection conn = oracle_connection.getOracleConnection();
            Statement stmt = conn.createStatement();

            //select query data
            ResultSet result = stmt.executeQuery(
                    "select " + column_name
                    + " from " + table_name
                    + filter_statement);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    countValue = result.getInt(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countValue;
    }

    public static int getNumberOfRecord(int key_col_data,
            String key_table, String key_col_name)
            throws Exception {
        int countValue = 0;
        try {
            String column_name
                    = "count(*)"//transactionUuid
                    ;
            String table_name = key_table;
            String filter_statement = " where"
                    + " " + key_col_name + " = " + key_col_data;

            //Construct connection
            Connection conn = oracle_connection.getOracleConnection();
            Statement stmt = conn.createStatement();

            //select query data
            ResultSet result = stmt.executeQuery(
                    "select " + column_name
                    + " from " + table_name
                    + filter_statement);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    countValue = result.getInt(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countValue;
    }
}
