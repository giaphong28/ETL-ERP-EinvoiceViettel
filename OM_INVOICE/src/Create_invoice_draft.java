import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;


public class Create_invoice_draft {
	public static String invokeApi(String json_string) throws Exception{

	    JSONObject obj = new JSONObject();
	    String err_code = "OK";
	    
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
//    System.out.println(json_string);
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
	obj = new JSONObject(response);
    if(responseCode>299){
    	err_code = obj.getString("message");				
    	}
//    System.out.println("Output from Server .... \n");
//    System.out.println(response);
    
    if (responseCode != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException("Failed : HTTP error code : " + responseCode);
    }


} catch (Exception e) {
//    e.printStackTrace();        
    }
return err_code;
}
}
