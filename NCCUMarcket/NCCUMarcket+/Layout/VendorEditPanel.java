package Layout;

import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

public class VendorEditPanel extends JPanel {
    public VendorEditPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JLabel title = new JLabel("攤販資料建立/更新", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JTextField stallIdField = new JTextField();
        JTextField nameField = new JTextField();

        JCheckBox eatTag = new JCheckBox("好吃");
        JCheckBox drinkTag = new JCheckBox("好喝");
        JCheckBox cultureTag = new JCheckBox("文創");
        JCheckBox fashionTag = new JCheckBox("穿搭時尚");
        JCheckBox otherTag = new JCheckBox("其他");

        JTextArea promoArea = new JTextArea();
        JTextField contactField = new JTextField();
        JCheckBox mobilePay = new JCheckBox("是否支援行動支付");

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

        JButton submitBtn = new JButton("建立/更新資料");
        submitBtn.setForeground(Color.BLACK);

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = stallIdField.getText();
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

                    String json = String.format("{\"name\":\"%s\",\"tags\":\"%s\",\"description\":\"%s\",\"contact_info\":\"%s\",\"support_mobile_payment\":%b}",
                            name, tags, description, contact, supportPay);

                    URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors.json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    conn.setDoOutput(true);

                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = json.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }

                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        JOptionPane.showMessageDialog(VendorEditPanel.this, "✅ 成功新增資料到 Firebase！");

                        // 清空輸入欄位
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
                    } else {
                        JOptionPane.showMessageDialog(VendorEditPanel.this, "❌ 傳送失敗，HTTP Code: " + responseCode);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(VendorEditPanel.this, "🚨 發生錯誤：" + ex.getMessage());
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submitBtn);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(5, 0, 380, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
        formPanel.setBounds(5, 60, 380, 600);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);
        bottomPanel.setBounds(5, 370, 380, 60);
        layeredPane.add(bottomPanel, JLayeredPane.DEFAULT_LAYER);

        JButton absoluteBackBtn = new JButton("←");
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }
}

