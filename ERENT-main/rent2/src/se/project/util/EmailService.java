package se.project.util;

import com.sun.mail.util.MailSSLSocketFactory;

import se.project.util.email.IMessageService;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailService implements IMessageService {

  public  void sendEmail(String user,String bikeName,String time,double money) {
    String to = user;//change accordingly
    String from = "dangquoctuhn@gmail.com";//change accordingly

    // Mention the SMTP server address. Below Gmail's SMTP server is being used to send email
    String host = "smtp.gmail.com";

    MailSSLSocketFactory sf;
    try {
      sf = new MailSSLSocketFactory();
      sf.setTrustAllHosts(true);
      // or
      // sf.setTrustedHosts(new String[] { "my-server" });

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "587");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.ssl.socketFactory", sf);
      // Get the Session object.// and pass username and password
      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

        protected PasswordAuthentication getPasswordAuthentication() {

          return new PasswordAuthentication("dangquoctuhn@gmail.com", "ozlzfsvmztsaxgpt");

        }

      });

      // Used to debug SMTP issues
      session.setDebug(true);
      try {
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);
        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));
        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // Set Subject: header field
        message.setSubject("[EBIKE-PAYMENT DETAILS]");

        MimeMultipart multipart = new MimeMultipart("related");
            
        // first part (the html )
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<p>Dear "+to+"</p>"
        +"<p>Here is your payment result:</p><p>Bike Rent:"+bikeName+
        "</p><p>Time:"+time
        +"</p><p>Money:"+  MyUtils.moneyformat1.format(money)
        +"</p><p>Have a great day</p><p>Sincerely, &nbsp; &nbsp;</p><p>Dang Quoc Tu &nbsp; &nbsp;&nbsp;</p>"
        +"<p>Software Engineer&nbsp;</p><p>Salary Manager Team</p><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html");
        // add it
        multipart.addBodyPart(messageBodyPart);

        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(
           "src/se/project/image/"+ bikeName + ".jpeg");

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");

        // add image to the multipart
        multipart.addBodyPart(messageBodyPart);
       
        // put everything together
        message.setContent(multipart);
        
        System.out.println("sending...");
        // Send message
        Transport.send(message);

        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
        mex.printStackTrace();
      }
    } catch (GeneralSecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }


  public static void main(String[] args) {
  }
}