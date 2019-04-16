import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

class EmailThread extends Thread {
	private String toEmail;
	public EmailThread(String email) {
		this.toEmail = email;
	}

	@Override
	public void run() {
		String mailTo = this.toEmail;
		String mailSubject = "Your friend made a playlist!";
		String mailText = "Check out your friends latest playlist!";
    Email playlistEmail = new Email(mailTo, mailSubject, mailText);
    playlistEmail.sendEmail();
	}

}

public class Email {
	final String senderEmailID = "audioface201@gmail.com";
	final String senderPassword = "audioface123";
	final String emailSMTPserver = "smtp.gmail.com";
	final String emailServerPort = "465";
	String receiverEmailID = null;
	static String emailSubject;
	static String emailBody;
	
	public Email(String receiverEmailID, String Subject, String Body) {
	  // Receiver Email Address
	  this.receiverEmailID=receiverEmailID; 
	  // Subject
	  this.emailSubject=Subject;
	  // Body
	  this.emailBody=Body;
	}
	
	public void sendEmail() {
	  Properties props = new Properties();
	  props.put("mail.smtp.user",senderEmailID);
	  props.put("mail.smtp.host", emailSMTPserver);
	  props.put("mail.smtp.port", emailServerPort);
	  props.put("mail.smtp.starttls.enable", "true");
	  props.put("mail.smtp.auth", "true");
	  props.put("mail.smtp.socketFactory.port", emailServerPort);
	  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	  props.put("mail.smtp.socketFactory.fallback", "false");
	  SecurityManager security = System.getSecurityManager();
	  
	  try {  
		  Authenticator auth = new SMTPAuthenticator();
		  Session session = Session.getInstance(props, auth);
		  MimeMessage msg = new MimeMessage(session);
		  msg.setText(emailBody);
		  msg.setSubject(emailSubject);
		  msg.setFrom(new InternetAddress(senderEmailID));
		  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmailID));
		  Transport.send(msg);
		  System.out.println("Message send Successfully:)");
	  } catch (Exception mex){
		  mex.printStackTrace();
	  }
	}
	
	public class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(senderEmailID, senderPassword);
		}
	}

    public static void main(String[] args) {
    	String mailTo = "stacyviviphan@gmail.com";
		String mailSubject = "Your friend made a playlist!";
		String mailText = "Check out your friends latest playlist!";
    	Email playlistEmail = new Email(mailTo, mailSubject, mailText);
    	playlistEmail.sendEmail();
    }

}