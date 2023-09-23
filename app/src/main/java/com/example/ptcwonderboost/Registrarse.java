package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.Random;

public class Registrarse extends AppCompatActivity {

    private EditText UsuarioEditText, ClaveEditText;

    private static String generarPing(){
        Random random = new Random();
        int verificationCode = 1000 + random.nextInt(9000); // Genera un número de 4 dígitos
        return String.valueOf(verificationCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        Button Cancel = findViewById(R.id.btncancelar);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registrarse.this, Login.class);
                startActivity(intent);
            }
        });

        UsuarioEditText = findViewById(R.id.txtusuarioR);
        ClaveEditText = findViewById(R.id.txtpass);
        String validacion1 = UsuarioEditText.getText().toString().trim();
        String validacion2 = ClaveEditText.getText().toString().trim();

        Button Registrarse = findViewById(R.id.btnregistrar);
        Registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UsuarioEditText.getText().toString();
                String password = ClaveEditText.getText().toString();
                if(Validaciones.Vacio(UsuarioEditText) || Validaciones.Vacio(ClaveEditText)) {
                    Toast.makeText(Registrarse.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
                }else if(!Validaciones.ValidarContrasena(password)) {
                    Toast.makeText(Registrarse.this, "La clave debe cumplir los siguientes requerimientos", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Registrarse.this, "La clave debe tener al menos una letra y un numero", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Registrarse.this, "La clave debe estar en el rango de 6 a 50 caracteres", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Usuario user = new Usuario();

                        String codigoPin = generarPing();
                        user.setUsuario(username);
                        user.setClave(password);
                        user.setPin(Integer.parseInt(codigoPin));
                        int valor = user.insertarUsuario(Registrarse.this);
                        if (valor == 1) {
                            Toast.makeText(Registrarse.this, "Se han ingresado los datos", Toast.LENGTH_SHORT);
                            ResultSet rs = user.CapturarID();
                            try {
                                while (rs.next()) {
                                    VariablesGlobales.idRegistro = rs.getInt("idUsuarios");
                                }
                                Toast.makeText(Registrarse.this, "id: " + VariablesGlobales.idRegistro, Toast.LENGTH_SHORT);
                            } catch (Exception ex) {
                                Toast.makeText(Registrarse.this, "Error: " + ex, Toast.LENGTH_SHORT);
                            }
                            Intent intent = new Intent(Registrarse.this, RegistrarsePersona.class);
                            startActivity(intent);
                        } else if (valor == 0) {
                            Toast.makeText(Registrarse.this, "Ha ocurrido un error inesperado ", Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        Toast.makeText(Registrarse.this, "Error: " + e, Toast.LENGTH_SHORT);
                    }
                }
            }
        });

    }
}
