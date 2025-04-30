package Layout;
import java.awt.*;
import javax.swing.*;

public class AdminEditPanel extends JPanel {
    public AdminEditPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JLabel title = new JLabel("管理者介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setText(
            "這是管理者專區（目前僅展示畫面）\n\n"
          + "\u2022 功能待實作：\n"
          + "  - 編輯/刪除顧客評論\n"
          + "  - 查看所有攤販資訊\n"
          + "  - 刪除攤販/帳號\n"
          + "  - 系統狀態總覽\n"
        );

        JScrollPane scrollPane = new JScrollPane(infoArea);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(0, 0, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);
        scrollPane.setBounds(0, 60, 400, 300);
        layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);

        JButton absoluteBackBtn = new JButton("←");
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }
}
