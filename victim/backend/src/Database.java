import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DB_URL = "jdbc:mysql://db:3306/mydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "myuser";
    private static final String DB_PASS = "mypassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static List<String[]> getProducts() {
        List<String[]> products = new ArrayList<>();
        String sql = "SELECT id, name FROM devices";
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
			
        	while (rs.next()) {
            	String[] product = new String[2];
            	product[0] = String.valueOf(rs.getInt("id"));
            	product[1] = rs.getString("name");
            	products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public static void newPurchase(int device, String email) {
    	String sql = "INSERT INTO purchases (device, email) VALUES (?, ?)";
    	try (Connection conn = getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql)) {

        	pstmt.setInt(1, device);
        	pstmt.setString(2, email);
        	pstmt.executeUpdate();

    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}
