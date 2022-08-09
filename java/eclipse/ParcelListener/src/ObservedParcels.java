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
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/partialnumber_db","admin","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from partial_numbers");
			
			while (rs.next()) {
				int parcelId = rs.getInt(1);
				String parcelNr = rs.getString(2);
				
				Parcel p = new Parcel(parcelNr, parcelId);
				observedParcels.add(p);
				
				System.out.println("Added observed parcel with id: " + parcelId + ", and number: " + parcelNr);
			}
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
