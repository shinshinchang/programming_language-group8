package Layout;

import java.awt.event.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;
import java.net.URL;
import java.lang.reflect.Type;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

   
}