import javax.swing.*;

public class AdminUI extends JFrame {
    public AdminUI() {
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Riwayat Transaksi", new TransactionHistoryPanel());
        tabbedPane.addTab("Manajemen Stok & Notifikasi", new StockManagementPanel());
        tabbedPane.addTab("Tambah Menu", new AddMenuPanel(this)); // this dikirim agar dialog bisa tampil

        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminUI::new);
    }
}
