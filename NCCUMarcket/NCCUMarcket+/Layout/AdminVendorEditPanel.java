package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AdminVendorEditPanel extends VendorEditPanel {

    public AdminVendorEditPanel(MainFrame frame) {
        super(frame);
        super.title.setText("攤販資料更新(管)");

        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("AdminEdit"));

    }

    @Override
    public void refreshComments(String id) {
        commentPanel.removeAll();
        try {
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + id + "/comments.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> comments = gson.fromJson(reader, type);
            reader.close();

            if (comments != null) {
            for (Map.Entry<String, Map<String, Object>> entry : comments.entrySet()) {
                String commentId = entry.getKey();
                Map<String, Object> data = entry.getValue();
                String name = (String) data.get("name");
                String text = (String) data.get("comment");

                JPanel singleCommentPanel = new JPanel(new BorderLayout());
                JTextArea area = new JTextArea("👤 " + name + "：" + text);
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setEditable(false);
                area.setBackground(new Color(245, 245, 245));
                area.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                singleCommentPanel.add(area, BorderLayout.CENTER);

                // ➕ 刪除按鈕
                JButton deleteBtn = new JButton("刪除");
                deleteBtn.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 12));
                deleteBtn.setMargin(new Insets(2, 5, 2, 5));
                deleteBtn.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "確定要刪除這則留言嗎？", "確認刪除", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteComment(id, commentId);
                    }
                });

                JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                btnPanel.add(deleteBtn);
                singleCommentPanel.add(btnPanel, BorderLayout.SOUTH);

                commentPanel.add(Box.createVerticalStrut(5));
                commentPanel.add(singleCommentPanel);
            }
            } else {
                commentPanel.add(new JLabel("目前尚無評論"));
            }

            commentPanel.revalidate();
            commentPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            commentPanel.add(new JLabel("讀取評論失敗：" + e.getMessage()));
        }
    }
    private void deleteComment(String stallId, String commentId) {
    try {
        String url = "https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors/" + stallId + "/comments/" + commentId + ".json";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("DELETE");

        int responseCode = conn.getResponseCode();
        conn.disconnect();

        if (responseCode == 200) {
            JOptionPane.showMessageDialog(this, "刪除成功！");
            refreshComments(stallIdField.getText());
        } else {
            JOptionPane.showMessageDialog(this, "刪除失敗，請稍後再試！");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "刪除發生錯誤！");
    }
    }
}

