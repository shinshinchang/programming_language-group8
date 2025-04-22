import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginPanel extends JPanel {
    public LoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("政大市集+", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        JPanel buttonPanel = new JPanel();
        JButton customerBtn = new JButton("顧客登入");
        JButton vendorBtn = new JButton("攤販登入");
        JButton adminBtn = new JButton("管理員登入");

        customerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.switchTo("CustomerLogin");
            }
        });

        vendorBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.switchTo("VendorLogin");
            }
        });

        adminBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoginPanel.this, "管理員介面尚未實作");
            }
        });

        buttonPanel.add(customerBtn);
        buttonPanel.add(vendorBtn);
        buttonPanel.add(adminBtn);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}