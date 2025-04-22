import java.awt.CardLayout;
import javax.swing.*;

public class MainFrame extends JFrame {
    private int selectedVendorId;
    CardLayout layout;
    JPanel mainPanel;

    public MainFrame() {
        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new VendorLoginPanel(this), "VendorLogin");
        mainPanel.add(new VendorEditPanel(this), "VendorEdit");
        mainPanel.add(new CustomerLoginPanel(this), "CustomerLogin");
        mainPanel.add(new CustomerBrowsePanel(this), "CustomerBrowse");
        mainPanel.add(new CustomerDetailPanel(this), "CustomerDetail");
        mainPanel.add(new AdminPanel(this), "Admin");

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

    public void setSelectedVendorId(int id) {
        this.selectedVendorId = id;
    }

    public int getSelectedVendorId() {
        return selectedVendorId;
    }
}
