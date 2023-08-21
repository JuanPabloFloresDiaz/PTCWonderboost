package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class ReestablecerClave extends AppCompatActivity {

    private static final String USERNAME = "soportewonderboost@gmail.com"; // Correo emisor
    private static final String PASSWORD = "fjttsuxuzkpabvbw"; // Contraseña del correo emisor
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 20;

    public String generateToken() {
        try{
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(randomIndex));
        }

        return token.toString();}
        catch (Exception e){
            Toast.makeText(ReestablecerClave.this, "Error" + e, Toast.LENGTH_LONG);
            return null;
        }
    }

    public void mandarCorreo(){
        try{
            EditText textCorreo = findViewById(R.id.editTextTextEmailAddress3);
            //Preparar las cosas
            String correoEmisor = "soportewonderboost@gmail.com";
            String contraseñaEmisor = "fjttsuxuzkpabvbw";
            String CorreoReceptor = textCorreo.getText().toString();;
            String asunto = "Recuperación de contraseña";
            String mensaje = "Tu codigo de verificación es: "+ generateToken();

            //Configurar SMTP
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starls.enable", "true");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //Configurar el envio
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoEmisor));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(CorreoReceptor));
            message.setSubject(asunto);
            message.setText(mensaje);
            //Envío del mensaje
            Transport t = session.getTransport("smtp");
            t.connect(correoEmisor, contraseñaEmisor);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            Toast.makeText(ReestablecerClave.this,"Correo enviado ", Toast.LENGTH_SHORT).show();
        }catch(NullPointerException e){
            Toast.makeText(ReestablecerClave.this,"Error +: " + e, Toast.LENGTH_SHORT).show();
        }catch(NetworkOnMainThreadException e){
            Toast.makeText(ReestablecerClave.this,"Error +: " + e, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(ReestablecerClave.this,"Error +: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mandarCorreo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Realizar acciones después de enviar el correo, si es necesario
        }
    }
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
                   new SendEmailTask().execute();
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
}