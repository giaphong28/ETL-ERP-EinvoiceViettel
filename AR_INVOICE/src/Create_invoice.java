import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;


public class Create_invoice {
	public static String getStackTrace(final Throwable throwable) {
	    final StringWriter sw = new StringWriter();
	    final PrintWriter pw = new PrintWriter(sw, true);
	    throwable.printStackTrace(pw);
	    return sw.getBuffer().toString();
	}
	public static String invoke_invoice_draft(String json_string) throws Exception{
		String err_code = "SUCCESS";
		try{
	String username = "username";
    String password = "password";
    String const_url = "https://api-vinvoice.viettel.vn/"
            + "services/einvoiceapplication/api/";
    String method_url = "InvoiceAPI/InvoiceWS/createOrUpdateInvoiceDraft/";
    
    String taxCode = URLEncoder.encode("username", "UTF-8");
    
    String userpass = username + ":" + password;
	String basicAuth = "Basic "
			+ Base64.encodeBase64String(userpass.getBytes("UTF-8"));
    //Request config
    URL complete_url = new URL(const_url + method_url + taxCode );
    HttpURLConnection con = (HttpURLConnection) complete_url.openConnection();
    con.setRequestMethod("POST");

    /**
     * *******Attention*********
     */
    // Header Config
    con.setRequestProperty("Content-type", "application/json");
    con.setRequestProperty("Accept", "application/json");
	con.setRequestProperty("Authorization", basicAuth);
    con.setRequestProperty("charset", "UTF-8");

    con.setConnectTimeout(30000);
    con.setReadTimeout(30000);

    con.setDoOutput(true);
    DataOutputStream out = new DataOutputStream(con.getOutputStream());

    /**
     * ***********
     * Attention: don't delete two comment below it's may be useful when
     * InvoiceInfo not give request properly uncomment and replace
     * InvoiceInfo by payloadBytes ***********
     */
//    String jsonPayload = InvoiceInfo.toString();
//    byte[] payloadBytes = jsonPayload.getBytes("UTF-8");
    out.write(json_string.getBytes("UTF-8"));

    out.flush();
    out.close();

    int responseCode = con.getResponseCode();
	BufferedReader br = null;

    if (responseCode>299){
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        
    } else {
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
    }
    String response = br.readLine();
    if(responseCode>299){
		JSONObject obj = new JSONObject(response);
    	err_code ="Error code: "+String.valueOf(obj.getInt("code"))+
    			".Data: "+ obj.getString("data");
    	return err_code;				
    	}
} catch (Exception e) {
    err_code = getStackTrace(e);       
    }
		return err_code;
}

	public static String invoke_invoice(String json_string) throws Exception{
	
		    String err_code = "SUCCESS";
			try{
		String username = "username";
	    String password = "password";
	    String const_url = "https://api-vinvoice.viettel.vn/"
	            + "services/einvoiceapplication/api/";
	    String method_url = "InvoiceAPI/InvoiceWS/createInvoice/";
	    
	    String taxCode = URLEncoder.encode("username", "UTF-8");
	    		
	
	    String userpass = username + ":" + password;
		String basicAuth = "Basic "
				+ Base64.encodeBase64String(userpass.getBytes("UTF-8"));
	    //Request config
	    URL complete_url = new URL(const_url + method_url + taxCode );
	    HttpURLConnection con = (HttpURLConnection) complete_url.openConnection();
	    con.setRequestMethod("POST");
	
	    /**
	     * *******Attention*********
	     */
	    // Header Config
	    con.setRequestProperty("Content-type", "application/json");
	    con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Authorization", basicAuth);
	    con.setRequestProperty("charset", "UTF-8");
	
	    con.setConnectTimeout(30000);
	    con.setReadTimeout(30000);
	
	    con.setDoOutput(true);
	    DataOutputStream out = new DataOutputStream(con.getOutputStream());
	
	    /**
	     * ***********
	     * Attention: don't delete two comment below it's may be useful when
	     * InvoiceInfo not give request properly uncomment and replace
	     * InvoiceInfo by payloadBytes ***********
	     */
	//    String jsonPayload = InvoiceInfo.toString();
	//    byte[] payloadBytes = jsonPayload.getBytes("UTF-8");
	    out.write(json_string.getBytes("UTF-8"));
	
	    out.flush();
	    out.close();
	
	    int responseCode = con.getResponseCode();
		BufferedReader br = null;
	
	    if (responseCode>299){
	        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	        
	    } else {
	        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    }
	    String response = br.readLine();
	    if(responseCode>299){
			JSONObject obj = new JSONObject(response);
	    	err_code ="Error code: "+String.valueOf(obj.getInt("code"))+
	    			".Data: "+ obj.getString("data");
	    	return err_code;				
	    	}
	    JSONObject obj = new JSONObject(response);
	    JSONObject res_result = obj.getJSONObject("result");
	    String  result_invoiceNo = res_result.getString("invoiceNo");
	    String  result_supplierTaxCode = res_result.getString("supplierTaxCode");
	    String  result_codeOfTax = res_result.optString("codeOfTax","");
	    String  result_transactionID = res_result.getString("transactionID");
	    String  result_reservationCode = res_result.getString("reservationCode");
	    String column_name = "EINVOICE_NUMBER";
		
		String table_name = "AR.AR_INVOICE_HEADERS";
		String filter_statement = " where" + " HEADER_ID = " + AR_MAIN.header_id;

		// tạo kết nối
		Connection conn = oracle_connection.getOracleConnection();
		Statement stmt = conn.createStatement();
		
		// lấy kết quả từ database
		int result1 = stmt.executeUpdate("update "+ table_name +" set " + column_name
				+ " = '" + result_invoiceNo +"'"+ filter_statement);
	 } catch (Exception e) {
		err_code = getStackTrace(e);
	}
	return err_code;
	}
}
