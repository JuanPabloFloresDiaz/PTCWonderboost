package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class ReestablecerClave extends AppCompatActivity {

    private static final String USERNAME = "soportewonderboost@gmail.com"; // Cambia esto por tu correo
    private static final String PASSWORD = "fjttsuxuzkpabvbw"; // Cambia esto por tu contraseña

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_clave);
        EditText textCorreo = findViewById(R.id.editTextTextEmailAddress3);
        Button enviar = findViewById(R.id.btnConfirmarCorreo);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String g =  generateVerificationCode();
                String email = textCorreo.getText().toString();
                sendVerificationCode(email, g);
            }
        });

        Button Cancelar = findViewById(R.id.btnreglogin);
        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReestablecerClave.this, Login.class);
                startActivity(intent);
            }
        });
    }
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Genera un número de 6 dígitos
        return String.valueOf(code);
    }
    public static void sendVerificationCode(String recipientEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto si usas otro servidor
        props.put("mail.smtp.port", "465"); // Puerto seguro de Gmail

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Código de verificación");
            message.setText("Tu código de verificación es: " + verificationCode);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}