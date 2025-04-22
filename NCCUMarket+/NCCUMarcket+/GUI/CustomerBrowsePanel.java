import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

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
                if (tagEat.isSelected()) selectedTags.add("#好吃");
                if (tagDrink.isSelected()) selectedTags.add("#好喝");
                if (tagCulture.isSelected()) selectedTags.add("#文創");
                if (tagFashion.isSelected()) selectedTags.add("#時尚穿搭");
                if (tagOther.isSelected()) selectedTags.add("#其他");

                listPanel.removeAll();

                ArrayList<Integer> matchedIds = new ArrayList<>();
                matchedIds.add(87);

                for (int id : matchedIds) {
                    listPanel.add(new VendorButton(frame, id));
                }

                listPanel.revalidate();
                listPanel.repaint();
            }
        });
    }

    private class VendorButton extends JButton {
        private int vendorId;

        public VendorButton(MainFrame frame, int vendorId) {
            this.vendorId = vendorId;
            String name = "⾼也椰薑餅屋";
            String tags = "#好吃 #好喝";
            double rating = 4.8;
            setText(name + " " + tags + " 評價：" + rating + "/5");

            setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.setSelectedVendorId(vendorId);
                    frame.switchTo("CustomerDetail");
                }
            });
        }
    }
}
