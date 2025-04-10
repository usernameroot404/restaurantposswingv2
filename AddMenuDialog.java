import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddMenuDialog extends JDialog {
    private JTextField nameField, priceField, stockField, imageField;
    private JTextArea descriptionArea;

    public AddMenuDialog(JFrame parent) {
        super(parent, "Tambah Menu", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Field
        nameField = new JTextField(20);

        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Garis di sekeliling

        priceField = new JTextField(20);
        stockField = new JTextField(20);
        imageField = new JTextField(20);
        imageField.setEditable(false);
        JButton browseBtn = new JButton("Pilih Gambar");

        // Tambah field
        addField(gbc, "Nama:", nameField, 0);
        addField(gbc, "Deskripsi:", descriptionArea, 1);
        addField(gbc, "Harga:", priceField, 2);
        addField(gbc, "Stok:", stockField, 3);
        addFieldWithButton(gbc, "Gambar:", imageField, browseBtn, 4);

        // Tombol Simpan
        JButton simpanBtn = new JButton("Simpan");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(simpanBtn, gbc);

        // Browse Gambar
        browseBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imageField.setText(file.getAbsolutePath());
            }
        });

        // Simpan Data
        simpanBtn.addActionListener(e -> {
            simpanData();
            dispose();
        });
    }

    private void addField(GridBagConstraints gbc, String label, Component comp, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(comp, gbc);
    }

    private void addFieldWithButton(GridBagConstraints gbc, String label, JTextField field, JButton btn, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(field, gbc);

        gbc.gridx = 2;
        add(btn, gbc);
    }

    private void simpanData() {
        try (Connection conn = KoneksiDB.getConnection()) {
            String sql = "INSERT INTO menu_items (name, description, price, stock, image_url) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nameField.getText());
            ps.setString(2, descriptionArea.getText());
            ps.setBigDecimal(3, new java.math.BigDecimal(priceField.getText()));
            ps.setInt(4, Integer.parseInt(stockField.getText()));
            ps.setString(5, imageField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data disimpan!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        }
    }
}
