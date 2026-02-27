import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.json.JSONObject;
import org.json.JSONArray;
/**
 *
 * @author ADMIN
 */

import java.util.HashMap;

public class ItemInfo {
	static HashMap<String, String> items;

	public static JSONArray getItems(String headerId) throws Exception {
		JSONArray itemInvoice_json = new JSONArray();
		items = new HashMap<String, String>();
		try {
			String itemCode = "";
			String itemName = "";
			String unitName = "";
			String itemNote = "";
			String batchNo = "";
			String expDate = "";
			items.put("LINE_ID", itemCode);//
			items.put("DESCRIPTION", itemName);//
			items.put("UOM", unitName);//
			items.put("DESCRIPTION", itemNote);
			items.put("BATCH_NO", batchNo);
			items.put("EXP_DATE", expDate);

			// int to string parameter
			String lineNumber = "1";
			String quantity = "1";
			String taxPercent = "";
			String taxId = "";
			items.put("LINE_NUMBER", lineNumber);
			items.put("QUANTITY", quantity);
			items.put("RATE", taxPercent);
			items.put("TAX_ID", taxId);
			String itemTotalAmountWithoutTax = "";
			items.put("AMOUNT", itemTotalAmountWithoutTax);
			String itemTotalAmountWithTax = "";
			items.put("AMOUNT_WITH_TAX", itemTotalAmountWithTax);
			String itemTotalAmountAfterDiscount = "";
			items.put("AMOUNT_AFTER_DISCOUNT", itemTotalAmountAfterDiscount);
			String taxAmount = "";
			items.put("TAX_AMOUNT", taxAmount);
			String discountAmount = "";
			items.put("PERCENT_DISCOUNT", discountAmount);

			// Construct connection
			Connection conn = oracle_connection.getOracleConnection();
			Statement stmt = conn.createStatement();

			// Construct Query
			String line_col_name = "ITEM_ID,"//
					+ "ITEM_NAME,"//
					+ "AMOUNT,"//
					+ "TAX_AMOUNT,"//
					+ "TAX_ID,"//
					+ "PERCENT_DISCOUNT"//
					+ ",DESCRIPTION";
			String line_table_name = "AR.AR_INVOICE_LINES_V";
			String line_filter_statement = " where" + " HEADER_ID = "
					+ headerId;

			// select query data
			ResultSet line_result = stmt.executeQuery("select " + line_col_name
					+ " from " + line_table_name + line_filter_statement);
			ResultSetMetaData line_rsmd = line_result.getMetaData();
			int line_columnsNumber = line_rsmd.getColumnCount();
			while (line_result.next()) {
				for (int i = 1; i <= line_columnsNumber; i++) {
					String columnValue = line_result.getString(i);
					String columnName = line_rsmd.getColumnName(i);
					if (items.containsKey(columnName)) {
						items.put(columnName, columnValue);
					}
				}
			}
			if (items.get("PERCENT_DISCOUNT") == ""
					|| items.get("PERCENT_DISCOUNT") == null)
				items.put("PERCENT_DISCOUNT", "0");
			items.put(
					"AMOUNT_AFTER_DISCOUNT",
					String.valueOf(Long.parseLong(items.get("AMOUNT"))
							* (100 - Long.parseLong(items
									.get("PERCENT_DISCOUNT"))) / 100));
			items.put("AMOUNT_WITH_TAX", String.valueOf(Long.parseLong(items
					.get("AMOUNT_AFTER_DISCOUNT"))
					+ Long.parseLong(items.get("TAX_AMOUNT"))));

			if (items.get("TAX_ID") != "" && items.get("TAX_ID") != null) {
				// Construct Query
				String tax_column_name = "TAX_ID," + "RATE"// exchangeRate
				;
				String tax_table_name = "PO.PO_TAXES";
				String tax_filter_statement = " where" + " TAX_ID = "
						+ items.get("TAX_ID");
				// select query data
				ResultSet tax_result = stmt.executeQuery("select "
						+ tax_column_name + " from " + tax_table_name
						+ tax_filter_statement);
				ResultSetMetaData tax_rsmd = tax_result.getMetaData();
				while (tax_result.next()) {
					if (items.get("TAX_ID").equals(tax_result.getString(1))) {
						String columnValue = tax_result.getString(2);
						String columnName = tax_rsmd.getColumnName(2);
						if (items.containsKey(columnName)) {
							items.put(columnName, columnValue);
						}

					}
				}
			} else {

				items.put("RATE", "0");
			}

			JSONObject item_detail = new JSONObject();
			item_detail.put("lineNumber",
					Integer.parseInt(items.get("LINE_NUMBER")));
			item_detail.put("itemCode", items.get("LINE_ID"));
			item_detail.put("itemName", items.get("DESCRIPTION"));
			item_detail.put("unitName", items.get("UOM"));
			item_detail.put("itemNote", items.get("DESCRIPTION"));
			item_detail.put("unitPrice", Integer.parseInt(items.get("AMOUNT")));
			item_detail
					.put("quantity", Integer.parseInt(items.get("QUANTITY")));
			item_detail.put("itemTotalAmountWithoutTax",
					Long.parseLong(items.get("AMOUNT")));
			item_detail.put("itemTotalAmountWithTax",
					Long.parseLong(items.get("AMOUNT_WITH_TAX")));
			item_detail.put("itemTotalAmountAfterDiscount",
					Long.parseLong(items.get("AMOUNT_AFTER_DISCOUNT")));
			item_detail.put("taxPercentage",
					Integer.parseInt(items.get("RATE")));
			item_detail.put("taxAmount",
					Long.parseLong(items.get("TAX_AMOUNT")));
			item_detail.put(
					"discount",
					Long.parseLong(items.get("PERCENT_DISCOUNT"))
							* Long.parseLong(items.get("AMOUNT")));
			item_detail.put("batchNo", batchNo);
			item_detail.put("expDate", expDate);
			itemInvoice_json.put(item_detail);

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemInvoice_json;
	}
}