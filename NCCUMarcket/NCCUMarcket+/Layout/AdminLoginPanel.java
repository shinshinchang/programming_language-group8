package Layout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminLoginPanel extends JPanel {
    private JLayeredPane layeredPane;
    public AdminLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("管理者登入介面");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        titlePanel.add(title);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel passwordLabel = new JLabel("密碼：");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(150, 30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        formPanel.add(passwordPanel);

        JButton loginBtn = new JButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField.getPassword());
                if (password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AdminLoginPanel.this, "請輸入密碼！");
                } else {
                    frame.switchTo("AdminEdit");
                }
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));


        bottomPanel.add(loginBtn);

        titlePanel.setBounds(0, 0, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
        formPanel.setBounds(0, 80, 400, 120);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);
        bottomPanel.setBounds(0, 220, 400, 60);
        layeredPane.add(bottomPanel, JLayeredPane.DEFAULT_LAYER);

        JButton absoluteBackBtn = new JButton("←");
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }
}
