package Layout;

import java.awt.*;
import javax.swing.*;

public class AdminDatabasePanel extends JPanel {
    private MainFrame frame;
    private JLayeredPane layeredPane;
    private JLabel title;
    private JPanel titlePanel, listPanel;
    private JScrollPane scrollPane;
    private JButton addVendorBtn, deleteAllBtn, backBtn;

    public AdminDatabasePanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        // 標題
        title = new JLabel("攤販帳號管理", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(0, 0, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);

        // 攤販清單區
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(0, 60, 400, 480);
        layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);

        // ➕ 新增攤位
        addVendorBtn = new JButton("➕ 新增攤位");
        addVendorBtn.setBounds(50, 560, 140, 40);
        addVendorBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "輸入要新增的攤位數量：");
            if (input != null && input.matches("\\d+")) {
                int count = Integer.parseInt(input);
                addVendorButtons(count);
            }
        });
        layeredPane.add(addVendorBtn, JLayeredPane.DEFAULT_LAYER);

        // 🗑 刪除攤位
        deleteAllBtn = new JButton("🗑 刪除攤位");
        deleteAllBtn.setBounds(210, 560, 140, 40);
        deleteAllBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "是否刪除所有攤位？", "確認", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                listPanel.removeAll();
                listPanel.revalidate();
                listPanel.repaint();
            }
        });
        layeredPane.add(deleteAllBtn, JLayeredPane.DEFAULT_LAYER);

        // ← 返回按鈕
        backBtn = new JButton("←");
        backBtn.setBounds(10, 10, 50, 30);
        backBtn.setMargin(new Insets(2, 6, 2, 6));
        backBtn.addActionListener(e -> frame.switchTo("AdminEdit"));
        layeredPane.add(backBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }

    private void addVendorButtons(int count) {
        listPanel.removeAll(); // 每次新增會清空原有列表
        for (int i = 0; i < count; i++) {
            String id = String.format("%02d", i);
            String pw = id + "1234";
            JButton btn = new JButton("攤位 " + id + " ｜ 密碼：" + pw);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(btn);
            
        }
        listPanel.revalidate();
        listPanel.repaint();
    }
}
