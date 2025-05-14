// ✅ AdminDatabasePanel.java（修正為：vendors 節點下是 01, 02 等攤位 ID）
// ✅ AdminDatabasePanel.java（修正為：vendors 節點下是 01, 02 等攤位 ID）
package Layout;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class AdminDatabasePanel extends JPanel {
    private MainFrame frame;
    private JLayeredPane layeredPane;
    private JLabel title;
    private JPanel titlePanel, listPanel;
    private JScrollPane scrollPane;
    private StyledButton addVendorBtn, deleteAllBtn, backBtn;

    Map<String, String> vendorMap;
    Map<String, Object> vendorData;

    public AdminDatabasePanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        title = new JLabel("攤販帳號管理", SwingConstants.CENTER);
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(0, 10, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(0, 70, 400, 470);
        layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);

        addVendorBtn = new StyledButton("新增攤位");
        addVendorBtn.setBounds(50, 560, 140, 40);
        addVendorBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "輸入要新增的攤位數量：");
            if (input != null && input.matches("\\d+")) {
                int count = Integer.parseInt(input);
                addVendorButtons(count);
            }
        });
        layeredPane.add(addVendorBtn, JLayeredPane.DEFAULT_LAYER);

        deleteAllBtn = new StyledButton("刪除攤位");
        deleteAllBtn.setBounds(210, 560, 140, 40);
        deleteAllBtn.addActionListener(e -> deleteAllVendors());
        layeredPane.add(deleteAllBtn, JLayeredPane.DEFAULT_LAYER);

        backBtn = new StyledButton("←");
        backBtn.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        backBtn.setPreferredSize(new Dimension(50, 30));
        backBtn.setBounds(10, 10, 50, 30);
        backBtn.addActionListener(e -> frame.switchTo("AdminEdit"));
        layeredPane.add(backBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);

        vendorMap = new LinkedHashMap<>();
        vendorData = new LinkedHashMap<>();
        SwingUtilities.invokeLater(() -> fetchVendorAccounts());
    }

    private void addVendorButtons(int count) {
        listPanel.removeAll();

        vendorMap.clear();
        vendorData.clear();

        for (int i = 1; i <= count; i++) {
            String id = String.format("%02d", i);
            String pw = id + "1234";
            vendorMap.put(id, pw);

            Map<String, Object> info = new LinkedHashMap<>();
            info.put("record_id", id);
            info.put("name", "");
            info.put("tags", "");
            info.put("description", "");
            info.put("contact_info", "");
            info.put("support_mobile_payment", false);
            vendorData.put(id, info);
        }

        try {
            // ✅ 寫入 vendor_accounts
            URL accUrl = new URL(
                    "https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendor_accounts.json");
            HttpURLConnection accConn = (HttpURLConnection) accUrl.openConnection();
            accConn.setRequestMethod("PUT");
            accConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            accConn.setDoOutput(true);
            accConn.getOutputStream().write(new Gson().toJson(vendorMap).getBytes("UTF-8"));
            accConn.getOutputStream().close();

            // ✅ 檢查是否寫入 vendor_accounts 成功
            int accResponseCode = accConn.getResponseCode();
            if (accResponseCode != 200) {
                JOptionPane.showMessageDialog(this, "❌ Firebase /vendor_accounts 寫入失敗，HTTP 回應：" + accResponseCode);
                return;
            } else {
                System.out.println("✅ Firebase /vendor_accounts 寫入成功");
            }

            // ✅ 先 GET 原始 /vendors.json
            URL getUrl = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors.json");
            HttpURLConnection getConn = (HttpURLConnection) getUrl.openConnection();
            getConn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(getConn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                jsonBuilder.append(line);
            reader.close();

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> currentData = gson.fromJson(jsonBuilder.toString(), type);
            if (currentData == null)
                currentData = new LinkedHashMap<>();

            // ✅ 清空原本的 Firebase vendor 資料後完整覆蓋
            currentData.clear();
            currentData.putAll(vendorData);

            // 寫入合併後結果
            URL putUrl = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors.json");
            HttpURLConnection putConn = (HttpURLConnection) putUrl.openConnection();
            putConn.setRequestMethod("PUT");
            putConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            putConn.setDoOutput(true);
            putConn.getOutputStream().write(gson.toJson(currentData).getBytes("UTF-8"));
            putConn.getOutputStream().close();

            // ✅ 檢查是否寫入成功
            int putResponseCode = putConn.getResponseCode();
            if (putResponseCode != 200) {
                JOptionPane.showMessageDialog(this, "❌ Firebase /vendors 寫入失敗，HTTP 回應：" + putResponseCode);
                return;
            } else {
                System.out.println("✅ Firebase /vendors 寫入成功");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "新增時發生錯誤：" + ex.getMessage());
            return;
        }

        for (Map.Entry<String, String> entry : vendorMap.entrySet()) {
            JButton btn = new JButton("攤位 " + entry.getKey() + " ｜ 密碼：" + entry.getValue());
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(btn);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private void deleteAllVendors() {
        int result = JOptionPane.showConfirmDialog(this, "確定要刪除所有攤位帳密與資料？", "警告", JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION)
            return;

        try {
            URL accUrl = new URL(
                    "https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendor_accounts.json");
            HttpURLConnection accConn = (HttpURLConnection) accUrl.openConnection();
            accConn.setRequestMethod("DELETE");
            accConn.getResponseCode();

            URL vendorsUrl = new URL(
                    "https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors.json");
            HttpURLConnection vendorsConn = (HttpURLConnection) vendorsUrl.openConnection();
            vendorsConn.setRequestMethod("DELETE");
            vendorsConn.getResponseCode();

            listPanel.removeAll();
            listPanel.revalidate();
            listPanel.repaint();
            JOptionPane.showMessageDialog(this, "✅ 所有攤販帳密與資料已成功刪除！");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ 發生錯誤：" + ex.getMessage());
        }
    }

    

    private void fetchVendorAccounts() {
        listPanel.removeAll();
        try {
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendor_accounts.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) jsonBuilder.append(line);
            reader.close();

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            vendorMap = gson.fromJson(jsonBuilder.toString(), type);

            if (vendorMap != null) {
                for (Map.Entry<String, String> entry : vendorMap.entrySet()) {
                    JButton btn = new JButton("攤位 " + entry.getKey() + " ｜ 密碼：" + entry.getValue());
                    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    listPanel.add(btn);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ 資料載入失敗：" + ex.getMessage());
        }

        listPanel.revalidate();
        listPanel.repaint();
    }
}
