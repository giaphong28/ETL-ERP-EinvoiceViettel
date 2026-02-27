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
    public static JSONArray getItems(String headerId)
            throws Exception {
        JSONArray itemInvoice_json = new JSONArray();
                countValue =  customMethodDatabase
                .getNumberOfRecord(Integer.parseInt(headerId), "INV.INV_TRANSACTION_LINES_V", "TRANSACTION_HEADER_ID");
                items  = new HashMap<String, String[]>(countValue);
//        System.out.println(countValue);
        try {
            if (countValue != 0) {
                //String value parameter
                String[] itemCode = new String[countValue];
                String[] itemName = new String[countValue];
                String[] unitName = new String[countValue];
                String[] itemNote = new String[countValue];
                String[] customTaxAmount = new String[countValue];
                String[] batchNo = new String[countValue];
                String[] expDate = new String[countValue];
                items.put("ITEM_ID", itemCode);//
                items.put("ITEM_NAME", itemName);//
                items.put("TRANSACTION_UOM", unitName);//
                items.put("ITEM_NUMBER", itemCode);
                items.put("ITEM_NOTE", itemNote);
                items.put("CUSTOM_TAX_AMOUNT", customTaxAmount);
                items.put("BATCH_NO", batchNo);
                items.put("EXP_DATE", expDate);

                //int to string parameter
                String[] lineNumber = new String[countValue];
                String[] quantity = new String[countValue];
                String[] taxPercent = new String[countValue];
                String[] taxId = new String[countValue];
                items.put("LINE_NUMBER", lineNumber);
                items.put("PRIMARY_QUANTITY", quantity);
                items.put("RATE", taxPercent);
                items.put("TAX_ID", taxId);
                String[] unitPrice = new String[countValue];
                items.put("ACTUAL_COST", unitPrice);
                String[] itemTotalAmountWithoutTax = new String[countValue];
                items.put("AMOUNT", itemTotalAmountWithoutTax);
                String[] itemTotalAmountWithTax = new String[countValue];
                items.put("AMOUNT_WITH_TAX", itemTotalAmountWithTax);
                String[] itemTotalAmountAfterDiscount = new String[countValue];
                items.put("AMOUNT_AFTER_DISCOUNT", itemTotalAmountAfterDiscount);
                String[] taxAmount = new String[countValue];
                items.put("TAX_AMOUNT", taxAmount);
                String[] discountAmount = new String[countValue];
                items.put("DISCOUNT_AMOUNT", discountAmount);

                //Construct connection
                Connection conn = oracle_connection.getOracleConnection();
                Statement stmt = conn.createStatement();

                //Construct Query
                String line_col_name
                        = "ITEM_ID,"//
                        + "ITEM_NAME,"//
                        + "ITEM_NUMBER,"//
                        + "TRANSACTION_UOM,"//
                        + "PRIMARY_QUANTITY,"//
                        + "ACTUAL_COST "//UNIT_PRICE 

                        ;
                String line_table_name = "INV.INV_TRANSACTION_LINES_V";
                String line_filter_statement = " where"
                        + " TRANSACTION_HEADER_ID = " + headerId;

                //select query data
                ResultSet line_result = stmt.executeQuery("select " + line_col_name
                        + " from " + line_table_name
                        + line_filter_statement);
                ResultSetMetaData line_rsmd = line_result.getMetaData();
                int line_columnsNumber = line_rsmd.getColumnCount();
                int numOfRecord = 0;
                while (line_result.next()) {
                    items.get("LINE_NUMBER")[numOfRecord] = String.valueOf(numOfRecord + 1);
                    for (int i = 1; i <= line_columnsNumber; i++) {
                        String columnValue = line_result.getString(i);
                        String columnName = line_rsmd.getColumnName(i);
                        if (items.containsKey(columnName)) {
                            items.get(columnName)[numOfRecord] = columnValue;
                        }
                    }
                    numOfRecord += 1;
                }
                
                //
                for(int i = 0; i<numOfRecord;i++){
                	if(quantity[i].charAt(0) == '-'){
                		quantity[i]=quantity[i].substring(1);
                	}
                	taxPercent[i] = "0";
                	taxAmount[i] = "0";
                	discountAmount[i] = "0";
                	itemTotalAmountWithoutTax[i] = String.valueOf(
                			Integer.parseInt(quantity[i])
                			* Integer.parseInt(unitPrice[i])
                			);
                	itemTotalAmountWithTax[i]= itemTotalAmountWithoutTax[i];
                	itemTotalAmountAfterDiscount[i] = itemTotalAmountWithoutTax[i];
                	
                }
                
                
                for(int i = 0; i< countValue; i++){
                    JSONObject item_detail = new JSONObject();
                    item_detail.put("lineNumber", Integer.parseInt(lineNumber[i]));
                    item_detail.put("itemCode", itemCode[i]);
                    item_detail.put("itemName", itemName[i]);
                    item_detail.put("unitName", unitName[i]);
                    item_detail.put("itemNote", "");
                    item_detail.put("unitPrice", Integer.parseInt(unitPrice[i]));
                    item_detail.put("quantity", Integer.parseInt(quantity[i]));
                    item_detail.put("itemTotalAmountWithoutTax", Long.parseLong(itemTotalAmountWithoutTax[i]));
                    item_detail.put("itemTotalAmountWithTax", Long.parseLong(itemTotalAmountWithTax[i]));
                    item_detail.put("itemTotalAmountAfterDiscount", Long.parseLong(itemTotalAmountAfterDiscount[i]));
                    item_detail.put("taxPercentage", Integer.parseInt(taxPercent[i]));
                    item_detail.put("taxAmount", Long.parseLong(taxAmount[i]));
                    item_detail.put("customTaxAmount", customTaxAmount[i]);
                    item_detail.put("discount", Long.parseLong(discountAmount[i]));
                    item_detail.put("batchNo", batchNo[i]);
                    item_detail.put("expDate", expDate[i]);   
                    itemInvoice_json.put(item_detail);
                }        
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemInvoice_json;
    }

}