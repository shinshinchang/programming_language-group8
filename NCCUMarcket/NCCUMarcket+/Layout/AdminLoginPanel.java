package Layout;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class AdminLoginPanel extends JPanel {
    public JLayeredPane layeredPane;
    public JPanel titlePanel;
    public JLabel title;
    public JPanel formPanel;
    public JPanel passwordPanel;
    public JLabel passwordLabel;
    public JPasswordField passwordField;
    public JButton loginBtn;
    public JPanel bottomPanel;
    public JButton absoluteBackBtn;

    private String inputPassword;
    private String dbPassword;//這裡抓資料庫的Password比對

    public AdminLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        title = new JLabel("管理者登入", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));
        titlePanel.add(title);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordLabel = new JLabel("密碼：");
        passwordLabel.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 18));
        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(150, 30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        formPanel.add(passwordPanel);

        loginBtn = new StyledButton("登入");
        loginBtn.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        inputPassword = new String(passwordField.getPassword());

        try {
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/admin_password.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String response = reader.readLine();
            reader.close();

            System.out.println("Firebase 回傳原始資料: " + response);

            dbPassword = response.replaceAll("[\"\\\\]", "");
            System.out.println("解析後 dbPassword: " + dbPassword);
            System.out.println("使用者輸入 inputPassword: " + inputPassword);

            if (inputPassword.equals(dbPassword)) {
                frame.switchTo("AdminEdit");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(AdminLoginPanel.this, "請輸入正確密碼！");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(AdminLoginPanel.this, "❌ 登入錯誤：" + ex.getMessage());
        }
    }
});


        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(loginBtn);

        titlePanel.setBounds(0, 70, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
        formPanel.setBounds(0, 160, 400, 120);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);
        bottomPanel.setBounds(0, 280, 400, 60);
        layeredPane.add(bottomPanel, JLayeredPane.DEFAULT_LAYER);

        absoluteBackBtn = new StyledButton("←");
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
        passwordField.setText("");
    }
}