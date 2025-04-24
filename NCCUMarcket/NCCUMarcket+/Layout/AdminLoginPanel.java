package Layout;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminLoginPanel extends JPanel {
    public AdminLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("管理者登入介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        // JLabel idLabel = new JLabel("攤位編號：");
        // JTextField idField = new JTextField();
        JLabel passwordLabel = new JLabel("密碼：");
        JPasswordField passwordField = new JPasswordField();

        // formPanel.add(idLabel);
        // formPanel.add(idField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JButton loginBtn = new JButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField.getPassword());
                
                if (password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AdminLoginPanel.this, "請輸入完整的編號與密碼！");
                } else {
                    frame.switchTo("Admin");
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





