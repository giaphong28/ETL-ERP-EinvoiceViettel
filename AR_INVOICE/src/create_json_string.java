import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;

public class create_json_string {
	static public String invoice_draft(String header_id) {
		JSONObject val = new JSONObject();
		try {
			HashMap<String, String> invoice_info = new HashMap<String, String>();
			invoice_info.put("CUSTOMER_SITE_ID", "");
			invoice_info.put("BANK_ID", "");
			// xây dựng truy vấn dữ liệu
			String column_name = "CUSTOMER_SITE_ID"// invoiceType
			;
			String table_name = "AR.AR_INVOICE_HEADERS_V";
			String filter_statement = " where" + " HEADER_ID = " + header_id;

			// tạo kết nối database
			Connection conn = oracle_connection.getOracleConnection();
			Statement stmt = conn.createStatement();

			// truy vấn dữ liệu
			ResultSet result = stmt.executeQuery("select " + column_name
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

			// đưa dữ liệu vào json
			JSONObject general = generalInfo.getGeneralInfo(header_id);
			JSONObject buyer = buyerInfo.getBuyerInfo(
					invoice_info.get("CUSTOMER_SITE_ID"));
			JSONArray items = ItemInfo.getItems(header_id);
			JSONArray payment = paymentInfo.getPaymentInfo();
			JSONObject seller = sellerInfo.getSellerInfo("sellerId");
			JSONObject summarize = summarizeInfo.getSummarizeInfo();
			JSONArray tax = taxBreakdownInfo.getTaxBreakdownInfo();
			JSONArray metadata = new JSONArray();
			JSONArray meterReading = new JSONArray();
			val.put("generalInvoiceInfo", general);
			val.put("buyerInfo", buyer);
			val.put("itemInfo", items);
			val.put("payments", payment);
			val.put("sellerInfo", seller);
			val.put("summarizeInfo", summarize);
			val.put("taxBreakdowns", tax);
			val.put("metadata", metadata);
			val.put("meterReading", meterReading);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return val.toString(1);
	}
	static public String invoice (String header_id){
		JSONObject val = new JSONObject();
		try {
			HashMap<String, String> invoice_info = new HashMap<String, String>();
			invoice_info.put("CUSTOMER_SITE_ID", "");
			invoice_info.put("BANK_ID", "");
			// tạo truy vấn
			String column_name = "CUSTOMER_SITE_ID";// invoiceType
			
			String table_name = "AR.AR_INVOICE_HEADERS_V";
			String filter_statement = " where" + " HEADER_ID = " + header_id;

			// tạo kết nối database
			Connection conn = oracle_connection.getOracleConnection();
			Statement stmt = conn.createStatement();

			// truy vấn dữ liệu 
			ResultSet result = stmt.executeQuery("select " + column_name
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
			// Connection conn = oracle_connection.getOracleConnection();
			JSONObject general = generalInfo.getGeneralInfo(header_id);
			JSONObject buyer = buyerInfo.getBuyerInfo(
					invoice_info.get("CUSTOMER_SITE_ID"));
			JSONArray items = ItemInfo.getItems(header_id);
			JSONArray payment = paymentInfo.getPaymentInfo();
			JSONObject seller = sellerInfo.getSellerInfo("sellerId");
			JSONObject summarize = summarizeInfo.getSummarizeInfo();
			JSONArray tax = taxBreakdownInfo.getTaxBreakdownInfo();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return val.toString(1);
	}
}
