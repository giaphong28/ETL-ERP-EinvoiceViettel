import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;


public class search_invoice {
	public static String searchInvoicebyTrasactionUuid(String urlencoded_string){
		
	    String err_code = "SUCCESS";
	    
		try{
	String username = "username";
    String password = "password";
    String const_url = "https://api-vinvoice.viettel.vn/"
            + "services/einvoiceapplication/api/";
    String method_url = "InvoiceAPI/InvoiceWS/searchInvoiceByTransactionUuid";
    
    String taxCode = URLEncoder.encode("username", "UTF-8");
    		

    String userpass = username + ":" + password;
	String basicAuth = "Basic "
			+ Base64.encodeBase64String(userpass.getBytes("UTF-8"));
    //Request config
    URL complete_url = new URL(const_url + method_url );
    HttpURLConnection con = (HttpURLConnection) complete_url.openConnection();
    con.setRequestMethod("POST");

    /**
     * *******Attention*********
     */
    // Header Config
    con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
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
//    System.out.println(json_string);
    out.write(urlencoded_string.getBytes("UTF-8"));

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
//    System.out.println("Output from Server .... \n");

//    JSONArray res = new JSONArray(br.toString());
    JSONObject obj = new JSONObject(response);
    JSONArray res_result = obj.getJSONArray("result");
    JSONObject obj_result = res_result.getJSONObject(0);   
    String  result_invoiceNo = obj_result.getString("invoiceNo");
    String  result_supplierTaxCode = obj_result.getString("supplierTaxCode");
    String  result_codeOfTax = obj_result.optString("codeOfTax","");
    String  result_transactionUuid = obj.getString("transactionUuid");
    String  result_reservationCode = obj_result.getString("reservationCode");
    System.out.println(result_invoiceNo);            
    System.out.println(result_supplierTaxCode);            
    System.out.println(result_transactionUuid);            
    System.out.println(result_reservationCode);           
    System.out.println(result_codeOfTax);           
//    System.out.println(obj.toString(1));
//    System.out.println(res_result.toString(1));
    System.out.println(response);
} catch (Exception e) {
    e.printStackTrace();        
    }

return err_code;
	}
}
