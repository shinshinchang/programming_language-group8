package Layout;

import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;

public class AdminChangePasswordPanel extends JPanel {
    private JPasswordField newPasswordField;
    private JButton confirmBtn;
    private JButton backBtn;

    public AdminChangePasswordPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JLabel title = new JLabel("修改密碼", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 24));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(0, 50, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);

        JPanel formPanel = new JPanel(new FlowLayout());
        JLabel newPwLabel = new JLabel("新密碼：");
        newPasswordField = new JPasswordField(15);
        newPasswordField.setPreferredSize(new Dimension(150, 30));
        formPanel.add(newPwLabel);
        formPanel.add(newPasswordField);
        formPanel.setBounds(100, 130, 200, 50);
        layeredPane.add(formPanel, JLayeredPane.DEFAULT_LAYER);

        confirmBtn = new StyledButton("確認");
        confirmBtn.setBounds(150, 200, 100, 40);
        confirmBtn.addActionListener(e -> updatePasswordToFirebase());
        layeredPane.add(confirmBtn, JLayeredPane.DEFAULT_LAYER);

        backBtn = new StyledButton("←");
        backBtn.setBounds(10, 10, 50, 30);
        backBtn.addActionListener(e -> frame.switchTo("AdminEdit"));
        layeredPane.add(backBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }

    private void updatePasswordToFirebase() {
        try {
            String newPassword = new String(newPasswordField.getPassword());

            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "密碼不能為空");
                return;
            }

            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/admin_password.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String json = "\"" + newPassword + "\"";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
            }

            if (conn.getResponseCode() == 200) {
                JOptionPane.showMessageDialog(this, "✅ 密碼修改成功！");
                newPasswordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ 密碼更新失敗，請稍後再試");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ 發生錯誤：" + ex.getMessage());
        }
    }
}