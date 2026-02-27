import java.sql.Connection;
import java.sql.Statement;


public class INV_MAIN {

	/**
	 * @param args
	 */
	public static String header_id = "";
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		header_id = args[0];//
		int method_id = Integer.parseInt(args[1]);//

		String json_string = "";
		String result = "";

		switch (method_id) {
		case 1:// create a einvoice draft
			json_string = create_json_string.invoice_draft(header_id);
			result = Create_invoice.invoke_invoice_draft(json_string);
			break;
		case 2:// preview a einvoice draft in pdf
			json_string = create_json_string.invoice_draft(header_id);
			result = Preview_invoice.preview_invoice_draft(json_string,
					header_id);
			break;
		case 3:// publish a einvoice
			json_string = create_json_string.invoice_draft(header_id);
			result = Create_invoice.invoke_invoice_draft(json_string);
			break;
		case 4:// preview a published einvoice in zip
			json_string = create_json_string.invoice(header_id);
			result = Preview_invoice.preview_invoice(json_string, header_id);
			break;

		default:
			System.out.println("Out of Scope");
		}
		String column_name = "EINVOICE_API_STATUS";
		
		String table_name = "INV.INV_TRANSACTION_HEADERS";
		String filter_statement = " where" + " TRANSACTION_HEADER_ID = " + header_id;

		// Construct connection
		Connection conn = oracle_connection.getOracleConnection();
		Statement stmt = conn.createStatement();
		
		// select query data
		int result1 = stmt.executeUpdate("update "+ table_name +" set " + column_name
				+ " = '" + result +"'"+ filter_statement);		
		
		System.out.println(json_string);
		System.out.println(result);
	}
}
