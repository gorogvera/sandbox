import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Maintenance {

	public static void clearDatabase() {
		try {
			// Clear tokens with expired dates
			removeExpiredTokens();
			
			// Clear not enabled users
			clearNotEnabledUsers();
			
			// Remove partial numbers which not connected to any user
			removePartialNumberWhichNotConnectedToUser();
			
			// TODO remove old entries in log_daily_run table
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DatabaseHelper.close();
		}
	}
	
	private static void removePartialNumberWhichNotConnectedToUser() throws SQLException, ClassNotFoundException {
		Statement smt = DatabaseHelper.getStatement();
		ResultSet res = smt.executeQuery("select * from partial_numbers_not_connected_to_user;");
		
		while (res.next()) {
			Integer pNumberId = res.getInt(1);
			smt = DatabaseHelper.getStatement();
			smt.execute("delete from partial_numbers where id="+pNumberId);
			System.out.println("Parcel is deleted with id: "+pNumberId);
		}
	}
	
	private static void removeExpiredTokens() throws SQLException, ClassNotFoundException {
		Statement smt = DatabaseHelper.getStatement();
		smt.execute("delete from password_reset_token where expiry_date < NOW()");
	}
	
	private static void clearNotEnabledUsers() throws SQLException, ClassNotFoundException {
		Statement smt = DatabaseHelper.getStatement();
		smt.execute("delete from users where enabled=0");
	}
	
}
