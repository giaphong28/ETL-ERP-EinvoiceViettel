import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author ADMIN
 */
public class taxBreakdownInfo {

    public static JSONArray getTaxBreakdownInfo()
            throws Exception {
        JSONArray tax_arr_json = new JSONArray();
        //Summarize Info
        try {

            HashMap<String, String> items = ItemInfo.items;
            HashMap<String, Long[]> map_tax_rate = new HashMap<String, Long[]>();

            String taxPercent_list = ",";
                if (!taxPercent_list.contains("," + items.get("RATE") + ",")) {
                    taxPercent_list += items.get("RATE") + ",";
                    Long[] amount = new Long[2];
                    amount[0] = Long.parseLong(items.get("AMOUNT"));
                    amount[1] = Long.parseLong(items.get("TAX_AMOUNT"));
                    map_tax_rate.put(items.get("RATE"), amount);
                } else {
                    Long[] amount = map_tax_rate.get(items.get("RATE"));
                    amount[0] += Long.parseLong(items.get("AMOUNT"));
                    amount[1] += Long.parseLong(items.get("TAX_AMOUNT"));
                    map_tax_rate.put(items.get("RATE"), amount);
                }
            
            String[] tax_pct = taxPercent_list.substring(1,taxPercent_list.length()).split(",");
            for (String i : tax_pct) {
                JSONObject tax_obj_json = new JSONObject();
                tax_obj_json.put("taxPercentage", Integer.parseInt(i));
                tax_obj_json.put("taxableAmount", map_tax_rate.get(i)[0]);
                tax_obj_json.put("taxAmount", map_tax_rate.get(i)[1]);
                tax_arr_json.put(tax_obj_json);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tax_arr_json;

    }
}
