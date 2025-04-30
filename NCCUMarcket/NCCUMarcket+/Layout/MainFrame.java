package Layout;

import java.awt.CardLayout;
import javax.swing.*;

public class MainFrame extends JFrame {
    private String selectedVendorId;
    CardLayout layout;
    JPanel mainPanel;

    public MainFrame() {
        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        VendorEditPanel VendorEdit = new VendorEditPanel(this);
        CustomerLoginPanel CustomerLogin = new CustomerLoginPanel(this);
        CustomerBrowsePanel CustomerBrowse = new CustomerBrowsePanel(this);
        CustomerDetailPanel CustomerDetail = new CustomerDetailPanel(this);
        AdminLoginPanel AdminLogin = new AdminLoginPanel(this);
        AdminEditPanel AdminEdit = new AdminEditPanel(this);

        mainPanel.add(VendorEdit, "VendorEdit");
        mainPanel.add(CustomerLogin, "CustomerLogin");
        mainPanel.add(CustomerBrowse, "CustomerBrowse");
        mainPanel.add(CustomerDetail, "CustomerDetail");
        mainPanel.add(AdminLogin, "AdminLogin");
        mainPanel.add(AdminEdit, "AdminEdit");

        add(mainPanel);
        layout.show(mainPanel, "Login");

        setTitle("政大市集+");
        setSize(405, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void switchTo(String panelName) {
        layout.show(mainPanel, panelName);
    }

    public void setSelectedVendorId(String id) {
        this.selectedVendorId = id;
    }

    public String getSelectedVendorId() {
        return selectedVendorId;
    }

    public void refresh(String id) {
        this.CustomerDetail.refresh();
    }

}
