package util;
import javafx.scene.control.Alert;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    public static void sendEmail(String to, String otp) {
        final String from = "omalmaleesha85@gmail.com";
        final String password = "hyue wgfc wtnq wdip";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);
            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION,"OTP sent successfully").show();
            System.out.println("OTP sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}