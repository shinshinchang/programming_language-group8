package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.security.auth.Refreshable;

public class CustomerDetailPanel extends JPanel {
    public CustomerDetailPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("⾼也椰薑餅屋 - 詳細資訊", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JTextArea detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setText(
                "#好吃 #好喝\n"
              + "總評分：4.8/5\n"
              + "歡迎來⾼爺爺的攤位買薑餅⼈!\n"
              + "網站：http://.com\n"
              + "聯絡：0912345678\n"
              + "付款方式：⽀援⾏動⽀付\n\n"
              + "⽊柵彭于晏：以購買⼩孩很愛吃\n"
              + "資管系辦⼀朵花：邊吃邊寫java事 半功倍，推推!\n"
        );

        JScrollPane scrollPane = new JScrollPane(detailArea);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextField commentField = new JTextField();
        JButton submitBtn = new JButton("新增評價");

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String comment = commentField.getText();
                if (comment.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerDetailPanel.this, "請輸入評論內容！");
                } else {
                    JOptionPane.showMessageDialog(CustomerDetailPanel.this, "評論已提交（但尚未實作儲存功能）");
                    commentField.setText("");
                }
            }
        });

        bottomPanel.add(new JLabel("輸入："), BorderLayout.WEST);
        bottomPanel.add(commentField, BorderLayout.CENTER);
        bottomPanel.add(submitBtn, BorderLayout.EAST);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        
    }


    public void refresh(String id){
        //從資料庫抓資料把標籤的資料更新，當browserpanel的商家按鈕按下，會callout frame的refresh()，然後frame會call這個refresh，這裡在更新顯示標籤就好
        //簡單來說商家按鈕按下這裡就顯示
        
    }
}