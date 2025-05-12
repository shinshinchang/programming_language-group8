package Layout;
import java.awt.*;
import javax.swing.*;

public class AdminEditPanel extends JPanel {

    public JLayeredPane layeredPane;
    public JLabel title;
    public JPanel titlePanel;
    public JButton browseBtn;
    public JButton vendorDataBtn;
    public JButton pwChangeBtn;
    public JButton absoluteBackBtn;

    public AdminEditPanel(MainFrame frame) {
        setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        title = new JLabel("管理者介面", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(0, 0, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);

        browseBtn = new JButton("瀏覽店家及評論");
        browseBtn.setBounds(100, 100, 200, 40);
        browseBtn.addActionListener(e -> frame.switchTo("AdminBrowse"));
        layeredPane.add(browseBtn, JLayeredPane.DEFAULT_LAYER);

        vendorDataBtn = new JButton("攤位資料庫");
        vendorDataBtn.setBounds(100, 160, 200, 40);
        vendorDataBtn.addActionListener(e -> frame.switchTo("AdminDatabase"));
        layeredPane.add(vendorDataBtn, JLayeredPane.DEFAULT_LAYER);

        pwChangeBtn = new JButton("更改密碼");
        pwChangeBtn.setBounds(100, 220, 200, 40);
        pwChangeBtn.addActionListener(e -> frame.switchTo("pwChangeBtn"));
        layeredPane.add(pwChangeBtn, JLayeredPane.DEFAULT_LAYER);

        absoluteBackBtn = new JButton("←");
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }
}
