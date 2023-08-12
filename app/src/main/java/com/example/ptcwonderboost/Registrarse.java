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

public class Registrarse extends AppCompatActivity {

    private EditText UsuarioEditText, ClaveEditText;

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
//                if (TextUtils.isEmpty(validacion1)) {
//                    Toast.makeText(Registrarse.this,"El campos de usuario esta vacio", Toast.LENGTH_SHORT);
//                } else if (TextUtils.isEmpty(validacion2)) {
//                    Toast.makeText(Registrarse.this,"El campos de clave esta vacio", Toast.LENGTH_SHORT);
//                }else {
                    try {
                        Usuario user = new Usuario();
                        String username = UsuarioEditText.getText().toString();
                        String password = ClaveEditText.getText().toString();
                        user.setUsuario(username);
                        user.setClave(password);
                        user.insertarUsuario(Registrarse.this);
                        Toast.makeText(Registrarse.this, "Se han ingresado los datos", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(Registrarse.this, RegistrarsePersona.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(Registrarse.this, "Error: " + e, Toast.LENGTH_SHORT);
                    }
                }
//            }
        });

    }
}
