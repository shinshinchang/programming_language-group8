import java.awt.*;
import javax.swing.*;

public class AdminPanel extends JPanel {
    public AdminPanel(MainFrame frame) {
        setLayout(new BorderLayout());

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

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
