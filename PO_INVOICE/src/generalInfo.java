
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
            generalInvoiceInfo.put("INVOICE_SERIAL", "");
            generalInvoiceInfo.put("INVOICE_SYMBOL", "06");
//            generalInvoiceInfo.put("TRANSACTION_DATE", "");
            generalInvoiceInfo.put("CURRENCY_CODE", "");
            generalInvoiceInfo.put("STATUS", "");
            generalInvoiceInfo.put("INVOICE_NUM", "");
            generalInvoiceInfo.put("CONVER_RATE", "");
            generalInvoiceInfo.put("TRANSACTION_NUMBER", "");

            //Construct Query
            String column_name
                    = "INVOICE_SERIAL,"//invoiceSeries
//                    + "INVOICE_SYMBOL,"//invoiceType
//                    + "TRANSACTION_DATE,"//invoiceInssuedDate need to transform
                    + "INVOICE_NUM,"//
                    + "TRANSACTION_NUMBER"//transactionUuid
                    ;
            String table_name = "PO.PO_RETURN_HEADERS_V";
            String filter_statement = " where"
                    + " TRANSACTION_HEADER_ID = " + header_id;

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
            
         // Construct Query
            column_name = "INVOICE_CURRENCY_CODE,"//
					+ "RATE,"//
					+ "PAYMENT_STATUS_FLAG"
			;
            filter_statement = "SELECT " + column_name
            		+ " from AP_INVOICE_LINES_ALL " +
            		" where RECEIPT_ID = " +
            		"(SELECT TRANSACTION_HEADER_ID " +
            		"FROM PO.PO_RETURN_LINES " +
            		"WHERE HEADER_ID = "
            		+ header_id+")" ;
            result = stmt.executeQuery(filter_statement);
            while(result.next()){
            generalInvoiceInfo.put("CURRENCY_CODE", result.getString(1));
            generalInvoiceInfo.put("CONVER_RATE", result.getString(2));
            generalInvoiceInfo.put("STATUS", result.getString(3));
            }
            if(generalInvoiceInfo.get("STATUS")!="Y")
            	generalInvoiceInfo.put("STATUS", "true");
            else
            	generalInvoiceInfo.put("STATUS", "false");
            //Invoice type checking null and data format
            if (generalInvoiceInfo.containsKey("INVOICE_SYMBOL")) {
//                if (generalInvoiceInfo.get("INVOICE_SYMBOL") == null) {
//                    generalInvoiceInfo.put("INVOICE_SYMBOL", "1");
//                }

                int invoiceType = Integer.parseInt(generalInvoiceInfo.
                        get("INVOICE_SYMBOL"));
                String invoice_type = String.valueOf(invoiceType);

                generalInvoiceInfo.put("INVOICE_SYMBOL", invoice_type);
            }
            //get transaction_number for naming einvoice pdf
            transaction_number = generalInvoiceInfo.get("TRANSACTION_NUMBER");
            
            generalInfo_json.put("transactionUuid", 
                    generalInvoiceInfo.get("TRANSACTION_NUMBER"));
            generalInfo_json.put("invoiceType", 
                    generalInvoiceInfo.get("INVOICE_SYMBOL"));
            generalInfo_json.put("templateCode", 
                    "templatecode");
            generalInfo_json.put("invoiceSeries", 
                    "invoiceSeries");
//            generalInfo_json.put("invoiceInssuedDate", 
//                    Long.getLong(generalInvoiceInfo.get("invoiceInssuedDate")));
            generalInfo_json.put("currencyCode", 
                    generalInvoiceInfo.get("CURRENCY_CODE"));
            generalInfo_json.put("exchangeRate", 
                    Integer.parseInt(generalInvoiceInfo.get("CONVER_RATE")));
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