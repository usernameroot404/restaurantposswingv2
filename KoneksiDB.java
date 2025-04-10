import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant_pos";
    private static final String DB_USER = "root";
    private static final String DB_PASS = ""; // Ubah sesuai konfigurasi MySQL kamu

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
