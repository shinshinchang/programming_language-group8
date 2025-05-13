package Layout;

import java.awt.event.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;
import java.net.URL;
import java.lang.reflect.Type;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CustomerDetailPanel extends VendorEditPanel {
    public CustomerDetailPanel(MainFrame frame) {
        super(frame);

        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("CustomerBrowse"));

        super.stallIdField.setEditable(false);

        super.nameField.setEditable(false);
        super.eatTag.setEnabled(false);
        super.drinkTag.setEnabled(false);
        super.cultureTag.setEnabled(false);
        super.fashionTag.setEnabled(false);
        super.otherTag.setEnabled(false);

        super.promoArea.setEditable(false);
        super.contactField.setEditable(false);
        super.mobilePay.setEnabled(false);
        super.submitBtn.setVisible(false);

        
    }

   public void refresh(String id) {
        try {
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + id + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> vendor = gson.fromJson(reader, type);
            reader.close();

            stallIdField.setText(id);
            nameField.setText((String) vendor.get("name"));
            contactField.setText((String) vendor.get("contact_info"));
            promoArea.setText((String) vendor.get("description"));

            // 標籤處理
            String tags = (String) vendor.get("tags");
            eatTag.setSelected(tags.contains("好吃"));
            drinkTag.setSelected(tags.contains("好喝"));
            cultureTag.setSelected(tags.contains("文創"));
            fashionTag.setSelected(tags.contains("穿搭時尚"));
            otherTag.setSelected(tags.contains("其他"));

            // 支援付款方式
            Object mobile = vendor.get("support_mobile_payment");
            mobilePay.setSelected(mobile instanceof Boolean && (Boolean) mobile);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "讀取商家資料失敗：" + e.getMessage());
        }
    }
}