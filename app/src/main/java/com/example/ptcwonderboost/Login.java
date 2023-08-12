package com.example.ptcwonderboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.txtusuario);
        EditText passwordEditText = findViewById(R.id.txtcontra);

        Button loginButton = findViewById(R.id.btningresar);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Usuario usuario = new Usuario();
                usuario.setUsuario(username);
                usuario.setClave(password);

                    if (usuario.Login()) {
                        // Inicio de sesión correcto
                        Toast.makeText(Login.this,"Credenciales correctas",Toast.LENGTH_SHORT).show();
                    } else {
                        // Inicio de sesión incorrecto
                        Toast.makeText(Login.this,"Error credenciales incorrectas",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception ex){
                    Toast.makeText(Login.this,"Error" + ex,Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button testConnectionButton = findViewById(R.id.testConnectionButton);
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