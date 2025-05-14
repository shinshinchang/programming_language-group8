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
        title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 28));

        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title);
        titlePanel.setBounds(0, 40, 400, 60);
        layeredPane.add(titlePanel, JLayeredPane.DEFAULT_LAYER);

        browseBtn = new StyledButton("瀏覽店家及評論");
        browseBtn.setBounds(100, 140, 200, 45);
        browseBtn.addActionListener(e -> frame.switchTo("AdminBrowse"));
        layeredPane.add(browseBtn, JLayeredPane.DEFAULT_LAYER);

        vendorDataBtn = new StyledButton("攤位資料庫");
        vendorDataBtn.setBounds(100, 200, 200, 45);
        vendorDataBtn.addActionListener(e -> frame.switchTo("AdminDatabase"));
        layeredPane.add(vendorDataBtn, JLayeredPane.DEFAULT_LAYER);

        pwChangeBtn = new StyledButton("更改密碼");
        pwChangeBtn.setBounds(100, 260, 200, 45);
        pwChangeBtn.addActionListener(e -> frame.switchTo("pwChangeBtn"));
        layeredPane.add(pwChangeBtn, JLayeredPane.DEFAULT_LAYER);

        absoluteBackBtn = new StyledButton("←");
        absoluteBackBtn.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14));
        absoluteBackBtn.setPreferredSize(new Dimension(50, 30));
        absoluteBackBtn.setMargin(new Insets(2, 6, 2, 6));
        absoluteBackBtn.setBounds(10, 10, 50, 30);
        absoluteBackBtn.addActionListener(e -> frame.switchTo("Login"));
        layeredPane.add(absoluteBackBtn, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
    }
}
