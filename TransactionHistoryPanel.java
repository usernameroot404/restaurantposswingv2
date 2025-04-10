import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TransactionHistoryPanel extends JPanel {
    private JTable table;

    public TransactionHistoryPanel() {
        setLayout(new BorderLayout());

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadTransactions();
    }

    private void loadTransactions() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Order ID");
        model.addColumn("Amount");
        model.addColumn("Method");
        model.addColumn("Waktu");

        try (Connection conn = KoneksiDB.getConnection()) {
            String sql = "SELECT * FROM payments ORDER BY transaction_time DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getBigDecimal("amount"),
                        rs.getString("payment_method"),
                        rs.getTimestamp("transaction_time")
                });
            }

            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load transaksi: " + e.getMessage());
        }
    }
}
