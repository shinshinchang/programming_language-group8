// 統一樣式的圓角按鈕類別，可用於所有面板
package Layout;

import java.awt.*;
import javax.swing.*;

public class StyledButton extends JButton {
    private static final int RADIUS = 25;
    private static final Color NORMAL_COLOR = new Color(180, 215, 245);
    private static final Color PRESSED_COLOR = new Color(160, 200, 235);

    public StyledButton(String text) {
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
        g2.setColor(getModel().isPressed() ? PRESSED_COLOR : NORMAL_COLOR);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // no border
    }
}
