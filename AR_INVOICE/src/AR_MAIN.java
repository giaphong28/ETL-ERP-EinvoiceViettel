import java.sql.Connection;
import java.sql.Statement;


public class AR_MAIN {

	/**
	 * @param args
	 */
	public static String header_id ="";
	public static void main(String[] args) throws Exception {

		// mã id và phương thức được đưa vào từ hàm gọi cmd runOnOtherJDK
		header_id = args[0];
		int method_id = Integer.parseInt(args[1]);

		// tạo chuỗi rỗng cho chuỗi json và chuỗi dữ liệu mà API Viettel trả về
		String json_string = "";
		String result = "";

		// dựa vào mã phương thức chia ra thực hiện
		switch (method_id) {
		case 1:// tạo hóa đơn nháp
			json_string = create_json_string.invoice_draft(header_id);
			result = Create_invoice.invoke_invoice_draft(json_string);
			break;
		case 2:// xem trước hóa đơn nháp bằng pdf
			json_string = create_json_string.invoice_draft(header_id);
			result = Preview_invoice.preview_invoice_draft(json_string,
					header_id);
			break;
		case 3:// phát hành hóa đơn điện tử
			json_string = create_json_string.invoice_draft(header_id);
			result = Create_invoice.invoke_invoice(json_string);
			break;
		case 4:// xem trước hóa đơn điện tử đã phát hành bằng zip
			json_string = create_json_string.invoice(header_id);
			result = Preview_invoice.preview_invoice(json_string, header_id);
			break;

		default:
			System.out.println("Out of Scope");
		}

		String column_name = "EINVOICE_API_STATUS";
		
		String table_name = "AR.AR_INVOICE_HEADERS";
		String filter_statement = " where" + " HEADER_ID = " + header_id;

		// tạo kết nối vào database
		Connection conn = oracle_connection.getOracleConnection();
		Statement stmt = conn.createStatement();
		
		// lấy thông tin trả về từ database
		int result1 = stmt.executeUpdate("update "+ table_name +" set " + column_name
				+ " = '" + result +"'"+ filter_statement);		
		
		System.out.println(json_string);
		System.out.println(result);
	}
}
