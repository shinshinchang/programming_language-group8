package Layout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class CustomerDetailPanel extends VendorEditPanel {

    private JTextArea commentInput;
    private JButton submitCommentBtn;
    private JPanel commentsPanel;
    private JScrollPane commentScrollPane;
    private String currentVendorId;

    public CustomerDetailPanel(MainFrame frame) {
        super(frame);

        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("CustomerBrowse"));

        super.title.setText("商家瀏覽頁面");
        super.stallIdField.setEditable(false);
        super.nameField.setEditable(false);
        super.eatTag.setEnabled(false);
        super.drinkTag.setEnabled(false);
        super.cultureTag.setEnabled(false);
        super.fashionTag.setEnabled(false);
        super.otherTag.setEnabled(false);
        super.promoArea.setEditable(false);
        super.contactField.setEditable(false);
        super.mobilePay.setEnabled(false);
        super.submitBtn.setVisible(false);

        // === 留言輸入與按鈕 ===
        commentInput = new JTextArea(3, 30);
        submitCommentBtn = new JButton("送出評論");
        submitCommentBtn.addActionListener(e -> submitComment());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(new JLabel("留下你的評論："), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(commentInput), BorderLayout.CENTER);
        inputPanel.add(submitCommentBtn, BorderLayout.SOUTH);

        // === 留言顯示面板 ===
        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentScrollPane = new JScrollPane(commentsPanel);
        commentScrollPane.setPreferredSize(new Dimension(500, 150));
        commentScrollPane.setBorder(BorderFactory.createTitledBorder("顧客留言"));

        // === 加入到畫面底部 ===
        JPanel commentSection = new JPanel();
        commentSection.setLayout(new BorderLayout());
        commentSection.add(commentScrollPane, BorderLayout.CENTER);
        commentSection.add(inputPanel, BorderLayout.SOUTH);

        this.add(commentSection, BorderLayout.SOUTH);
    }

    // === 顯示商家資料時一併載入留言 ===
    public void refresh(String vendorId) {
        this.currentVendorId = vendorId;
        super.refresh(vendorId);
        loadComments(vendorId);
    }

    // === 從 Firebase 載入留言 ===
    private void loadComments(String vendorId) {
        commentsPanel.removeAll();
        try {
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendor_reviews/"
                    + vendorId + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();
            reader.close();

            Gson gson = new Gson();
            Type reviewListType = new TypeToken<List<Review>>() {
            }.getType();
            List<Review> reviews = gson.fromJson(response, reviewListType);

            if (reviews != null) {
                for (Review review : reviews) {
                    JLabel label = new JLabel("🗨️ " + review.user + "：" + review.comment);
                    commentsPanel.add(label);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    // === 提交留言到 Firebase ===//
    private void submitComment() {
        String comment = commentInput.getText().trim();
        if (comment.isEmpty())
            return;

        try {
            URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendor_reviews/"
                    + currentVendorId + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            Review newReview = new Review("匿名", comment, new Date().toString());
            Gson gson = new Gson();
            String json = gson.toJson(newReview);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            commentInput.setText("");
            loadComments(currentVendorId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // === 留言資料類別 ===
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