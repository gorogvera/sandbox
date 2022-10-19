import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

	private static Connection connection = null;
	private static final String DATABASE_NAME = "jdbc:mysql://localhost:3306/partialnumber_spring_db";
	private static final String DATABASE_USER = "admin";
	private static final String DATABASE_PASSWORD = "password";
	
	public static void connect() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DATABASE_NAME,DATABASE_USER,DATABASE_PASSWORD);
		}
	}
	
	public static Statement getStatement() throws SQLException, ClassNotFoundException {
		if (connection == null) connect();
		return connection.createStatement();
	}
	
	public static void close() {
		try {
			if (connection != null) connection.close();
		} catch (SQLException se) {
			// TODO some logs
			System.out.println("Close database is not possible:" + se.getMessage());
		} finally {
			connection = null;
		}
	}
	
}
