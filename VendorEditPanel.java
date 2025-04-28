import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VendorEditPanel extends JPanel {
    private boolean editMode = false;

    public VendorEditPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("攤販資料建⽴/更新", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JTextField stallIdField = new JTextField("87");
        JTextField nameField = new JTextField("⾼也椰薑餅屋");

        JCheckBox eatTag = new JCheckBox("好吃", true);
        JCheckBox drinkTag = new JCheckBox("好喝", true);
        JCheckBox cultureTag = new JCheckBox("文創");
        JCheckBox fashionTag = new JCheckBox("穿搭時尚");
        JCheckBox otherTag = new JCheckBox("其他");

        JTextArea promoArea = new JTextArea("歡迎來⾼爺爺的攤位買薑餅⼈!\nhttp://.com");
        JTextField contactField = new JTextField("0912345678");
        JCheckBox mobilePay = new JCheckBox("是否⽀援⾏動⽀付", true);

        Color bgColor = UIManager.getColor("Panel.background");

        stallIdField.setEditable(false);
        nameField.setEditable(false);
        promoArea.setEditable(false);
        contactField.setEditable(false);
        stallIdField.setBackground(bgColor);
        nameField.setBackground(bgColor);
        contactField.setBackground(bgColor);
        promoArea.setBackground(bgColor);
        eatTag.setEnabled(false);
        drinkTag.setEnabled(false);
        cultureTag.setEnabled(false);
        fashionTag.setEnabled(false);
        otherTag.setEnabled(false);
        mobilePay.setEnabled(false);

        formPanel.add(new JLabel("攤位編號："));
        formPanel.add(stallIdField);
        formPanel.add(new JLabel("名稱："));
        formPanel.add(nameField);

        formPanel.add(new JLabel("攤販標籤："));
        JPanel tagPanel = new JPanel(new FlowLayout());
        tagPanel.add(eatTag);
        tagPanel.add(drinkTag);
        tagPanel.add(cultureTag);
        tagPanel.add(fashionTag);
        tagPanel.add(otherTag);
        formPanel.add(tagPanel);

        formPanel.add(new JLabel("⽂宣內容/連結："));
        formPanel.add(new JScrollPane(promoArea));
        formPanel.add(new JLabel("聯絡⽅式："));
        formPanel.add(contactField);
        formPanel.add(new JLabel("付款⽅式："));
        formPanel.add(mobilePay);

        JButton submitBtn = new JButton("建⽴");
        submitBtn.setForeground(Color.GRAY);

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!editMode) {
                    // 進入編輯模式
                    stallIdField.setEditable(true);
                    nameField.setEditable(true);
                    promoArea.setEditable(true);
                    contactField.setEditable(true);
                    stallIdField.setBackground(Color.WHITE);
                    nameField.setBackground(Color.WHITE);
                    contactField.setBackground(Color.WHITE);
                    promoArea.setBackground(Color.WHITE);
                    eatTag.setEnabled(true);
                    drinkTag.setEnabled(true);
                    cultureTag.setEnabled(true);
                    fashionTag.setEnabled(true);
                    otherTag.setEnabled(true);
                    mobilePay.setEnabled(true);
                    submitBtn.setText("確定修改");
                    editMode = true;
                } else {
                    // 按下「確定修改」時收資料並傳到Firebase
                    String stallId = stallIdField.getText();
                    String name = nameField.getText();
                    String description = promoArea.getText();
                    String contactInfo = contactField.getText();
                    boolean mobilePayment = mobilePay.isSelected();

                    String selectedTag = "";
                    if (eatTag.isSelected()) selectedTag = "好吃";
                    else if (drinkTag.isSelected()) selectedTag = "好喝";
                    else if (cultureTag.isSelected()) selectedTag = "文創";
                    else if (fashionTag.isSelected()) selectedTag = "穿搭時尚";
                    else if (otherTag.isSelected()) selectedTag = "其他";

                    FirebaseService.addOrUpdateVendor(stallId, name, selectedTag, description, contactInfo, mobilePayment);

                    JOptionPane.showMessageDialog(VendorEditPanel.this, "資料已更新並儲存到Firebase！");
                    submitBtn.setText("建⽴");
                    submitBtn.setForeground(Color.GRAY);
                    editMode = false;
                    stallIdField.setEditable(false);
                    nameField.setEditable(false);
                    promoArea.setEditable(false);
                    contactField.setEditable(false);
                    stallIdField.setBackground(bgColor);
                    nameField.setBackground(bgColor);
                    contactField.setBackground(bgColor);
                    promoArea.setBackground(bgColor);
                    eatTag.setEnabled(false);
                    drinkTag.setEnabled(false);
                    cultureTag.setEnabled(false);
                    fashionTag.setEnabled(false);
                    otherTag.setEnabled(false);
                    mobilePay.setEnabled(false);
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submitBtn);

        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
