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
    public CustomerBrowsePanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("商家列表", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel filterPanel = new JPanel(new FlowLayout());
        JCheckBox tagEat = new JCheckBox("#好吃", true);
        JCheckBox tagDrink = new JCheckBox("#好喝", true);
        JCheckBox tagCulture = new JCheckBox("#文創");
        JCheckBox tagFashion = new JCheckBox("#時尚穿搭");
        JCheckBox tagOther = new JCheckBox("#其他");

        filterPanel.add(tagEat);
        filterPanel.add(tagDrink);
        filterPanel.add(tagCulture);
        filterPanel.add(tagFashion);
        filterPanel.add(tagOther);

        JButton filterBtn = new JButton("篩選商家");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterBtn.setPreferredSize(new Dimension(150, 30));
        buttonPanel.add(filterBtn);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listPanel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(titlePanel);
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, filterPanel.getPreferredSize().height));
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(filterPanel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(buttonPanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        filterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> selectedTags = new ArrayList<>();
                if (tagEat.isSelected()) selectedTags.add("好吃");
                if (tagDrink.isSelected()) selectedTags.add("好喝");
                if (tagCulture.isSelected()) selectedTags.add("文創");
                if (tagFashion.isSelected()) selectedTags.add("時尚穿搭");
                if (tagOther.isSelected()) selectedTags.add("其他");

                listPanel.removeAll();

                try {
                    URL url = new URL("https://nccu-market-default-rtdb.asia-southeast1.firebasedatabase.app/vendors.json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();

                    Gson gson = new Gson();
                    Type mapType = new TypeToken<Map<String, Vendor>>(){}.getType();
                    Map<String, Vendor> vendorMap = gson.fromJson(content.toString(), mapType);

                    for (Map.Entry<String, Vendor> entry : vendorMap.entrySet()) {
                        Vendor vendor = entry.getValue();
                        if (vendor.tags == null) continue;
                        String lowerTag = vendor.tags.toLowerCase();
                        boolean match = selectedTags.stream().anyMatch(t -> lowerTag.contains(t));
                        if (match) {
                            listPanel.add(new VendorButton(frame, entry.getKey(), vendor.name, vendor.tags));
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

    private class VendorButton extends JButton {
        public VendorButton(MainFrame frame, String vendorId, String name, String tags) {
            setText(name + " #" + tags);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.setSelectedVendorId(vendorId);
                    frame.switchTo("CustomerDetail");
                }
            });
        }
    }

    public class Vendor {
        public String name;
        public String tags;
    }
}

