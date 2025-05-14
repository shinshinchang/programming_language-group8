package Layout;

import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.swing.*;

public class CustomerLoginPanel extends JPanel {
    private JLayeredPane layeredPane;
    private JTextField nicknameField;
    private StyledButton loginBtn;
    private StyledButton absoluteBackBtn;
    private JPanel titlePanel, formPanel, bottomPanel;
    private JLabel title;

    public CustomerLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(400, 600));

        // 標題區
        title = new JLabel("顧客登入", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBounds(0, 100, 400, 60);
        titlePanel.add(title);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);

        // 輸入欄位區
        formPanel = new JPanel(new FlowLayout());
        JLabel nicknameLabel = new JLabel("輸入暱稱：");
        nicknameLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
        nicknameField = new JTextField(20);
        formPanel.add(nicknameLabel);
        formPanel.add(nicknameField);
        formPanel.setBounds(0, 180, 400, 60);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);

        // 登入按鈕區
        loginBtn = new StyledButton("登入");
        bottomPanel = new JPanel();
        bottomPanel.add(loginBtn);
        bottomPanel.setBounds(0, 250, 400, 60);
        layeredPane.add(bottomPanel, JLayeredPane.DEFAULT_LAYER);

        // 返回鍵也使用 StyledButton，小尺寸
        absoluteBackBtn = new StyledButton("←");
        absoluteBackBtn.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        absoluteBackBtn.setPreferredSize(new Dimension(50, 30));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        // 登入邏輯
        loginBtn.addActionListener(e -> {
            String nickname = nicknameField.getText().trim();
            if (nickname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "請輸入暱稱！");
                return;
            }
            frame.setCustomerNickname(nickname);
            try {
                URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/customers.json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder json = new StringBuilder();
                while (scanner.hasNext()) json.append(scanner.nextLine());
                scanner.close();
                if (!json.toString().contains("\"" + nickname + "\"")) {
                    URL postUrl = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/customers.json");
                    HttpURLConnection postConn = (HttpURLConnection) postUrl.openConnection();
                    postConn.setRequestMethod("POST");
                    postConn.setRequestProperty("Content-Type", "application/json");
                    postConn.setDoOutput(true);
                    String body = "{\"nickname\":\"" + nickname + "\"}";
                    try (OutputStream os = postConn.getOutputStream()) {
                        os.write(body.getBytes("utf-8"));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "檢查暱稱時發生錯誤");
            }

            clearFields();
            frame.switchTo("CustomerBrowse");
        });

        add(layeredPane, BorderLayout.CENTER);
    }

    public void clearFields() {
        nicknameField.setText("");
    }
}
