package Layout;

import java.awt.*;
import java.awt.event.*;
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
        title = new JLabel("管理者登入介面");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        titlePanel.add(title);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordLabel = new JLabel("密碼：");
        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(150, 30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        formPanel.add(passwordPanel);

        loginBtn = new JButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputPassword = new String(passwordField.getPassword());
                dbPassword = "1234";//這裡抓資料庫的Password比對
                if (inputPassword.equals(dbPassword)) {
                    frame.switchTo("AdminEdit");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(AdminLoginPanel.this, "請輸入正確密碼！");
                }
                
            }
        });

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(loginBtn);

        titlePanel.setBounds(0, 0, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
        formPanel.setBounds(0, 80, 400, 120);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);
        bottomPanel.setBounds(0, 220, 400, 60);
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
        passwordField.setText("");
    }
}
