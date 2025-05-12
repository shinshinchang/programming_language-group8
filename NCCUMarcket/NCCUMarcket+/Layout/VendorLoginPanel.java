package Layout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VendorLoginPanel extends JPanel {
    public JLayeredPane layeredPane;
    public JLabel title;
    public JPanel formPanel;
    public JTextField idField;
    public JPasswordField passwordField;
    public JPanel idPanel;
    public JPanel passwordPanel;
    public JButton loginBtn;
    public JPanel bottomPanel;
    public JPanel titlePanel;
    public JButton absoluteBackBtn;

    public VendorLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        title = new JLabel("攤販登入介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        idField = new JTextField(15);
        idField.setPreferredSize(new Dimension(150, 30));
        idField.setMaximumSize(new Dimension(150, 30));

        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(150, 30));
        passwordField.setMaximumSize(new Dimension(150, 30));

        idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        idPanel.add(new JLabel("攤位編號："));
        idPanel.add(idField);

        passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.add(new JLabel("攤位密碼："));
        passwordPanel.add(passwordField);

        formPanel.add(idPanel);
        formPanel.add(passwordPanel);

        loginBtn = new JButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());

                if (id.trim().isEmpty() || password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(VendorLoginPanel.this, "請輸入完整的編號與密碼！");
                } else {
                    frame.switchTo("VendorEdit");
                }
                clearFields();
            }
        });

        bottomPanel = new JPanel();
        bottomPanel.add(loginBtn);

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(0, 0, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
        formPanel.setBounds(0, 60, 400, 120);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);
        bottomPanel.setBounds(0, 180, 400, 60);
        layeredPane.add(bottomPanel, JLayeredPane.DEFAULT_LAYER);

        absoluteBackBtn = new JButton("←");
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> {
            frame.switchTo("Login");
            clearFields();
        });
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }

    public void clearFields() {
        idField.setText("");
        passwordField.setText("");
    }
}
