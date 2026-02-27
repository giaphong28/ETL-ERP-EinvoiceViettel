
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author ADMIN
 */
public class paymentInfo {
     public static JSONArray getPaymentInfo()
            throws Exception {         
        JSONArray paymentInvoice_json = new JSONArray();
        JSONObject paymentInvoice = new JSONObject();
        //Summarize Info
        try {            
            String paymentMethod = "2";
            String paymentMethodName = "CK";            
            
            paymentInvoice.put("paymentMethod", paymentMethod);
            paymentInvoice.put("paymentMethodName", paymentMethodName);
            paymentInvoice_json.put(paymentInvoice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentInvoice_json;
    }
}