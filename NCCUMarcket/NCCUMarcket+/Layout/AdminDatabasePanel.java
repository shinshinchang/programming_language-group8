package Layout;

import java.awt.*;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.net.URL;

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
    listPanel.removeAll();

    Map<String, String> vendorMap = new LinkedHashMap<>();
    for (int i = 1; i <= count; i++) { // 從 1 開始
        String id = String.format("%02d", i);
        String pw = id + "1234";
        vendorMap.put(id, pw);
    }

    try {
        URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendor_accounts.json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        String json = new com.google.gson.Gson().toJson(vendorMap);
        conn.getOutputStream().write(json.getBytes("UTF-8"));
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        if (conn.getResponseCode() != 200) {
            JOptionPane.showMessageDialog(this, "Firebase 寫入失敗！");
            return;
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "新增時發生錯誤：" + ex.getMessage());
        return;
    }

    // 建立按鈕
    for (Map.Entry<String, String> entry : vendorMap.entrySet()) {
        JButton btn = new JButton("攤位 " + entry.getKey() + " ｜ 密碼：" + entry.getValue());
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPanel.add(btn);
    }

    listPanel.revalidate();
    listPanel.repaint();
}

private void deleteAllVendors() {
    int result = JOptionPane.showConfirmDialog(this, "是否刪除所有攤位帳密與資料？", "確認", JOptionPane.YES_NO_OPTION);
    if (result != JOptionPane.YES_OPTION) return;

    try {
        // 刪除帳密
        URL accountsUrl = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendor_accounts.json");
        HttpURLConnection conn1 = (HttpURLConnection) accountsUrl.openConnection();
        conn1.setRequestMethod("DELETE");
        int res1 = conn1.getResponseCode();

        // 刪除攤位資料
        URL vendorsUrl = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors.json");
        HttpURLConnection conn2 = (HttpURLConnection) vendorsUrl.openConnection();
        conn2.setRequestMethod("DELETE");
        int res2 = conn2.getResponseCode();

        if (res1 == 200 && res2 == 200) {
            listPanel.removeAll();
            listPanel.revalidate();
            listPanel.repaint();
            JOptionPane.showMessageDialog(this, "✅ 所有攤位帳密與資料已刪除！");
        } else {
            JOptionPane.showMessageDialog(this, "❌ 刪除失敗，請檢查網路！");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "刪除時發生錯誤：" + ex.getMessage());
    }
}

}