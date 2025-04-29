package Layout;

import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();  // MainFrame 已經包含 setVisible(true)
        });
    }
}

