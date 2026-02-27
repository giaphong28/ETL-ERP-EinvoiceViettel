
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import org.json.*;
/**
 *
 * @author ADMIN
 */
public class generalInfo {
	public static String transaction_number = "";
    public static JSONObject getGeneralInfo(String header_id)
            throws Exception {        
        JSONObject generalInfo_json = new JSONObject();
        
        try {// TODO code application logic here
            HashMap<String, String> generalInvoiceInfo = new HashMap<String, String>();
            generalInvoiceInfo.put("CUS_CHAR4", "");
            generalInvoiceInfo.put("INVOICE_TYPE", "");
//            generalInvoiceInfo.put("TRANSACTION_DATE", "");
            generalInvoiceInfo.put("CURRENCY_CODE", "");
            generalInvoiceInfo.put("STATUS", "");
            generalInvoiceInfo.put("INVOICE_NUMBER", "");
            generalInvoiceInfo.put("RATE", "");
            generalInvoiceInfo.put("TRANSACTION_NUMBER", "");

            //Construct Query
            String column_name
                    = "CUS_CHAR4,"//invoiceSeries
                    + "INVOICE_TYPE,"//invoiceType
//                    + "TRANSACTION_DATE,"//invoiceInssuedDate need to transform
                    + "CURRENCY_CODE,"//currencyCode
                    + "INVOICE_NUMBER,"//
                    + "RATE,"//exchangeRate
                    + "TRANSACTION_NUMBER"//transactionUuid
                    ;
            String table_name = "OM.OM_INVOICE_HEADERS";
            String filter_statement = " where"
                    + " HEADER_ID = " + header_id;

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
                    String columnValue = result.getString(i);
                    String columnName = rsmd.getColumnName(i);
                    if (generalInvoiceInfo.containsKey(columnName)) {
                        generalInvoiceInfo.put(columnName, columnValue);
                    }
                }
            }
            
            //Construct Query
            column_name
                    = "STATUS"
                    ;
            table_name = "AR.AR_INVOICE_HEADERS";
            filter_statement = " where"
                    + " TRANSACTION_NUMBER = \'" + generalInvoiceInfo.get("TRANSACTION_NUMBER")+"\'";


            //select query data
            result = stmt.executeQuery(
                    "select " + column_name
                    + " from " + table_name
                    + filter_statement);
            rsmd = result.getMetaData();
            columnsNumber = rsmd.getColumnCount();

            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = result.getString(i);
                    String columnName = rsmd.getColumnName(i);
                    if (generalInvoiceInfo.containsKey(columnName)) {
                        generalInvoiceInfo.put(columnName, columnValue);
                    }
                }
            }
            //format and tranform paymentStatus
            Boolean paymentStatus = null;
            if (generalInvoiceInfo.containsKey("STATUS")) {
                if (generalInvoiceInfo.get("STATUS").equals("Complete")) {
                    paymentStatus = true;
                } else {
                    paymentStatus = false;
                }
                generalInvoiceInfo.put("STATUS", String.valueOf(paymentStatus));
            }

//            System.out.println(generalInvoiceInfo.get("STATUS"));            
            //Invoice type checking null and data format
            if (generalInvoiceInfo.containsKey("INVOICE_TYPE")) {
//                if (generalInvoiceInfo.get("INVOICE_TYPE") == null) {
//                    generalInvoiceInfo.put("INVOICE_TYPE", "1");
//                }

                int invoiceType = Integer.parseInt(generalInvoiceInfo.
                        get("INVOICE_TYPE"));
                String invoice_type = String.valueOf(invoiceType);

                generalInvoiceInfo.put("INVOICE_TYPE", invoice_type);
            }
            //get transaction_number for naming einvoice pdf
            transaction_number = generalInvoiceInfo.get("TRANSACTION_NUMBER");
            
            generalInfo_json.put("transactionUuid", 
                    generalInvoiceInfo.get("TRANSACTION_NUMBER"));
            generalInfo_json.put("invoiceType", 
                    generalInvoiceInfo.get("INVOICE_TYPE"));
            generalInfo_json.put("templateCode", 
                    "templatecode");
            generalInfo_json.put("invoiceSeries", 
                    "invoiceSeries");
//            generalInfo_json.put("invoiceInssuedDate", 
//                    Long.getLong(generalInvoiceInfo.get("invoiceInssuedDate")));
            generalInfo_json.put("currencyCode", 
                    generalInvoiceInfo.get("CURRENCY_CODE"));
            generalInfo_json.put("exchangeRate", 
                    Integer.parseInt(generalInvoiceInfo.get("RATE")));
            generalInfo_json.put("adjustmentType", 
                    "1");//default value "1"
            generalInfo_json.put("paymentStatus", 
                    Boolean.parseBoolean(generalInvoiceInfo.get("STATUS")));
            generalInfo_json.put("cusGetInvoiceRight", 
                   new Boolean(true));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generalInfo_json;
    }
}