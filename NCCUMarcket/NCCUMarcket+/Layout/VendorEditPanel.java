// ✅ 修正：VendorEditPanel 初始化帶入 stallId，避免 Firebase 空寫入
package Layout;

import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import java.io.InputStreamReader;
import java.util.Map;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VendorEditPanel extends JPanel {

    public JButton absoluteBackBtn;
    public JLayeredPane layeredPane;
    public JLabel title;
    public JPanel formPanel;
    public JTextField stallIdField;
    public JTextField nameField;
    public JCheckBox eatTag, drinkTag, cultureTag, fashionTag, otherTag;
    public JTextArea promoArea;
    public JTextField contactField;
    public JCheckBox mobilePay;
    public JButton submitBtn;
    public JPanel bottomPanel;
    public JPanel titlePanel;
    private MainFrame frame;

    public VendorEditPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        title = new JLabel("攤販資料更新", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        stallIdField = new JTextField();
        stallIdField.setEditable(false);
        stallIdField.setText(frame.getSelectedVendorId()); // ✅ 從登入者帶入 ID

        nameField = new JTextField();

        eatTag = new JCheckBox("好吃");
        drinkTag = new JCheckBox("好喝");
        cultureTag = new JCheckBox("文創");
        fashionTag = new JCheckBox("穿搭時尚");
        otherTag = new JCheckBox("其他");

        promoArea = new JTextArea();
        contactField = new JTextField();
        mobilePay = new JCheckBox("是否支援行動支付");

        formPanel.add(new JLabel("攤位編號："));
        formPanel.add(stallIdField);
        formPanel.add(new JLabel("名稱："));
        formPanel.add(nameField);
        formPanel.add(new JLabel("攤販標籤："));
        JPanel tagPanel = new JPanel();
        tagPanel.add(eatTag);
        tagPanel.add(drinkTag);
        tagPanel.add(cultureTag);
        tagPanel.add(fashionTag);
        tagPanel.add(otherTag);
        formPanel.add(tagPanel);
        formPanel.add(new JLabel("文宣內容/連結："));
        formPanel.add(new JScrollPane(promoArea));
        formPanel.add(new JLabel("聯絡方式："));
        formPanel.add(contactField);
        formPanel.add(new JLabel("付款方式："));
        formPanel.add(mobilePay);

        submitBtn = new JButton("更新資料");
        submitBtn.setForeground(Color.BLACK);
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = stallIdField.getText().trim();
                    if (id.isEmpty()) {
                        JOptionPane.showMessageDialog(VendorEditPanel.this, "錯誤：找不到攤位編號！");
                        return;
                    }

                    String name = nameField.getText();
                    String description = promoArea.getText();
                    String contact = contactField.getText();
                    boolean supportPay = mobilePay.isSelected();

                    StringBuilder tagsBuilder = new StringBuilder();
                    if (eatTag.isSelected()) tagsBuilder.append("好吃 ");
                    if (drinkTag.isSelected()) tagsBuilder.append("好喝 ");
                    if (cultureTag.isSelected()) tagsBuilder.append("文創 ");
                    if (fashionTag.isSelected()) tagsBuilder.append("穿搭時尚 ");
                    if (otherTag.isSelected()) tagsBuilder.append("其他 ");

                    String tags = tagsBuilder.toString().trim();

                    String json = String.format("{\"record_id\":\"%s\",\"name\":\"%s\",\"tags\":\"%s\",\"description\":\"%s\",\"contact_info\":\"%s\",\"support_mobile_payment\":%b}",
                            id, name, tags, description, contact, supportPay);

                    URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + id + ".json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    conn.setDoOutput(true);

                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = json.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }

                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        JOptionPane.showMessageDialog(VendorEditPanel.this, "✅ 成功更新資料到 Firebase！");
                    } else {
                        JOptionPane.showMessageDialog(VendorEditPanel.this, "❌ 傳送失敗，HTTP Code: " + responseCode);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(VendorEditPanel.this, "🚨 發生錯誤：" + ex.getMessage());
                }
            }
        });

        bottomPanel = new JPanel();
        bottomPanel.add(submitBtn);

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(5, 0, 380, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
        formPanel.setBounds(5, 60, 380, 500);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);
        bottomPanel.setBounds(5, 570, 380, 60);
        layeredPane.add(bottomPanel, JLayeredPane.DEFAULT_LAYER);

        absoluteBackBtn = new JButton("←");
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> {
            clearFields();
            frame.switchTo("Login");
        });
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
        
    }

    public void clearFields() {
        stallIdField.setText("");
        nameField.setText("");
        promoArea.setText("");
        contactField.setText("");
        mobilePay.setSelected(false);
        eatTag.setSelected(false);
        drinkTag.setSelected(false);
        cultureTag.setSelected(false);
        fashionTag.setSelected(false);
        otherTag.setSelected(false);
    }

    public void refresh(String id) {
        try {
            stallIdField.setText(id); // ✅ 同步設定 ID

            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + id + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> vendor = gson.fromJson(reader, type);
            reader.close();

            nameField.setText((String) vendor.get("name"));
            contactField.setText((String) vendor.get("contact_info"));
            promoArea.setText((String) vendor.get("description"));

            String tags = (String) vendor.get("tags");
            eatTag.setSelected(tags != null && tags.contains("好吃"));
            drinkTag.setSelected(tags != null && tags.contains("好喝"));
            cultureTag.setSelected(tags != null && tags.contains("文創"));
            fashionTag.setSelected(tags != null && tags.contains("穿搭時尚"));
            otherTag.setSelected(tags != null && tags.contains("其他"));

            Object mobile = vendor.get("support_mobile_payment");
            mobilePay.setSelected(mobile instanceof Boolean && (Boolean) mobile);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "讀取商家資料失敗：" + e.getMessage());
        }
    }
}

