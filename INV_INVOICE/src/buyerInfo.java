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
public class buyerInfo {

	public static JSONObject getBuyerInfo(String vs_site_id) throws Exception {
		JSONObject buyerInvoiceInfo_json = new JSONObject();
		// String output_checking = "";
		try {// TODO code application logic here
			HashMap<String, String> buyerInvoiceInfo = new HashMap<String, String>();
			buyerInvoiceInfo.put("VS_SITE_ID", "");//
			buyerInvoiceInfo.put("VS_ID", "");//
			buyerInvoiceInfo.put("buyerName", "");// buyerName
			buyerInvoiceInfo.put("VS_SITE_NAME", "");// buyerLegalName
			buyerInvoiceInfo.put("TAX_CODE", "");// buyerTaxCode
			buyerInvoiceInfo.put("ADDRESS_LINE1", "");// buyerAddressLine
			buyerInvoiceInfo.put("ZIP", "");// buyerPostalCode
			buyerInvoiceInfo.put("CITY", "");// buyerCityName
			buyerInvoiceInfo.put("COUNTRY_CODE", "");// buyerCountryCode
			buyerInvoiceInfo.put("PHONE", "");// buyerPhoneNumber
			buyerInvoiceInfo.put("EMAIL", "");// buyerEmail
			buyerInvoiceInfo.put("FAX", "");// buyerFaxNumber
			buyerInvoiceInfo.put("buyerDistrictName", "");// buyerFaxNumber
			buyerInvoiceInfo.put("BANK_NAME", "");// bankName
			buyerInvoiceInfo.put("BANK_ACCOUNT_NUM", "");// buyerBankAccount
			buyerInvoiceInfo.put("BANK_ID", "");// bankId
			buyerInvoiceInfo.put("BANK_BRANCH_ID", "");// bankBranchId
			buyerInvoiceInfo.put("BANK_CODE", "");// bankAccountId
			buyerInvoiceInfo.put("buyerIdType", "");// buyerIdType
			buyerInvoiceInfo.put("buyerIdNo", "");// buyerIdNo
			buyerInvoiceInfo.put("buyerCode", "");// buyerCode
			buyerInvoiceInfo.put("buyerBirthDay", "");// buyerBirthDay

			// Construct Query
			String site_column_name = "VS_ID,"// invoiceSeries
					+ "VS_SITE_ID,"// invoiceType
					+ "VS_SITE_NAME,"// buyerLegalName
					+ "TAX_CODE,"// buyerTaxCode
					+ "ADDRESS_LINE1,"// buyerAddressLine
					+ "ZIP,"// buyerPostalCode
					+ "CITY,"// buyerCityName
					+ "PHONE,"// buyerPhoneNumber
					+ "EMAIL,"// buyerEmail
					+ "FAX,"// buyerFaxNumber
					+ "BANK_ID,"// buyerFaxNumber
					+ "COUNTRY_CODE"// buyerCountryCode
			;
			String table_name_site = "HR.SYS_MASTER_SITE_VEN_CUS";
			String filter_statement_site = " where" + " VS_SITE_ID = "
					+ vs_site_id + "";

			// Construct connection
			Connection conn = oracle_connection.getOracleConnection();
			Statement stmt = conn.createStatement();

			// select query data
			ResultSet result = stmt.executeQuery("select " + site_column_name
					+ " from " + table_name_site + filter_statement_site);
			ResultSetMetaData rsmd = result.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (result.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = result.getString(i);
					String columnName = rsmd.getColumnName(i);
					if (buyerInvoiceInfo.containsKey(columnName)
							&& columnValue != null) {
						buyerInvoiceInfo.put(columnName, columnValue);
					}
				}
			}

			// Construct query
			String bank_column_name = "BANK_ACCOUNT_NUM," + "BANK_NAME, "//
					+ "BANK_ID "// bankAccountNum
			;
			String table_name_bank = "HR.SYS_MASTER_VS_BANKS";
			String filter_statement_bank = " where" + " VS_ID = " + vs_site_id
					+ " AND PRIMARY_FLAG = 'Y' AND ENABLE_FLAG = 'Y'";
			if (!buyerInvoiceInfo.get("BANK_ID").equals("")) {
				filter_statement_bank += " and BANK_ID = "
						+ buyerInvoiceInfo.get("BANK_ID");
			}

			// Construct connection
			Statement stmt_bank = conn.createStatement();

			// select query data
			ResultSet result_bank = stmt_bank.executeQuery("select "
					+ bank_column_name + " from " + table_name_bank
					+ filter_statement_bank);
			ResultSetMetaData rsmd_bank = result_bank.getMetaData();
			int columnsNumber_bank = rsmd_bank.getColumnCount();
			while (result_bank.next()) {
				for (int i = 1; i <= columnsNumber_bank; i++) {
					String columnValue = result_bank.getString(i);
					String columnName = rsmd_bank.getColumnName(i);
					if (buyerInvoiceInfo.containsKey(columnName)
							&& columnValue != null) {
						buyerInvoiceInfo.put(columnName, columnValue);
					}
				}
			}

			if (!buyerInvoiceInfo.get("BANK_ID").equals("")) {
				// Construct query
				String banks_column_name = "BANK_NAME"// bankName
				;
				String table_name_banks = "AP.AP_BANKS";
				String filter_statement_banks = " where" + " BANK_ID = "
						+ buyerInvoiceInfo.get("BANK_ID");

				// Construct connection
				Statement stmt_banks = conn.createStatement();

				// select query data
				ResultSet result_banks = stmt_banks.executeQuery("select "
						+ banks_column_name + " from " + table_name_banks
						+ filter_statement_banks);
				ResultSetMetaData rsmd_banks = result_banks.getMetaData();
				int columnsNumber_banks = rsmd_banks.getColumnCount();
				while (result_banks.next()) {
					for (int i = 1; i <= columnsNumber_banks; i++) {
						String columnValue = result_banks.getString(i);
						String columnName = rsmd_banks.getColumnName(i);
						if (buyerInvoiceInfo.containsKey(columnName)
								&& columnValue != null) {
							buyerInvoiceInfo.put(columnName, columnValue);
						}
					}
				}
			}
			buyerInvoiceInfo_json.put("buyerName",
					buyerInvoiceInfo.get("buyerName"));
			buyerInvoiceInfo_json.put("buyerLegalName",
					buyerInvoiceInfo.get("VS_SITE_NAME"));
			buyerInvoiceInfo_json.put("buyerTaxCode",
			// buyerInvoiceInfo.get("TAX_CODE"));
					"taxcode");
			buyerInvoiceInfo_json.put("buyerAddressLine",
					buyerInvoiceInfo.get("ADDRESS_LINE1"));
			buyerInvoiceInfo_json.put("buyerPostalCode",
					buyerInvoiceInfo.get("ZIP"));
			buyerInvoiceInfo_json.put("buyerCountryCode",
					buyerInvoiceInfo.get("COUNTRY_CODE"));
			buyerInvoiceInfo_json.put("buyerDistrictName",
					buyerInvoiceInfo.get("buyerDistrictName"));
			buyerInvoiceInfo_json.put("buyerCityName",
					buyerInvoiceInfo.get("CITY"));
			buyerInvoiceInfo_json.put("buyerPhoneNumber",
					buyerInvoiceInfo.get("PHONE"));// default value "1"
			buyerInvoiceInfo_json.put("buyerFaxNumber",
					buyerInvoiceInfo.get("FAX"));
			buyerInvoiceInfo_json.put("buyerEmail",
					buyerInvoiceInfo.get("EMAIL"));
			buyerInvoiceInfo_json.put("buyerBankName",
					buyerInvoiceInfo.get("BANK_NAME"));
			buyerInvoiceInfo_json.put("buyerBankAccount",
					buyerInvoiceInfo.get("BANK_ACCOUNT_NUM"));
			buyerInvoiceInfo_json.put("buyerIdType",
					buyerInvoiceInfo.get("buyerIdType"));
			buyerInvoiceInfo_json.put("buyerIdNo",
					buyerInvoiceInfo.get("buyerIdNo"));
			buyerInvoiceInfo_json.put("buyerCode",
					buyerInvoiceInfo.get("buyerCode"));
			buyerInvoiceInfo_json.put("buyerBirthDay",
					buyerInvoiceInfo.get("buyerBirthDay"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyerInvoiceInfo_json;
	}
}