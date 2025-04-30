package Layout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminLoginPanel extends JPanel {
    public AdminLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("管理者登入介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

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

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loginBtn);

        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
