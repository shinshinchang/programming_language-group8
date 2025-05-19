// ✅ 修正：MainFrame 初始化時延後建立 VendorEditPanel，並在登入後設定 selectedVendorId
package Layout;

import java.awt.CardLayout;
import javax.swing.*;

public class MainFrame extends JFrame {
    private String selectedVendorId;
    private String customerNickname;
    CardLayout layout;
    JPanel mainPanel;

    VendorLoginPanel VendorLogin;
    VendorEditPanel VendorEdit; // 延後初始化
    CustomerLoginPanel CustomerLogin;
    CustomerBrowsePanel CustomerBrowse;
    CustomerDetailPanel CustomerDetail;
    AdminLoginPanel AdminLogin;
    AdminEditPanel AdminEdit;
    AdminBrowsePanel AdminBrowse;
    AdminDatabasePanel AdminDatabase;
    AdminVendorEditPanel AdminVendorEdit;
    AdminChangePasswordPanel AdminChangePassword;
    LoginPanel Login;

    public MainFrame() {
        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        VendorLogin = new VendorLoginPanel(this);
        CustomerLogin = new CustomerLoginPanel(this);
        CustomerBrowse = new CustomerBrowsePanel(this);
        CustomerDetail = new CustomerDetailPanel(this);
        AdminLogin = new AdminLoginPanel(this);
        AdminEdit = new AdminEditPanel(this);
        AdminBrowse = new AdminBrowsePanel(this);
        AdminDatabase = new AdminDatabasePanel(this);
        AdminVendorEdit = new AdminVendorEditPanel(this);
        Login = new LoginPanel(this);
        AdminChangePassword = new AdminChangePasswordPanel(this);

        // 延後初始化 VendorEditPanel（在 setSelectedVendorId 時再補）
        VendorEdit = new VendorEditPanel(this);

        mainPanel.add(VendorLogin, "VendorLogin");
        mainPanel.add(VendorEdit, "VendorEdit");
        mainPanel.add(CustomerLogin, "CustomerLogin");
        mainPanel.add(CustomerBrowse, "CustomerBrowse");
        mainPanel.add(CustomerDetail, "CustomerDetail");
        mainPanel.add(AdminLogin, "AdminLogin");
        mainPanel.add(AdminEdit, "AdminEdit");
        mainPanel.add(AdminBrowse, "AdminBrowse");
        mainPanel.add(AdminDatabase, "AdminDatabase");
        mainPanel.add(AdminVendorEdit, "AdminVendorEdit");
        mainPanel.add(Login, "Login");
        mainPanel.add(AdminChangePassword, "AdminChangePassword");


        add(mainPanel);
        layout.show(mainPanel, "Login");

        setTitle("政大市集+");
        setSize(405, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void switchTo(String panelName) {
        layout.show(mainPanel, panelName);
        System.out.println(panelName);
        if (panelName == "CustomerBrowse") {
            CustomerBrowse.filt();

        }
        if (panelName == "AdminBrowse") {

            AdminBrowse.filt();
        }

    }

    public void setSelectedVendorId(String id) {
        this.selectedVendorId = id;
        if (VendorEdit != null) {
            VendorEdit.stallIdField.setText(id); // 即時同步到輸入欄
        }
    }

    public String getSelectedVendorId() {
        return selectedVendorId;
    }

    public void refresh(String id) {
        CustomerDetail.refresh(id);
        AdminVendorEdit.refresh(id);// 偷懶方法
        VendorEdit.refresh(id);
    }

    public void setCustomerNickname(String nickname) {
        this.customerNickname = nickname;
    }

    public String getCustomerNickname() {
        return customerNickname;
    }
}
