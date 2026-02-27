import org.jdesktop.jdic.desktop.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONObject;

/**
 * 
 * @author ADMIN
 */
public class Preview_invoice {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static String getStackTrace(final Throwable throwable) {
		    final StringWriter sw = new StringWriter();
		    final PrintWriter pw = new PrintWriter(sw, true);
		    throwable.printStackTrace(pw);
		    return sw.getBuffer().toString();
		}
	 
	public static String preview_invoice_draft(String json_string, String header_id) throws Exception {
		// TODO code application logic here
		String fileName = "";
		// general invoice info
		String err_code = "SUCCESS";
		try {

			String username = "username";
			String password = "password";
			String const_url = "https://api-vinvoice.viettel.vn/"
					+ "services/einvoiceapplication/api/";
			String method_url = "InvoiceAPI/InvoiceUtilsWS/createInvoiceDraftPreview/";

			String taxCode = URLEncoder.encode("username", "UTF-8");
			
			System.setProperty("javax.net.ssl.trustStore",
					"E:\\FORMS\\EINVOICE\\plugin\\cacerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			URL complete_url = new URL(const_url + method_url + taxCode
			);
			HttpURLConnection con = (HttpURLConnection) complete_url
					.openConnection();
			con.setRequestMethod("POST");

			String userpass = username + ":" + password;
			String basicAuth = "Basic "
					+ Base64.encodeBase64String(userpass.getBytes("UTF-8"));
//			System.out.println(basicAuth);
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
			 * *********** Attention: don't delete two comment below it's may be
			 * useful when InvoiceInfo not give request properly uncomment and
			 * replace InvoiceInfo by payloadBytes ***********
			 */
			// String jsonPayload = InvoiceInfo.toString();
			// byte[] payloadBytes = jsonPayload.getBytes("UTF-8");
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
		    System.out.println("Output from Server .... \n");
		    System.out.println(response);
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ responseCode);
			}
			JSONObject obj = new JSONObject(response);
			String fileToBytes = obj.optString("fileToBytes", "");
			fileName = obj.optString("fileName", "");

			
			// System.out.println(res);

			byte[] decodedBytes = Base64.decodeBase64(fileToBytes);
			File file_v2 = new File("E:\\Report\\einvoice_pdf\\" +
					header_id+"_"+fileName);
			
			FileUtils.writeByteArrayToFile(file_v2, decodedBytes);
			fileName = file_v2.toString();
			if (file_v2.toString().endsWith(".pdf")) {
				Runtime.getRuntime().exec(
						"rundll32 url.dll,FileProtocolHandler " + file_v2);
			} else {
				System.out.print("File Not Exist");
			}

		} catch (Exception e) {
			err_code = getStackTrace(e);
		}
		return err_code;
	}

	public static String preview_invoice(String json_string,String header_id ) throws Exception {
			// TODO code application logic here
			String fileName = "";
			// general invoice info
	
			String err_code = "SUCCESS";
			try {
	
				String username = "username";
				String password = "password";
				String const_url = "https://api-vinvoice.viettel.vn/"
						+ "services/einvoiceapplication/api/";
				String method_url = "InvoiceAPI/InvoiceUtilsWS/getInvoiceRepresentationFile";
	
	
				// Request config
				URL complete_url = new URL(const_url + method_url 
				// + "?token="+result_token
				);
				HttpURLConnection con = (HttpURLConnection) complete_url
						.openConnection();
				con.setRequestMethod("POST");
	//			System.out.println(complete_url);
	
				String userpass = username + ":" + password;
				String basicAuth = "Basic "
						+ Base64.encodeBase64String(userpass.getBytes("UTF-8"));
	//			System.out.println(basicAuth);
				/**
				 * *******Attention*********
				 */
				// Header Config
				con.setRequestProperty("Content-type", "application/json");
				con.setRequestProperty("Accept", "application/json");
				con.setRequestProperty("Authorization", basicAuth);
				con.setRequestProperty("charset", "UTF-8");
				con.setDoInput(true);
				con.setConnectTimeout(30000);
				con.setReadTimeout(30000);
	
				con.setDoOutput(true);
				DataOutputStream out = new DataOutputStream(con.getOutputStream());
	
				/**
				 * *********** Attention: don't delete two comment below it's may be
				 * useful when InvoiceInfo not give request properly uncomment and
				 * replace InvoiceInfo by payloadBytes ***********
				 */
				// String jsonPayload = InvoiceInfo.toString();
				 byte[] payloadBytes = json_string.getBytes("UTF-8");
	//			 System.out.println(json_string);
	//			 System.out.println(payloadBytes);
				out.write(payloadBytes);
	
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
			    System.out.println("Output from Server .... \n");
			    System.out.println(response);
				if (responseCode != HttpURLConnection.HTTP_OK) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ responseCode);
				}
	
	

				JSONObject obj = new JSONObject(response);
				String fileToBytes = obj.optString("fileToBytes", "");
				fileName = obj.optString("fileName", "");
	
			
				byte[] decodedBytes = Base64.decodeBase64(fileToBytes);
				File file_v2 = new File("E:\\Report\\einvoice_pdf\\" +
						header_id+"_"+fileName);
				
				FileUtils.writeByteArrayToFile(file_v2, decodedBytes);
				fileName = file_v2.toString();
	
			} catch (Exception e) {
				err_code = getStackTrace(e);
			}
			return err_code;
		}

}