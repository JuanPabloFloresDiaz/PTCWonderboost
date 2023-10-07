package com.example.ptcwonderboost;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {
    private static final String EMAIL = "soportewonderboost@gmail.com"; // Tu dirección de correo electrónico
    private static final String PASSWORD = "fjttsuxuzkpabvbw";   // Tu contraseña de correo electrónico

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");     // Servidor SMTP de Gmail
        properties.put("mail.smtp.socketFactory.port", "465");  // Puerto para SSL (Gmail usa el puerto 465)
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");               // Habilita la autenticación
        properties.put("mail.smtp.port", "465");                // Puerto SMTP (Gmail usa el puerto 465)
        return properties;
    }

    public static void sendEmail(String recipient, String subject, String messageBody) {
        Session session = Session.getDefaultInstance(getProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message); // Envía el correo

            // Si llega aquí, el correo se envió correctamente
            System.out.println("Correo electrónico enviado exitosamente");
        } catch (MessagingException e) {
            // Manejo de errores en caso de que el correo no se pueda enviar
            System.err.println("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }
}
