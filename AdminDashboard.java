import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton riwayatBtn = new JButton("Riwayat Transaksi");
        JButton stokBtn = new JButton("Manajemen Stok & Notifikasi");
        JButton tambahMenuBtn = new JButton("Tambah Menu");

        setLayout(new GridLayout(3, 1, 10, 10));
        add(riwayatBtn);
        add(stokBtn);
        add(tambahMenuBtn);

        // Event listeners
        riwayatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransactionHistoryPanel().setVisible(true);
            }
        });

        stokBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StockManagementPanel().setVisible(true);
            }
        });

        tambahMenuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMenuDialog(AdminDashboard.this).setVisible(true);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard());
    }
}