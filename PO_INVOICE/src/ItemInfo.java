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
	static HashMap<String, String[]> items;
    static int countValue ;

	public static JSONArray getItems(String headerId) throws Exception {
		JSONArray itemInvoice_json = new JSONArray();
		countValue = customMethodDatabase.getNumberOfRecord(
				Integer.parseInt(headerId), "PO.PO_RETURN_LINES", "HEADER_ID");
		items = new HashMap<String, String[]>(countValue);
		// System.out.println(countValue);
		try {
			if (countValue != 0) {
				// String value parameter
				String[] itemCode = new String[countValue];
				String[] itemName = new String[countValue];
				String[] unitName = new String[countValue];
				String[] itemNote = new String[countValue];
				String[] batchNo = new String[countValue];
				String[] expDate = new String[countValue];
				items.put("ITEM_ID", itemCode);//
				items.put("ITEM_NAME", itemName);//
				items.put("TRANSACTION_UOM", unitName);//
				items.put("DESCRIPTION", itemNote);
				items.put("BATCH_NO", batchNo);
				items.put("EXP_DATE", expDate);

				String[] lineNumber = new String[countValue];
				String[] quantity = new String[countValue];
				String[] taxPercent = new String[countValue];
				String[] transaction_number = new String[countValue];
				items.put("LINE_NUMBER", lineNumber);
				items.put("RETURN_QUANTITY1", quantity);
				items.put("TAX_PERCENT1", taxPercent);
				items.put("TRANSACTION_NUMBER", transaction_number);
				String[] unitPrice = new String[countValue];
				items.put("TRANSACTION_COST", unitPrice);
				String[] itemTotalAmountWithoutTax = new String[countValue];
				items.put("AMOUNT", itemTotalAmountWithoutTax);
				String[] itemTotalAmountWithTax = new String[countValue];
				items.put("AMOUNT_WITH_TAX", itemTotalAmountWithTax);
				String[] itemTotalAmountAfterDiscount = new String[countValue];
				items.put("AMOUNT_AFTER_DISCOUNT", itemTotalAmountAfterDiscount);
				String[] taxAmount = new String[countValue];
				items.put("TAX_AMOUNT", taxAmount);
				String[] percentDiscount = new String[countValue];
				items.put("PERCENT_DISCOUNT", percentDiscount);
				String tax_id = "";
				String tax_rate = "";

				// Construct connection
				Connection conn = oracle_connection.getOracleConnection();
				Statement stmt = conn.createStatement();

				String line_col_name = "DESCRIPTION";
				String line_table_name = "PO.PO_RETURN_HEADERS_V";
				String line_filter_statement = " where"
						+ " TRANSACTION_HEADER_ID = " + headerId;

				// select query data
				ResultSet line_result = stmt.executeQuery("select "
						+ line_col_name + " from " + line_table_name
						+ line_filter_statement);
				ResultSetMetaData line_rsmd = line_result.getMetaData();
				int line_columnsNumber = line_rsmd.getColumnCount();

				while (line_result.next()) {
					for (int i = 1; i <= line_columnsNumber; i++) {
						for (int j = countValue - 1; j >= 0; j--) {
							items.get("DESCRIPTION")[j] = line_result
									.getString(1);
						}
					}
				}

				// Construct Query
				line_col_name = "ITEM_ID,"//
						+ "ITEM_NAME,"//
						+ "TRANSACTION_UOM,"//
						+ "RETURN_QUANTITY1,"//
						+ "TRANSACTION_COST,"//
						+ "TRANSACTION_NUMBER,"//
						+ "TAX_AMOUNT1,"//
						+ "TRANSACTION_NUMBER,"//
						+ "TAX_PERCENT1"//
						// + "PERCENT_DISCOUNT,"//
						// + "DESCRIPTION"
				;
				line_table_name = "PO.PO_RETURN_LINES_V";
				line_filter_statement = " where" + " HEADER_ID = " + headerId;

				// select query data
				line_result = stmt.executeQuery("select " + line_col_name
						+ " from " + line_table_name + line_filter_statement);
				line_rsmd = line_result.getMetaData();
				int numOfRecord = 0;
				line_columnsNumber = line_rsmd.getColumnCount();
				while (line_result.next()) {
					for (int i = 1; i <= line_columnsNumber; i++) {
						String columnValue = line_result.getString(i);
						String columnName = line_rsmd.getColumnName(i);
						if (items.containsKey(columnName)) {
							items.get(columnName)[numOfRecord] = columnValue;
						}
					}
					items.get("AMOUNT")[numOfRecord] = String
							.valueOf(Integer.parseInt(items
									.get("TRANSACTION_COST")[numOfRecord])
									* Integer.parseInt(items
											.get("RETURN_QUANTITY1")[numOfRecord]));

					String sql_tax = "select TAX_PERCENT1 "
							+ "from PO.PO_TRANSACTION_LINES "
							+ "where TRANSACTION_NUMBER = '"
							+ items.get("TRANSACTION_NUMBER")[numOfRecord]
							+ "' AND ITEM_ID = "
							+ items.get("ITEM_ID")[numOfRecord];
					ResultSet tax_result = stmt.executeQuery(sql_tax);
					tax_result.next();
					items.get("TAX_PERCENT1")[numOfRecord] = tax_result
							.getString(1);

					if (items.get("TAX_PERCENT1")[numOfRecord] == ""
							|| items.get("TAX_PERCENT1")[numOfRecord] == null)
						items.get("TAX_PERCENT1")[numOfRecord] = "0";
					if (tax_rate != null && tax_rate != "")
						items.get("TAX_PERCENT1")[numOfRecord] = tax_rate;

					items.get("AMOUNT_AFTER_DISCOUNT")[numOfRecord] = items
							.get("AMOUNT")[numOfRecord];

					items.get("TAX_AMOUNT")[numOfRecord] = String
							.valueOf(Long.parseLong(items
									.get("AMOUNT_AFTER_DISCOUNT")[numOfRecord])
									* (Long.parseLong(items.get("TAX_PERCENT1")[numOfRecord]))
									/ 100);

					items.get("AMOUNT_WITH_TAX")[numOfRecord] = String
							.valueOf(Long.parseLong(items
									.get("AMOUNT_AFTER_DISCOUNT")[numOfRecord])
									* (100 + Long.parseLong(items
											.get("TAX_PERCENT1")[numOfRecord]))
									/ 100);
					items.get("PERCENT_DISCOUNT")[numOfRecord] = "0";
					numOfRecord += 1;
					items.get("LINE_NUMBER")[numOfRecord - 1] = String
							.valueOf(numOfRecord);
				}

				line_col_name = "LOT_NUMBER";
				line_table_name = "PO.PO_LOTS_TEMP";
				line_filter_statement = " where" + " HEADER_ID = " + headerId;

				// select query data
				line_result = stmt.executeQuery("select " + line_col_name
						+ " from " + line_table_name + line_filter_statement);
				line_rsmd = line_result.getMetaData();
				line_columnsNumber = line_rsmd.getColumnCount();
				int num = 0;
				while (line_result.next()) {
					for (int i = 1; i <= line_columnsNumber; i++) {
						String columnValue = line_result.getString(i);
						String columnName = line_rsmd.getColumnName(i);
						items.get("BATCH_NO")[num] = columnValue;
					}
					num += 1;
				}

				for (int i = 0; i < countValue; i++) {
					JSONObject item_detail = new JSONObject();
					item_detail.put("lineNumber",
							Integer.parseInt(items.get("LINE_NUMBER")[i]));
					item_detail.put("itemCode", items.get("ITEM_ID")[i]);
					item_detail.put("itemName", items.get("ITEM_NAME")[i]);
					item_detail
							.put("unitName", items.get("TRANSACTION_UOM")[i]);
					item_detail.put("itemNote", items.get("DESCRIPTION")[i]);
					item_detail.put("unitPrice",
							Integer.parseInt(items.get("TRANSACTION_COST")[i]));
					item_detail.put("quantity",
							Integer.parseInt(items.get("RETURN_QUANTITY1")[i]));
					item_detail.put("itemTotalAmountWithoutTax",
							Long.parseLong(items.get("AMOUNT")[i]));
					item_detail.put("itemTotalAmountWithTax",
							Long.parseLong(items.get("AMOUNT_WITH_TAX")[i]));
					item_detail.put("itemTotalAmountAfterDiscount", Long
							.parseLong(items.get("AMOUNT_AFTER_DISCOUNT")[i]));
					item_detail.put("taxPercentage",
							Integer.parseInt(items.get("TAX_PERCENT1")[i]));
					item_detail.put("taxAmount",
							Long.parseLong(items.get("TAX_AMOUNT")[i]));
					item_detail.put("discount", Long.parseLong("0"));
					item_detail.put("batchNo", batchNo[i]);
					item_detail.put("expDate", expDate[i]);
					itemInvoice_json.put(item_detail);
				}
			}

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemInvoice_json;
	}
	// public static Long getAmount(){
	// return new Long(1L);
	// }
}