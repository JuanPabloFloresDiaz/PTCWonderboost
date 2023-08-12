package com.example.ptcwonderboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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