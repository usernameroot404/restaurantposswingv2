import javax.swing.*;
import java.awt.*;

public class AddMenuPanel extends JPanel {
    public AddMenuPanel(JFrame parent) {
        setLayout(new BorderLayout());

        JButton tambahBtn = new JButton("Tambah Menu Baru");
        tambahBtn.addActionListener(e -> new AddMenuDialog(parent).setVisible(true));

        add(tambahBtn, BorderLayout.NORTH);
    }
}
