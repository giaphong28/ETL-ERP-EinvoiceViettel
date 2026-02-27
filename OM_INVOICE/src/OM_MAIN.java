import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;

public class OM_MAIN {

	/**
	 * @param args
	 */
	public static String header_id = "";
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		header_id = args[0];
		int method_id = Integer.parseInt(args[1]);

		String json_string = "";
		String urlencoded_string = "";
		String result = "";

		switch (method_id) {
		case 1:// create a einvoice draft
			json_string = create_json_string.invoice_draft(header_id);
			result = Create_invoice_draft.invokeApi(json_string);
			break;
		case 2:// preview a einvoice draft in pdf
			json_string = create_json_string.invoice_draft(header_id);
			result = Preview_invoice.preview_invoice_draft(json_string,
					header_id);
			break;
		case 3:// publish a einvoice
			json_string = create_json_string.invoice(header_id);
			result = Publish_invoice.invokeApi(json_string);
			break;
		case 4:// preview a published einvoice in zip
			json_string = create_json_string.invoice(header_id);
			result = Preview_invoice.preview_invoice(json_string, header_id);
			break;
		case 5:
			urlencoded_string = x_www_form_urlencoded_string.searchInvoiceByTransactionUuid(header_id);
			result = search_invoice.searchInvoicebyTrasactionUuid(urlencoded_string);
			break;

		default:
			System.out.println("Out of Scope");
		}

		System.out.println(json_string);
		System.out.println(urlencoded_string);
		System.out.println(result);
		
	}
}
