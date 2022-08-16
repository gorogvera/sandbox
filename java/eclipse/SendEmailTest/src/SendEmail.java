/*
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

    public static void main(String[] args) {
        System.out.println("SimpleEmail Start");
        String smtpHostServer = "smtp.gmail.com";
        String toEmail = "gorog.veronika@gmail.com";
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.port", "587");
        Session session = Session.getDefaultInstance(props);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("progor.noreply@gmail.com", "progor.noreply.1981"));
            msg.setReplyTo(InternetAddress.parse("progor.noreply@gmail.com", false));
            msg.setSubject("SimpleEmail Testing Subject", "UTF-8");
            msg.setText("SimpleEmail Testing Body", "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);
            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    public static void main(String[] args) {
      String to = "gorog.veronika@gmail.com";
      String from = "progor.noreply@gmail.com";
      String host = "smtp.gmail.com";

      Properties properties = System.getProperties();
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.ssl.enable", "true");
      properties.put("mail.smtp.auth", "true");

      Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("progor.noreply@gmail.com", "wrcwvijulzgmrluy");
        }
      });

      try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Java programbol kuldott email");
        message.setText("Vegre sikerult!! Gmail smtp-vel, app passwd-t kellett hozza beallitani");

        Transport.send(message);
      } catch (MessagingException mex) {
        mex.printStackTrace();
      }
   }
}