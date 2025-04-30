package Layout;

import java.awt.CardLayout;
import javax.swing.*;

public class MainFrame extends JFrame {
    private String selectedVendorId;
    CardLayout layout;
    JPanel mainPanel;

    VendorLoginPanel VendorLogin;
    VendorEditPanel VendorEdit;
    CustomerLoginPanel CustomerLogin;
    CustomerBrowsePanel CustomerBrowse;
    CustomerDetailPanel CustomerDetail;
    AdminLoginPanel AdminLogin;
    AdminEditPanel AdminEdit;
    LoginPanel Login;

    public MainFrame() {
        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        VendorLogin = new VendorLoginPanel(this);
        VendorEdit = new VendorEditPanel(this);
        CustomerLogin = new CustomerLoginPanel(this);
        CustomerBrowse = new CustomerBrowsePanel(this);
        CustomerDetail = new CustomerDetailPanel(this);
        AdminLogin = new AdminLoginPanel(this);
        AdminEdit = new AdminEditPanel(this);
        Login = new LoginPanel(this);

        mainPanel.add(VendorLogin, "VendorLogin");
        mainPanel.add(VendorEdit, "VendorEdit");
        mainPanel.add(CustomerLogin, "CustomerLogin");
        mainPanel.add(CustomerBrowse, "CustomerBrowse");
        mainPanel.add(CustomerDetail, "CustomerDetail");
        mainPanel.add(AdminLogin, "AdminLogin");
        mainPanel.add(AdminEdit, "AdminEdit");
        mainPanel.add(Login, "Login");

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
        CustomerDetail.refresh(id);
    }
}

