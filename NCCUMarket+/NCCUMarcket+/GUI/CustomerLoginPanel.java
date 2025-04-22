import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomerLoginPanel extends JPanel {
    public CustomerLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("顧客登入介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout());
        formPanel.add(new JLabel("輸入暱稱："));

        JTextField nicknameField = new JTextField(20);
        formPanel.add(nicknameField);

        JButton loginBtn = new JButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nickname = nicknameField.getText();
                if (nickname.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerLoginPanel.this, "請輸入暱稱！");
                } else {
                    frame.switchTo("CustomerBrowse");
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
