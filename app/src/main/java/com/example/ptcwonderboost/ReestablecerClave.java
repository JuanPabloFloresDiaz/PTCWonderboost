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
import android.widget.Toast;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class ReestablecerClave extends AppCompatActivity {

    private static final String USERNAME = "soportewonderboost@gmail.com"; // Correo emisor
    private static final String PASSWORD = "fjttsuxuzkpabvbw"; // Contraseña del correo emisor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_clave);
        EditText textCorreo = findViewById(R.id.editTextTextEmailAddress3);
        //Acciones del boton enviar
        Button enviar = findViewById(R.id.btnConfirmarCorreo);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String g =  generateVerificationCode();
                    String email = textCorreo.getText().toString();
                    sendVerificationCode(email, g);
                }catch (Exception ex){
                    Toast.makeText(ReestablecerClave.this,"Error: " + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Acciones del boton cancelar
        Button Cancelar = findViewById(R.id.btnreglogin);
        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReestablecerClave.this, Login.class);
                startActivity(intent);
            }
        });
    }
    //Metodo para generar un codigo de verificación
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
    //Envio de correos.
    public static void sendVerificationCode(String recipientEmail, String verificationCode) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

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

        }
    }
}