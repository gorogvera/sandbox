import java.sql.*;

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

public class MySqlMain {

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/partialnumber_db","admin","password");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from email_addresses");
			
			while (rs.next()) {
				System.out.println(rs.getString(2));				
			}
			conn.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

}

