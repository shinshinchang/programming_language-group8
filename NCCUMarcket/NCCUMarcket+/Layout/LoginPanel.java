package Layout;

import java.awt.*;
import javax.swing.*;

public class LoginPanel extends JPanel {
    public LoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // 背景淺灰

        // === 標題 ===
        JLabel title = new JLabel("政大市集+", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 40));
        title.setForeground(new Color(33, 33, 33));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(getBackground());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 40, 0));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);
        // === NCCU Logo 區 ===
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("images.png")); // 確保路徑正確
        Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(getBackground());
        logoPanel.add(logoLabel);
        add(logoPanel, BorderLayout.CENTER);


        // === 按鈕區 ===
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(getBackground());
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));

        RoundedButton customerBtn = new RoundedButton("顧客登入");
        RoundedButton vendorBtn = new RoundedButton("攤販登入");
        RoundedButton adminBtn = new RoundedButton("管理員登入");

        customerBtn.addActionListener(e -> frame.switchTo("CustomerLogin"));
        vendorBtn.addActionListener(e -> frame.switchTo("VendorLogin"));
        adminBtn.addActionListener(e -> frame.switchTo("AdminLogin"));

        buttonPanel.add(customerBtn);
        buttonPanel.add(vendorBtn);
        buttonPanel.add(adminBtn);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(getBackground());
        centerWrapper.add(buttonPanel);
        JPanel middleWrapper = new JPanel();
        middleWrapper.setLayout(new BorderLayout());
        middleWrapper.setBackground(getBackground());
        middleWrapper.add(logoPanel, BorderLayout.NORTH);
        middleWrapper.add(centerWrapper, BorderLayout.CENTER);
        add(middleWrapper, BorderLayout.CENTER);

    }

    // === 自訂圓角按鈕類別，含按壓變色 ===
    static class RoundedButton extends JButton {
        private static final int RADIUS = 25;
        private static final Color NORMAL_COLOR = new Color(180, 215, 245);   // 正常狀態：淺藍稍深
        private static final Color PRESSED_COLOR = new Color(160, 200, 235);  // 按下狀態：再深一點

        public RoundedButton(String text) {
            super(text);
            setFont(new Font("Microsoft JhengHei", Font.PLAIN, 18));
            setPreferredSize(new Dimension(200, 45));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color fillColor = getModel().isPressed() ? PRESSED_COLOR : NORMAL_COLOR;
            g2.setColor(fillColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);

            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // 不畫邊框
        }
    }
}
