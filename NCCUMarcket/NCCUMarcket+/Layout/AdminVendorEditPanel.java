package Layout;

import java.awt.event.*;

public class AdminVendorEditPanel extends VendorEditPanel{

    public AdminVendorEditPanel(MainFrame frame) {
        super(frame);
        super.title.setText("攤販資料建立/更新(管理者)");

        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("AdminEdit"));
        
    }
 

}
