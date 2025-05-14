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
    private JPanel formPanel, bottomPanel, titlePanel;
    private JLabel title;
    private JButton absoluteBackBtn;

    public CustomerLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        title = new JLabel("顧客登入介面", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));

        formPanel = new JPanel(new FlowLayout());
        formPanel.add(new JLabel("輸入暱稱："));
        nicknameField = new JTextField(20);
        formPanel.add(nicknameField);

        loginBtn = new StyledButton("登入");
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
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }

    public void clearFields() {
        nicknameField.setText("");
    }
}
