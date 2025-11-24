package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    private static final String FROM_EMAIL = "payalparate545@gmail.com"; 
    private static final String PASSWORD   = "pytv qeic cpps efgi";  

    // Method to send booking email
    public static void sendBookingEmail(String toEmail, String eventName, String eventDate, String venue) {

        // SMTP properties for Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Authenticate your email
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            // Prepare message
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Booking Confirmation - " + eventName);

            String content = "Hello,\n\n"
                    + "Your event booking is confirmed!\n\n"
                    + "Event Name: " + eventName + "\n"
                    + "Date: " + eventDate + "\n"
                    + "Venue: " + venue + "\n\n"
                    + "Thank you for choosing us!\n"
                    + "Have a wonderful day.\n";

            message.setText(content);

            // Send email
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);

        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
 // Method to send booking cancellation email
    public static void sendCancellationEmail(String toEmail, String eventName, String eventDate, String venue) {

        // SMTP properties for Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Ticket Cancellation - " + eventName);

            String content = "Hello,\n\n"
                    + "Your event ticket has been cancelled.\n\n"
                    + "Event Name: " + eventName + "\n"
                    + "Event Date: " + eventDate + "\n"
                    + "Venue: " + venue + "\n\n"
                    + "If this cancellation was not done by you, please contact our support team.\n\n"
                    + "Regards,\nEvent Management Team";

            message.setText(content);

            Transport.send(message);
            System.out.println("Cancellation email sent to " + toEmail);

        } catch (MessagingException e) {
            System.out.println("Error sending cancellation email: " + e.getMessage());
        }
    }

}
