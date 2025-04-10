import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StockManagementPanel extends JPanel {
    private JTable table;

    public StockManagementPanel() {
        setLayout(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadStockData();
    }

    private void loadStockData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama Menu");
        model.addColumn("Stok");
        model.addColumn("Notifikasi");

        try (Connection conn = KoneksiDB.getConnection()) {
            String sql = "SELECT id, name, stock FROM menu_items";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int stock = rs.getInt("stock");
                String notif = (stock <= 5) ? "Stok menipis!" : "";
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        stock,
                        notif
                });
            }

            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load stok: " + e.getMessage());
        }
    }
}
