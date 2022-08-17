import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	private static Session session;
	private static String from = "progor.noreply@gmail.com";
	private static String host = "smtp.gmail.com";
	
	
	public static void sendAll(ArrayList<Parcel> foundParcels) {
		
	      Properties properties = System.getProperties();
	      properties.put("mail.smtp.host", host);
	      properties.put("mail.smtp.port", "465");
	      properties.put("mail.smtp.ssl.enable", "true");
	      properties.put("mail.smtp.auth", "true");

	      session = Session.getInstance(properties, new javax.mail.Authenticator(){
	        protected PasswordAuthentication getPasswordAuthentication() {
	          return new PasswordAuthentication("progor.noreply@gmail.com", "wrcwvijulzgmrluy");
	        }
	      });
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/partialnumber_db","admin","password");
			Statement stmt = conn.createStatement();
			
			for (Parcel foundP : foundParcels) {
				ResultSet rs = stmt.executeQuery("select email_address from email_pnumber_connections "
						+ "left join email_addresses on email_pnumber_connections.email_id = email_addresses.email_id "
						+ "where pnumber_id = "+ foundP.getParcelId() +";");
				
				while (rs.next()) {
					String emailAddr = rs.getString(1);
					
					System.out.println("Sending email to " + emailAddr + " with parcel number: " + foundP.getParcelNumber());
					
					sendMail(emailAddr, foundP.getParcelUrl(), foundP.getParcelNumber());
				}
			}
			
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static void sendMail(String addr, String url, String pNumber) {
		 try {
		        MimeMessage message = new MimeMessage(session);
		        message.setFrom(new InternetAddress(from));
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(addr));
		        message.setSubject(pNumber + " kifüggesztésre került");
		        message.setText("Részletekért kattintson az alábbi linkre: " + url);

		        Transport.send(message);
		      } catch (MessagingException mex) {
		        mex.printStackTrace();
		      }
	}
}