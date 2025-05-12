package Layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class CustomerBrowsePanel extends JPanel {

    public MainFrame frame;
    private JLayeredPane layeredPane;
    public JLabel title;
    private JPanel filterPanel, buttonPanel, listPanel, topPanel, titlePanel;
    private JCheckBox tagEat, tagDrink, tagCulture, tagFashion, tagOther;
    public JButton filterBtn, absoluteBackBtn;
    private JScrollPane scrollPane;

    public CustomerBrowsePanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(400, 600));

        title = new JLabel("商家列表", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);

        tagEat = new JCheckBox("#好吃", true);
        tagDrink = new JCheckBox("#好喝", true);
        tagCulture = new JCheckBox("#文創");
        tagFashion = new JCheckBox("#時尚穿搭");
        tagOther = new JCheckBox("#其他");

        filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(tagEat);
        filterPanel.add(tagDrink);
        filterPanel.add(tagCulture);
        filterPanel.add(tagFashion);
        filterPanel.add(tagOther);
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, filterPanel.getPreferredSize().height));

        filterBtn = new JButton("篩選商家");
        filterBtn.setPreferredSize(new Dimension(150, 30));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(filterBtn);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(listPanel);

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setPreferredSize(new Dimension(400, 200));
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(titlePanel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(filterPanel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(buttonPanel);

        topPanel.setBounds(0, 0, 400, 200);
        scrollPane.setBounds(0, 200, 400, 360);

        layeredPane.add(topPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);

        absoluteBackBtn = new JButton("←");
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);

        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> selectedTags = new ArrayList<>();
                if (tagEat.isSelected())
                    selectedTags.add("好吃");
                if (tagDrink.isSelected())
                    selectedTags.add("好喝");
                if (tagCulture.isSelected())
                    selectedTags.add("文創");
                if (tagFashion.isSelected())
                    selectedTags.add("時尚穿搭");
                if (tagOther.isSelected())
                    selectedTags.add("其他");

                listPanel.removeAll();

                try {
                    URL url = new URL(
                            "https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors.json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();

                    Gson gson = new Gson();
                    Type mapType = new TypeToken<Map<String, Vendor>>() {
                    }.getType();
                    Map<String, Vendor> vendorMap = gson.fromJson(content.toString(), mapType);

                    for (Map.Entry<String, Vendor> entry : vendorMap.entrySet()) {
                        Vendor vendor = entry.getValue();
                        if (vendor.tags == null)
                            continue;
                        String lowerTag = vendor.tags.toLowerCase();
                        boolean match = selectedTags.stream().anyMatch(t -> lowerTag.contains(t));
                        if (match) {
                            listPanel.add(createVendorButton(entry.getKey(), vendor.name, vendor.tags));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CustomerBrowsePanel.this, "資料載入失敗: " + ex.getMessage());
                }

                listPanel.revalidate();
                listPanel.repaint();
            }
        });
    }

    public class VendorButton extends JButton {
        public VendorButton(MainFrame frame, String vendorId, String name, String tags) {
            setText(name + " #" + tags);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
            addActionListener(e -> {
                frame.switchTo("CustomerDetail");
                frame.refresh(vendorId);
            });
        }
    }

    protected JButton createVendorButton(String vendorId, String name, String tags) {
        return new VendorButton(frame, vendorId, name, tags); // 預設建立原始 VendorButton
    }

    public class Vendor {
        public String name;
        public String tags;
    }
}
