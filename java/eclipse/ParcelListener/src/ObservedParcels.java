import java.sql.*;
import java.util.ArrayList;

/* In mysql I have created a user called admin and I used it for the connection
*
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost'
WITH GRANT OPTION;

CREATE USER 'admin'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%'
WITH GRANT OPTION; 
* 
*/

final public class ObservedParcels {

	public static ArrayList<Parcel> observedParcels = new ArrayList<Parcel>();
	
	public static void fillObservedParcels() {
		try {
			Statement stmt = DatabaseHelper.getStatement();
			ResultSet rs = 
					stmt.executeQuery("select * from partial_numbers_with_locality_names;");
			
			while (rs.next()) {
				int parcelId = rs.getInt(1);
				String parcelNr = rs.getString(2);
				String locality = rs.getString(3);
				
				Parcel p = new Parcel(parcelNr, locality, parcelId);
				observedParcels.add(p);
				
				System.out.println("Added observed parcel:" + p);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO some logs
			// TODO make more specific exceptions with logs
		} finally {
			DatabaseHelper.close();
		}
	}
}
