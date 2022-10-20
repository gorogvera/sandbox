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
			Statement stmt = DatabaseHelper.getStatement();
			
			for (Parcel foundP : foundParcels) {
				ResultSet rs = stmt.executeQuery(
						"select email,pnumber_id from user_emails_with_pnumber_connections "
						+ "where pnumber_id = "+ foundP.getParcelId() +";");
				
				while (rs.next()) {
					String emailAddr = rs.getString(1);
					
					System.out.println("Sending email to " + emailAddr + " with parcel number: " + foundP.getParcelNumber());
					
					sendMail(emailAddr, foundP.getParcelUrl(), foundP.getParcelNumber());
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO some logs
			// TODO make more specific exceptions with logs
		} finally {
			DatabaseHelper.close();
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
