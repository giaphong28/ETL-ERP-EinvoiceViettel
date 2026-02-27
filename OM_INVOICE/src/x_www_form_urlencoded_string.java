import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;


public class x_www_form_urlencoded_string {
	public static String searchInvoiceByTransactionUuid(String header_id){
		String invoice_string="";
		try{
		HashMap<String, String> invoice_info = new HashMap<String, String>();
		invoice_info.put("TRANSACTION_NUMBER", "");
		invoice_info.put("SUPPLIER_TAX_CODE", "taxcode");
		// Construct Query
		String column_name = "TRANSACTION_NUMBER"// transactionNumber
		;
		String table_name = "OM.OM_INVOICE_HEADERS";
		String filter_statement = " where" + " HEADER_ID = " + header_id;

		// Construct connection
		Connection conn = oracle_connection.getOracleConnection();
		Statement stmt = conn.createStatement();

		// select query data
		ResultSet result = stmt.executeQuery(
				"select " + column_name
				+ " from " + table_name + filter_statement);
		ResultSetMetaData rsmd = result.getMetaData();
		int columnsNumber = rsmd.getColumnCount();

		while (result.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				String columnValue = result.getString(i);
				String columnName = rsmd.getColumnName(i);
				if (invoice_info.containsKey(columnName)) {
					invoice_info.put(columnName, columnValue);
				}
			}
		}
		invoice_string = "transactionUuid="+invoice_info.get("TRANSACTION_NUMBER")
				+"&supplierTaxCode="+invoice_info.get("SUPPLIER_TAX_CODE");		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return invoice_string;
	}
	//updatePaymentStatus: use for 
	public static String updatePaymentStatus(String header_id){
		String urlencoded_string = "";
		try{
			HashMap<String, String> invoice_info = new HashMap<String, String>();
			invoice_info.put("TRANSACTION_NUMBER", "");
			invoice_info.put("SUPPLIER_TAX_CODE", "taxcode");
			// Construct Query
			String column_name = "TRANSACTION_NUMBER"// transactionNumber
			;
			String table_name = "OM.OM_INVOICE_HEADERS";
			String filter_statement = " where" + " HEADER_ID = " + header_id;

			// Construct connection
			Connection conn = oracle_connection.getOracleConnection();
			Statement stmt = conn.createStatement();

			// select query data
			ResultSet result = stmt.executeQuery(
					"select " + column_name
					+ " from " + table_name + filter_statement);
			ResultSetMetaData rsmd = result.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			while (result.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = result.getString(i);
					String columnName = rsmd.getColumnName(i);
					if (invoice_info.containsKey(columnName)) {
						invoice_info.put(columnName, columnValue);
					}
				}
			}
			urlencoded_string = "templateCode="+invoice_info.get("TRANSACTION_NUMBER")
					+"&supplierTaxCode="+invoice_info.get("SUPPLIER_TAX_CODE")
					+"&invoiceNo="+invoice_info.get("SUPPLIER_TAX_CODE")
					;		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return urlencoded_string;
	}
}
