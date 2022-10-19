import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Maintenance {

	public static void clearDatabase() {
		try {
			// Clear tokens with expired dates
			// TODO
			
			// Remove partial numbers which not connected to any user
			removePartialNumberWhichNotConnectedToUser();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DatabaseHelper.close();
		}
	}
	
	private static void removePartialNumberWhichNotConnectedToUser() throws SQLException, ClassNotFoundException {
		Statement smt = DatabaseHelper.getStatement();
		ResultSet res = smt.executeQuery("select partial_numbers.id from partial_numbers left join user_pnumber_connections on partial_numbers.id = user_pnumber_connections.pnumber_id where pnumber_id IS NULL;");
		
		while (res.next()) {
			Integer pNumberId = res.getInt(1);
			smt = DatabaseHelper.getStatement();
			smt.execute("delete from partial_numbers where id="+pNumberId);
			System.out.println("Parcel is deleted with id: "+pNumberId);
		}
	}
}
