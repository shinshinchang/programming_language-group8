package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CustomerLoginPanel extends JPanel {
    private JLayeredPane layeredPane;
    private JTextField nicknameField;
    private JButton loginBtn;
    private JPanel formPanel, bottomPanel, titlePanel;
    private JLabel title;
    private JButton absoluteBackBtn;

    public CustomerLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        title = new JLabel("顧客登入介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        formPanel = new JPanel(new FlowLayout());
        formPanel.add(new JLabel("輸入暱稱："));

        nicknameField = new JTextField(20);
        formPanel.add(nicknameField);

        loginBtn = new JButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nickname = nicknameField.getText();
                if (nickname.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerLoginPanel.this, "請輸入暱稱！");
                    return;
                }

                // 記錄暱稱進 frame
                frame.setCustomerNickname(nickname);

                // 檢查是否已存在該暱稱
                try {
                    URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/customers.json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json");

                    Scanner scanner = new Scanner(conn.getInputStream());
                    StringBuilder json = new StringBuilder();
                    while (scanner.hasNext()) {
                        json.append(scanner.nextLine());
                    }
                    scanner.close();

                    if (!json.toString().contains("\"" + nickname + "\"")) {
                        // 不存在則新增
                        URL postUrl = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/customers.json");
                        HttpURLConnection postConn = (HttpURLConnection) postUrl.openConnection();
                        postConn.setRequestMethod("POST");
                        postConn.setRequestProperty("Content-Type", "application/json");
                        postConn.setDoOutput(true);

                        String body = "{\"nickname\":\"" + nickname + "\"}";
                        try (OutputStream os = postConn.getOutputStream()) {
                            byte[] input = body.getBytes("utf-8");
                            os.write(input, 0, input.length);
                        }

                        int responseCode = postConn.getResponseCode();
                        if (responseCode != 200) {
                            JOptionPane.showMessageDialog(CustomerLoginPanel.this, "寫入暱稱失敗：" + responseCode);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CustomerLoginPanel.this, "檢查暱稱時發生錯誤");
                }

                clearFields();
                frame.switchTo("CustomerBrowse");
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
