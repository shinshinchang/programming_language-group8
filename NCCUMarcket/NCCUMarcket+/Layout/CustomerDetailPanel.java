package Layout;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CustomerDetailPanel extends VendorEditPanel {
    // private JPanel commentDisplayPanel;

    // StyledButton absoluteBackBtn = new StyledButton("←");

    public CustomerDetailPanel(MainFrame frame) {
        super(frame);
        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("CustomerBrowse"));

        super.title.setText("商家瀏覽頁面");
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

        // 顧客留言按鈕
        JButton commentButton = new StyledButton("我要留言");

        commentButton.setPreferredSize(new Dimension(300, 30));
        super.bottomPanel.add(commentButton);

        commentButton.addActionListener(e -> openCommentDialog());

    }

    public void setVendorData(String stallId) {
        try {
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + stallId
                    + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            Map<String, Object> vendorData = gson.fromJson(response.toString(), new TypeToken<Map<String, Object>>() {
            }.getType());

            if (vendorData != null) {
                String name = (String) vendorData.get("name");
                super.title.setText(name != null ? name : "商家名稱未提供");

                // 同步設定欄位（如你要顯示詳細資料）
                stallIdField.setText(stallId);
                nameField.setText(name);
                refresh(stallId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ 無法載入商家資訊！");
        }
    }

    private void openCommentDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "新增留言", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        JTextArea commentArea = new JTextArea(5, 30);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);

        // 自動取得顧客名稱（暱稱）
        String nickname = frame.getCustomerNickname();

        panel.add(new JLabel("您的暱稱：" + nickname));
        panel.add(new JLabel("輸入留言："));
        panel.add(new JScrollPane(commentArea));

        JButton submitBtn = new JButton("送出留言");
        submitBtn.addActionListener(e -> {
            String comment = commentArea.getText().trim();

            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "請輸入留言內容！");
                return;
            }
            sendCommentToDatabase(stallIdField.getText(), comment, nickname, dialog);
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(submitBtn, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void sendCommentToDatabase(String id, String comment, String nickname, JDialog dialog) {
        try {
            // 1. 組成 Map 物件（鍵值對）
            Map<String, String> data = new HashMap<>();
            data.put("comment", comment);
            data.put("name", nickname);

            // 2. 轉換為 JSON 字串
            Gson gson = new Gson();
            String json = gson.toJson(data);

            // 3. 建立連線
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + id
                    + "/comments.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int code = conn.getResponseCode();
            if (code == 200) {
                JOptionPane.showMessageDialog(dialog, "✅ 留言成功！");
                dialog.dispose();
                refreshComments(id);
            } else {
                JOptionPane.showMessageDialog(dialog, "❌ 留言失敗，HTTP Code: " + code);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "🚨 發生錯誤：" + ex.getMessage());
        }
    }

}