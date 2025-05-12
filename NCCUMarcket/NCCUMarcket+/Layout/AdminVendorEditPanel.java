package Layout;

import java.awt.event.*;

public class AdminVendorEditPanel extends VendorEditPanel{

    public AdminVendorEditPanel(MainFrame frame) {
        super(frame);

        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("AdminEdit"));
        
    }
 

}
