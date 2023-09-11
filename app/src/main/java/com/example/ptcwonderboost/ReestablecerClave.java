package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
public class ReestablecerClave extends AppCompatActivity {

    private EditText txtPing, txtUsuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_clave);
        txtPing = findViewById(R.id.txtPin);
        txtUsuario = findViewById(R.id.txtPinUsuario);
        //Acciones del boton enviar
        Button enviar = findViewById(R.id.btnConfirmarPin);
       enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   String usuario = txtUsuario.getText().toString();
                   int ping = Integer.parseInt(txtPing.getText().toString());

                   Recuperacion recuperacion = new Recuperacion();
                   recuperacion.setUsuario(usuario);
                   recuperacion.setPing(ping);

                   // Llama al método RecuperacionPing() para verificar la existencia del usuario y ping
                   int res = recuperacion.RecuperacionPing();
                   if (res == 1) {
                       // Usuario y ping son válidos, permite al usuario restablecer su contraseña
                       // Redirige a la actividad de restablecimiento de contraseña
                       int idUsuario = recuperacion.getIdUsuario();
                       VariablesGlobales.setIdUsuario(idUsuario);
                       Intent intent = new Intent(ReestablecerClave.this, ReestablecerContrasena.class);
                       startActivity(intent);
                       Toast.makeText(ReestablecerClave.this,"Pin correcto: " + usuario, Toast.LENGTH_SHORT).show();
                   } else {
                       // Usuario o ping inválido, muestra un mensaje de error al usuario
                       Toast.makeText(ReestablecerClave.this, "Usuario o ping inválido", Toast.LENGTH_SHORT).show();
                   }
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