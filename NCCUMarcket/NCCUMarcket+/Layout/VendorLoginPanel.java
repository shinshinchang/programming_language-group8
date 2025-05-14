// ✅ 美化後的 VendorLoginPanel，含 StyledButton 與字體調整
package Layout;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class VendorLoginPanel extends JPanel {
    public JLayeredPane layeredPane;
    public JLabel title;
    public JPanel formPanel;
    public JTextField idField;
    public JPasswordField passwordField;
    public JPanel idPanel;
    public JPanel passwordPanel;
    public StyledButton loginBtn;
    public JPanel bottomPanel;
    public JPanel titlePanel;
    public StyledButton absoluteBackBtn;

    public VendorLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(400, 600));

        title = new JLabel("攤販登入", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBounds(0, 80, 400, 60);
        titlePanel.add(title);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        idField = new JTextField(15);
        idField.setPreferredSize(new Dimension(150, 30));
        idField.setMaximumSize(new Dimension(150, 30));

        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(150, 30));
        passwordField.setMaximumSize(new Dimension(150, 30));

        idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel idLabel = new JLabel("攤位編號：");
        idLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
        idPanel.add(idLabel);
        idPanel.add(idField);

        passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel pwdLabel = new JLabel("攤位密碼：");
        pwdLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 18));
        passwordPanel.add(pwdLabel);
        passwordPanel.add(passwordField);

        formPanel.add(idPanel);
        formPanel.add(passwordPanel);
        formPanel.setBounds(0, 160, 400, 100);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);

        loginBtn = new StyledButton("登入");
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (id.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(VendorLoginPanel.this, "請輸入完整的編號與密碼！");
                    return;
                }

                try {
                    URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendor_accounts/" + id + ".json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String dbPassword = reader.readLine().replaceAll("\"", "");
                    reader.close();

                    if (password.equals(dbPassword)) {
                        frame.setSelectedVendorId(id);
                        frame.switchTo("VendorEdit");
                    } else {
                        JOptionPane.showMessageDialog(VendorLoginPanel.this, "帳號或密碼錯誤！");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(VendorLoginPanel.this, "登入失敗！");
                }

                clearFields();
                frame.refresh(id);
            }
        });

        bottomPanel = new JPanel();
        bottomPanel.add(loginBtn);
        bottomPanel.setBounds(0, 270, 400, 60);
        layeredPane.add(bottomPanel, JLayeredPane.DEFAULT_LAYER);

        absoluteBackBtn = new StyledButton("←");
        absoluteBackBtn.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        absoluteBackBtn.setPreferredSize(new Dimension(50, 30));
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