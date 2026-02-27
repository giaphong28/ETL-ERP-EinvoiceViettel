import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author ADMIN
 */
public class summarizeInfo {

    public static JSONObject getSummarizeInfo()
            throws Exception {
        JSONObject summariesInvoice_json = new JSONObject();
        //Summarize Info
        try {
            Long sumOfTotalLineAmountWithoutTax = 0L;
            Long totalAmountWithoutTax = 0L;
            Long totalTaxAmount = 0L;
            Long totalAmountWithTax = 0L;
            Long totalAmountAfterDiscount = 0L;
            Long DiscountAmount = 0L;
            String extraName = "";
            String extraValue = "";
            int countValue = ItemInfo.countValue;
            HashMap<String, String[]> items = ItemInfo.items;
            for (int i = 0; i < countValue; i++) {
                sumOfTotalLineAmountWithoutTax += Long.parseLong(items.get("AMOUNT")[i]);
                totalAmountWithoutTax += Long.parseLong(items.get("AMOUNT")[i]);
                totalTaxAmount += Long.parseLong(items.get("TAX_AMOUNT")[i]);
                totalAmountWithTax += Long.parseLong(items.get("AMOUNT_WITH_TAX")[i]);
                totalAmountAfterDiscount += Long.parseLong(items.get("AMOUNT_AFTER_DISCOUNT")[i]);
                DiscountAmount += Long.parseLong(items.get("PERCENT_DISCOUNT")[i])*
						Long.parseLong(items.get("AMOUNT")[i]);
            }
            
            
            summariesInvoice_json.put("sumOfTotalLineAmountWithoutTax", sumOfTotalLineAmountWithoutTax);
            summariesInvoice_json.put("totalAmountWithoutTax", totalAmountWithoutTax);
            summariesInvoice_json.put("totalTaxAmount", totalTaxAmount);
            summariesInvoice_json.put("totalAmountWithTax", totalAmountWithTax);
            summariesInvoice_json.put("totalAmountAfterDiscount", totalAmountAfterDiscount);
            summariesInvoice_json.put("discountAmount", DiscountAmount);
            summariesInvoice_json.put("extraName", extraName);
            summariesInvoice_json.put("extraValue", extraValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summariesInvoice_json;

    }
}
