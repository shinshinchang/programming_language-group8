package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VendorLoginPanel extends JPanel {
    public VendorLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("攤販登入介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        JTextField idField = new JTextField(15);
        idField.setPreferredSize(new Dimension(150, 30));
        idField.setMaximumSize(new Dimension(150, 30));

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(150, 30));
        passwordField.setMaximumSize(new Dimension(150, 30));

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        idPanel.add(new JLabel("攤位編號："));
        idPanel.add(idField);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.add(new JLabel("攤位密碼："));
        passwordPanel.add(passwordField);

        formPanel.add(idPanel);
        formPanel.add(passwordPanel);

        JButton loginBtn = new JButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());

                if (id.trim().isEmpty() || password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(VendorLoginPanel.this, "請輸入完整的編號與密碼！");
                } else {
                    frame.switchTo("VendorEdit");
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
