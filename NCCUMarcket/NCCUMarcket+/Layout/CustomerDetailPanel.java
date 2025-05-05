package Layout;

import java.awt.event.*;

public class CustomerDetailPanel extends VendorEditPanel {
    public CustomerDetailPanel(MainFrame frame) {
        super(frame);

        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("CustomerBrowse"));

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

    public void refresh(String id) {
        // 從資料庫抓資料把標籤的資料更新，當browserpanel的商家按鈕按下，會callout
        // frame的refresh()，然後frame會call這個refresh，這裡在更新顯示標籤就好
        // 簡單來說商家按鈕按下這裡就顯示

    }
}