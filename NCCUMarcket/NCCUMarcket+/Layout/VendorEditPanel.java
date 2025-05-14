// ✅ 美化後的 VendorLoginPanel 與 VendorEditPanel，含 StyledButton、字體與版面調整
package Layout;

import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VendorEditPanel extends JPanel {
    public MainFrame frame;
    protected JPanel contentPanel;
    private JScrollPane scrollPane;

    public StyledButton absoluteBackBtn;
    public JLabel title;
    public JPanel formPanel;
    public JTextField stallIdField;
    public JTextField nameField;
    public JCheckBox eatTag, drinkTag, cultureTag, fashionTag, otherTag;
    public JTextArea promoArea;
    public JTextField contactField;
    public JCheckBox mobilePay;
    public StyledButton submitBtn;
    public JPanel bottomPanel;
    public JPanel titlePanel;

    public JPanel commentPanel;
    public JLabel commentTitle;

    public VendorEditPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setPreferredSize(new Dimension(400, 900));

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        title = new JLabel("攤販資料更新", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));

        formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        stallIdField = new JTextField();
        stallIdField.setEditable(false);
        stallIdField.setText(frame.getSelectedVendorId());

        nameField = new JTextField();
        eatTag = new JCheckBox("好吃");
        drinkTag = new JCheckBox("好喝");
        cultureTag = new JCheckBox("文創");
        fashionTag = new JCheckBox("穿搭時尚");
        otherTag = new JCheckBox("其他");

        promoArea = new JTextArea();
        contactField = new JTextField();
        mobilePay = new JCheckBox("是否支援行動支付");

        JLabel idLabel = new JLabel("攤位編號：");
        idLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 16));
        formPanel.add(idLabel);

        formPanel.add(stallIdField);
        
        JLabel nameLabel = new JLabel("名稱：");
        nameLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 16));
        formPanel.add(nameLabel);

        formPanel.add(nameField);

        JLabel sellLabel = new JLabel("攤販標籤：");
        sellLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 16));
        formPanel.add(sellLabel);

        JPanel tagPanel = new JPanel();
        tagPanel.setBounds(10, 60, 360, 200);
        tagPanel.add(eatTag);
        tagPanel.add(drinkTag);
        tagPanel.add(cultureTag);
        tagPanel.add(fashionTag);
        tagPanel.add(otherTag);
        formPanel.add(tagPanel);

        JLabel textLabel = new JLabel("文宣內容/連結：");
        textLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 16));
        formPanel.add(textLabel);

        formPanel.add(new JScrollPane(promoArea));
        
        JLabel callLabel = new JLabel("聯絡方式：");
        callLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 16));
        formPanel.add(callLabel);

        formPanel.add(contactField);

        JLabel payLabel = new JLabel("付款方式：");
        payLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 16));
        formPanel.add(payLabel);
        
        formPanel.add(mobilePay);


        formPanel.setBounds(10, 60, 360, 400);
        contentPanel.add(formPanel);

        submitBtn = new StyledButton("更新資料");
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
                    if (eatTag.isSelected()) tagsBuilder.append("好吃");
                    if (drinkTag.isSelected()) tagsBuilder.append("好喝");
                    if (cultureTag.isSelected()) tagsBuilder.append("文創");
                    if (fashionTag.isSelected()) tagsBuilder.append("穿搭時尚");
                    if (otherTag.isSelected()) tagsBuilder.append("其他");

                    String tags = tagsBuilder.toString().trim();

                    String json = String.format(
                        "{\"record_id\":\"%s\",\"name\":\"%s\",\"tags\":\"%s\",\"description\":\"%s\",\"contact_info\":\"%s\",\"support_mobile_payment\":%b}",
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
        bottomPanel.setBounds(10, 470, 360, 50);
        bottomPanel.add(submitBtn);
        contentPanel.add(bottomPanel);

        commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setBounds(10, 540, 360, 300);
        commentTitle = new JLabel("顧客評價");
        commentTitle.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
        commentPanel.add(commentTitle);
        contentPanel.add(commentPanel);

        refreshComments(stallIdField.getText());

        absoluteBackBtn = new StyledButton("←");
        absoluteBackBtn.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        absoluteBackBtn.setPreferredSize(new Dimension(50, 30));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> {
            clearFields();
            frame.switchTo("Login");
        });
        contentPanel.add(absoluteBackBtn);

        titlePanel = new JPanel();
        titlePanel.setBounds(60, 10, 280, 40);
        titlePanel.add(title);
        contentPanel.add(titlePanel);
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
            stallIdField.setText(id);
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + id + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
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
        refreshComments(id);
    }

    public void refreshComments(String id) {
        commentPanel.removeAll();
        try {
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + id + "/comments.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> comments = gson.fromJson(reader, type);
            reader.close();

            if (comments != null) {
                for (Map<String, Object> entry : comments.values()) {
                    String text = (String) entry.get("comment");
                    String name = (String) entry.get("name");
                    JTextArea area = new JTextArea("👤 " + name + "：" + text, 2, 28);
                    area.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);
                    area.setEditable(false);
                    area.setBackground(new Color(245, 245, 245));
                    area.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    commentPanel.add(Box.createVerticalStrut(1));
                    commentPanel.add(area);
                }
            } else {
                commentPanel.add(new JLabel("目前尚無評論"));
            }
            commentPanel.revalidate();
            commentPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            commentPanel.add(new JLabel("讀取評論失敗：" + e.getMessage()));
        }
    }
}