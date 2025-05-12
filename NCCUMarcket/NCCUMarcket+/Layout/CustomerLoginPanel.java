package Layout;

import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

public class CustomerLoginPanel extends JPanel {

    public JLayeredPane layeredPane;
    public JLabel title;
    public JPanel formPanel;
    public JTextField nicknameField;
    public JButton loginBtn;
    public JPanel bottomPanel;
    public JPanel titlePanel;
    public JButton absoluteBackBtn;

    public CustomerLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        title = new JLabel("顧客登入介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout());
        formPanel.add(new JLabel("輸入暱稱："));

        nicknameField = new JTextField(20);
        formPanel.add(nicknameField);

        loginBtn = new JButton("登入");
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
                            clearFields();
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

        bottomPanel = new JPanel();
        bottomPanel.add(loginBtn);

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(0, 0, 405, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
        formPanel.setBounds(0, 60, 405, 100);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);
        bottomPanel.setBounds(0, 160, 405, 60);
        layeredPane.add(bottomPanel, JLayeredPane.DEFAULT_LAYER);

        absoluteBackBtn = new JButton("←");
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }

    public void clearFields() {
        nicknameField.setText("");
    }
}
