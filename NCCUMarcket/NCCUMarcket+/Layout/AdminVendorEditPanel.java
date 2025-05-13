package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class AdminVendorEditPanel extends VendorEditPanel {
    // 49and90行的database還沒連接
    private JPanel adminCommentsPanel;
    private JScrollPane adminCommentScrollPane;
    private String currentVendorId;

    public AdminVendorEditPanel(MainFrame frame) {
        super(frame);
        super.title.setText("攤販資料建立/更新(管理者)");

        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("AdminEdit"));

        // 留言面板
        adminCommentsPanel = new JPanel();
        adminCommentsPanel.setLayout(new BoxLayout(adminCommentsPanel, BoxLayout.Y_AXIS));
        adminCommentScrollPane = new JScrollPane(adminCommentsPanel);
        adminCommentScrollPane.setPreferredSize(new Dimension(450, 180));
        adminCommentScrollPane.setBorder(BorderFactory.createTitledBorder("顧客留言管理"));
        add(adminCommentScrollPane, BorderLayout.SOUTH);
    }

    @Override
    public void refresh(String vendorId) {
        super.refresh(vendorId);
        this.currentVendorId = vendorId;
        loadAdminComments(vendorId);
    }

    private void loadAdminComments(String vendorId) {
        adminCommentsPanel.removeAll();

        try {
            // URL url = new URL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();
            reader.close();

            Gson gson = new Gson();
            Type reviewMapType = new TypeToken<Map<String, Review>>() {
            }.getType();
            Map<String, Review> reviewMap = gson.fromJson(response, reviewMapType);

            if (reviewMap != null) {
                for (Map.Entry<String, Review> entry : reviewMap.entrySet()) {
                    String reviewId = entry.getKey();
                    Review review = entry.getValue();

                    JPanel panel = new JPanel(new BorderLayout());
                    panel.add(new JLabel("🗨️ " + review.user + ": " + review.comment), BorderLayout.CENTER);

                    JButton deleteBtn = new JButton("刪除");
                    deleteBtn.addActionListener(e -> deleteReview(vendorId, reviewId));
                    panel.add(deleteBtn, BorderLayout.EAST);

                    adminCommentsPanel.add(panel);
                }
            } else {
                adminCommentsPanel.add(new JLabel("（目前尚無留言）"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            adminCommentsPanel.add(new JLabel("留言載入失敗"));
        }

        adminCommentsPanel.revalidate();
        adminCommentsPanel.repaint();
    }

    private void deleteReview(String vendorId, String reviewId) {
        try {
            // URL url = new URL(database連接);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.getInputStream().close(); // 一定要呼叫才能完成請求
            loadAdminComments(vendorId); // 即時刷新
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class Review {
        String user;
        String comment;
        String timestamp;

        public Review(String user, String comment, String timestamp) {
            this.user = user;
            this.comment = comment;
            this.timestamp = timestamp;
        }
    }
}
