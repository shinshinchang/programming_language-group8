package Layout;

import java.awt.event.*;


public class CustomerDetailPanel extends VendorEditPanel {
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

        
    }

   
}