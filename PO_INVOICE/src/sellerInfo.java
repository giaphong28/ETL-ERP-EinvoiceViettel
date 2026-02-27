import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author ADMIN
 */
public class sellerInfo {
    public static JSONObject getSellerInfo(String vs_site_id)
            throws Exception {        
        JSONObject sellerInvoiceInfo_json = new JSONObject();
//        String output_checking = "";
        try {// TODO code application logic here
            HashMap<String, String> sellerInvoiceInfo = new HashMap<String, String>();
            sellerInvoiceInfo.put("VS_SITE_ID", "");//
            sellerInvoiceInfo.put("VS_ID", "");//
            sellerInvoiceInfo.put("VS_SITE_NAME", "");//buyerLegalName
            sellerInvoiceInfo.put("TAX_CODE", "taxcode");//buyerTaxCode
            sellerInvoiceInfo.put("ADDRESS_LINE1", "");//buyerAddressLine
            sellerInvoiceInfo.put("CITY", "");//buyerCityName
            sellerInvoiceInfo.put("COUNTRY_CODE", "");//buyerCountryCode
            sellerInvoiceInfo.put("PHONE", "");//buyerPhoneNumber
            sellerInvoiceInfo.put("EMAIL", "");//buyerEmail
            sellerInvoiceInfo.put("FAX", "");//buyerFaxNumber
            sellerInvoiceInfo.put("sellerDistrictName", "");//buyerFaxNumber
            sellerInvoiceInfo.put("BANK_NAME", "");//bankName
            sellerInvoiceInfo.put("BANK_BRANCH_NAME", "");//buyerBankName
            sellerInvoiceInfo.put("BANK_ACCOUNT_NUM", "");//buyerBankAccount
            sellerInvoiceInfo.put("BANK_ID", "");//bankId
            sellerInvoiceInfo.put("BANK_BRANCH_ID", "");//bankBranchId
            sellerInvoiceInfo.put("BANK_CODE", "");//bankAccountId
            sellerInvoiceInfo.put("sellerWebsite", "verp.com.vn");//sellerWebsite

            //Construct Query 
            String site_column_name
                    = "VS_ID,"//invoiceSeries
                    + "VS_SITE_ID,"//invoiceType
                    + "VS_SITE_NAME,"//buyerLegalName
//                    + "TAX_CODE,"//buyerTaxCode
                    + "ADDRESS_LINE1,"//buyerAddressLine
                    + "CITY,"//buyerCityName
                    + "PHONE,"//buyerPhoneNumber
                    + "EMAIL,"//buyerEmail
                    + "FAX,"//buyerFaxNumber
                    + "BANK_ID,"//buyerFaxNumber
                    + "COUNTRY_CODE"//buyerCountryCode
                    ;
            String table_name_site = "HR.SYS_MASTER_SITE_VEN_CUS";
            String filter_statement_site = " where"
                    +" VS_SITE_ID = " + vs_site_id+"";

            //Construct connection
            Connection conn = oracle_connection.getOracleConnection();
            Statement stmt = conn.createStatement();

            //select query data
            ResultSet result = stmt.executeQuery(
                    "select " + site_column_name
                    + " from " + table_name_site
                    + filter_statement_site);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (result.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = result.getString(i);
                    
                    String columnName = rsmd.getColumnName(i);
                    if (sellerInvoiceInfo.containsKey(columnName) 
                            && columnValue != null) {
                        sellerInvoiceInfo.put(columnName, columnValue);
                    }
                }
            }            
            
            sellerInvoiceInfo_json.put("sellerLegalName", 
                    sellerInvoiceInfo.get("VS_SITE_NAME"));
            sellerInvoiceInfo_json.put("sellerTaxCode", 
                    sellerInvoiceInfo.get("TAX_CODE"));
            sellerInvoiceInfo_json.put("sellerAddressLine", 
                    sellerInvoiceInfo.get("ADDRESS_LINE1"));
            sellerInvoiceInfo_json.put("sellerCountryCode", 
                    sellerInvoiceInfo.get("COUNTRY_CODE"));
            sellerInvoiceInfo_json.put("sellerDistrictName", 
                    sellerInvoiceInfo.get("sellerDistrictName"));
            sellerInvoiceInfo_json.put("sellerCityName", 
                    sellerInvoiceInfo.get("CITY"));
            sellerInvoiceInfo_json.put("sellerPhoneNumber",
                    sellerInvoiceInfo.get("PHONE"));//default value "1"
            sellerInvoiceInfo_json.put("sellerFaxNumber", 
                    sellerInvoiceInfo.get("FAX"));
            sellerInvoiceInfo_json.put("sellerEmail",
                    sellerInvoiceInfo.get("EMAIL"));
            sellerInvoiceInfo_json.put("sellerBankName",
                    sellerInvoiceInfo.get("BANK_NAME"));
            sellerInvoiceInfo_json.put("sellerBankAccount",
                    sellerInvoiceInfo.get("BANK_ACCOUNT_NUM"));
            sellerInvoiceInfo_json.put("sellerWebsite",
                    sellerInvoiceInfo.get("sellerWebsite"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sellerInvoiceInfo_json;
    }
}