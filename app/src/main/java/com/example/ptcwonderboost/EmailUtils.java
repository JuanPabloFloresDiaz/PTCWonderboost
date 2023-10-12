package com.example.ptcwonderboost;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.net.ssl.SSLHandshakeException;

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

    public static void sendEmailNew(String recipient, String subject, String messageBody, Context context) {
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

            // Crear el contenido de correo personalizado
            MimeMultipart elementosCorreo = new MimeMultipart("related");

            // Agregar el mensaje de texto HTML
            MimeBodyPart contenido = new MimeBodyPart();
            contenido.setContent(messageBody, "text/html; charset=utf-8");
            elementosCorreo.addBodyPart(contenido);

            // Agregar la imagen incrustada
            MimeBodyPart encabezado = new MimeBodyPart();
            @SuppressLint("ResourceType") ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(context.getResources().openRawResource(R.drawable.encabezado), "image/png");
            encabezado.setDataHandler(new DataHandler(byteArrayDataSource));
            encabezado.setHeader("Content-ID", "<headerImage>");
            encabezado.setDisposition(MimeBodyPart.INLINE);
            elementosCorreo.addBodyPart(encabezado);

            // Agregar el contenido al mensaje
            message.setContent(elementosCorreo);

            Transport.send(message); // Envía el correo

            // Si llega aquí, el correo se envió correctamente
            System.out.println("Correo electrónico enviado exitosamente");
            Toast.makeText(context, "Correo electrónico enviado exitosamente", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            // Manejo de errores en caso de que el correo no se pueda enviar
            String errorMessage = "Error al enviar el correo electrónico: " + e.getMessage();
            Log.e("MiApp", errorMessage); // "MiApp" es una etiqueta que identifica tus mensajes en el LogCat
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmailOld(String recipient, String subject, String messageBody) {
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
