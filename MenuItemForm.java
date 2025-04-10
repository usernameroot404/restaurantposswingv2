import java.awt.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MenuItemForm extends JFrame {
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private Cart cart;

    public MenuItemForm() {
        setTitle("Daftar Menu");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cart = new Cart();

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Harga", "Stok"}, 0);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton tambahKeKeranjangBtn = new JButton("Tambah ke Keranjang");
        tambahKeKeranjangBtn.addActionListener(e -> tambahKeKeranjang());

        JButton lihatKeranjangBtn = new JButton("Lihat Keranjang");
        lihatKeranjangBtn.addActionListener(e -> {
            CartDialog dialog = new CartDialog(this, cart);
            dialog.setVisible(true);
            loadMenu(); // Refresh stok setelah checkout
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(tambahKeKeranjangBtn);
        bottomPanel.add(lihatKeranjangBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadMenu();
    }

    private void tambahKeKeranjang() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            BigDecimal price = (BigDecimal) tableModel.getValueAt(selectedRow, 2);
            int stock = (int) tableModel.getValueAt(selectedRow, 3);

            if (stock <= 0) {
                JOptionPane.showMessageDialog(this, "Stok habis!");
                return;
            }

            String qtyStr = JOptionPane.showInputDialog(this, "Jumlah yang ingin ditambahkan:");
            int qty = Integer.parseInt(qtyStr);

            if (qty > stock) {
                JOptionPane.showMessageDialog(this, "Jumlah melebihi stok!");
                return;
            }

            CartItem item = new CartItem(id, name, price, qty);
            cart.addItem(item);
            JOptionPane.showMessageDialog(this, "Item ditambahkan ke keranjang!");
        } else {
            JOptionPane.showMessageDialog(this, "Pilih item terlebih dahulu.");
        }
    }

    private void loadMenu() {
        tableModel.setRowCount(0);
        try (Connection conn = KoneksiDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM menu_items")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("name");
                BigDecimal harga = rs.getBigDecimal("price");
                int stok = rs.getInt("stock");
                tableModel.addRow(new Object[]{id, nama, harga, stok});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data menu: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuItemForm().setVisible(true));
    }
}
