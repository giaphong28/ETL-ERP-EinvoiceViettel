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

	public static JSONObject getGeneralInfo(String header_id) throws Exception {
		JSONObject generalInfo_json = new JSONObject();

		try {
			// TODO code application logic here
			HashMap<String, String> generalInvoiceInfo = new HashMap<String, String>();

			// view INV.INV_TRANSACTION_HEADER_MISCS_V
			generalInvoiceInfo.put("INVOICE_SERI", "");
			generalInvoiceInfo.put("ATTRIBUTE6", "");
			generalInvoiceInfo.put("TRANSACTION_NUMBER", "");
			generalInvoiceInfo.put("STATUS", "false");// default: not pay
			generalInvoiceInfo.put("INVOICE_NUMBER", "");

			// view INV.INV_TRANSACTION_LINES_V
			generalInvoiceInfo.put("CURRENCY_CODE", "");
			generalInvoiceInfo.put("CURRENCY_CONVERSION_RATE", "");

			// Construct Query
			String column_name = "INVOICE_SERI,"// invoiceSeries
					+ "ATTRIBUTE6,"// invoiceType
					+ "INVOICE_NUMBER,"//
					+ "TRANSACTION_NUMBER"// transactionUuid
			;
			String table_name = "INV.INV_TRANSACTION_HEADER_MISCS_V";
			String filter_statement = " where" + " TRANSACTION_HEADER_ID = "
					+ header_id;

			// Construct connection
			Connection conn = oracle_connection.getOracleConnection();
			Statement stmt = conn.createStatement();

			// select query data
			ResultSet result = stmt.executeQuery("select " + column_name
					+ " from " + table_name + filter_statement);
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

			column_name = "CURRENCY_CODE,"// currency code
					+ "CURRENCY_CONVERSION_RATE"// transactionUuid
			;
			table_name = "INV.INV_TRANSACTION_LINES_V";
			filter_statement = " where" + " TRANSACTION_HEADER_ID = "
					+ header_id;

			// select query data
			result = stmt.executeQuery("select " + column_name + " from "
					+ table_name + filter_statement);
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


			// Invoice type checking null and data format
			if (generalInvoiceInfo.containsKey("ATTRIBUTE6")) {

				int invoiceType = Integer.parseInt(generalInvoiceInfo
						.get("ATTRIBUTE6"));
				String invoice_type = String.valueOf(invoiceType);

				generalInvoiceInfo.put("ATTRIBUTE6", invoice_type);
			}

			generalInfo_json.put("transactionUuid",
					generalInvoiceInfo.get("TRANSACTION_NUMBER"));
			generalInfo_json.put("invoiceType",
//					generalInvoiceInfo.get("ATTRIBUTE6"));
					"6");
			generalInfo_json.put("templateCode", "templeCode");
			generalInfo_json.put("invoiceSeries", "invoiceSeries");


			generalInfo_json.put("currencyCode",
					generalInvoiceInfo.get("CURRENCY_CODE"));
			generalInfo_json.put("exchangeRate",
					Integer.parseInt(generalInvoiceInfo.get("CURRENCY_CONVERSION_RATE")));
			generalInfo_json.put("adjustmentType", "1");// default value "1"
			generalInfo_json.put("paymentStatus",
					Boolean.parseBoolean(generalInvoiceInfo.get("STATUS")));
			generalInfo_json.put("cusGetInvoiceRight", new Boolean(true));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return generalInfo_json;
	}
}