package Layout;

import java.awt.event.*;
import javax.swing.JButton;

public class AdminBrowsePanel extends CustomerBrowsePanel {

    public AdminBrowsePanel(MainFrame frame) {
        super(frame);
        title.setText("商家列表（管理者）");

        for (ActionListener al : absoluteBackBtn.getActionListeners()) {
            absoluteBackBtn.removeActionListener(al);
        }
        absoluteBackBtn.addActionListener(e -> frame.switchTo("AdminEdit"));

    }

    @Override
    protected JButton createVendorButton(String vendorId, String name, String tags) {
        return new VendorButton(frame, vendorId, name, tags); // 使用我自己的 VendorButton
    }

    public class VendorButton extends CustomerBrowsePanel.VendorButton {
    public VendorButton(MainFrame frame, String vendorId, String name, String tags) {
        super(frame, vendorId, name, tags);
        for (ActionListener al : getActionListeners()) {
            removeActionListener(al);
        }
        addActionListener(e -> {
            frame.switchTo("AdminVendorEdit"); // 改成你想切的頁面
            frame.refresh(vendorId);
        });
    }
}


}
