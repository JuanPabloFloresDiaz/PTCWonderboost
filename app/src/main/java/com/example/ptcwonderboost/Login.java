package com.example.ptcwonderboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Login extends AppCompatActivity {

    private EditText user1;
    private EditText contra1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button testConnectionButton = findViewById(R.id.testConnectionButton);
        user1 = (EditText) findViewById(R.id.txtusuario);
        contra1 = (EditText) findViewById(R.id.txtcontra);
        testConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDatabaseConnection();
            }
        });

        TextView registrarse = findViewById(R.id.twcuenta);
            registrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login.this, Registrarse.class);
                    startActivity(intent);
                }
            });

           TextView recuperacion = findViewById(R.id.twclave);
           recuperacion.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(Login.this, ReestablecerClave.class);
                   startActivity(intent);
               }
            });

        EditText usernameEditText = findViewById(R.id.txtusuario);
        EditText passwordEditText = findViewById(R.id.txtcontra);

        Button loginButton = findViewById(R.id.btningresar);
        loginButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                try {
                    String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();

                    Usuario usuario = new Usuario();
                    usuario.setUsuario(username);
                    usuario.setClave(password);

                    if (usuario.Login()) {
                        // Inicio de sesión correcto
                        Toast.makeText(Login.this, "Credenciales correctas", Toast.LENGTH_SHORT).show();
                    } else {
                        // Inicio de sesión incorrecto
                        Toast.makeText(Login.this, "Error credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Toast.makeText(Login.this, "Error" + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void testDatabaseConnection() {
        Connection connection = Conexion.getConnection(this);
        if (connection != null) {
            Toast.makeText(this, "Conexión exitosa", Toast.LENGTH_SHORT).show();
            Conexion.closeConnection(connection);
        } else {
            Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show();
        }
    }




}