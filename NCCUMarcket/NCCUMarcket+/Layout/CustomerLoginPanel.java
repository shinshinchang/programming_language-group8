package Layout;

import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

public class CustomerLoginPanel extends JPanel {
    public CustomerLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("顧客登入介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout());
        formPanel.add(new JLabel("輸入暱稱："));

        JTextField nicknameField = new JTextField(20);
        formPanel.add(nicknameField);

        JButton loginBtn = new JButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nickname = nicknameField.getText();
                if (nickname.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerLoginPanel.this, "請輸入暱稱！");
                } else {
                    try {
                        String json = String.format("{\"nickname\":\"%s\"}", nickname);

                        URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/customers.json");
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
                            
                            JOptionPane.showMessageDialog(CustomerLoginPanel.this, "✅ 成功登入並記錄暱稱！");
                            nicknameField.setText("");
                            frame.switchTo("CustomerBrowse");
                        } else {
                            JOptionPane.showMessageDialog(CustomerLoginPanel.this, "❌ 傳送失敗，HTTP Code: " + responseCode);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CustomerLoginPanel.this, "🚨 發生錯誤：" + ex.getMessage());
                    }
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loginBtn);

        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}