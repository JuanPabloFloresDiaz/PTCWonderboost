package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReestablecerContrasena extends AppCompatActivity {

    private EditText txtClave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_contrasena);
        txtClave = findViewById(R.id.txtClaveNueva);
        Button enviar = findViewById(R.id.btnCambiarClave);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String clave = txtClave.getText().toString();
                    if (!Validaciones.ValidarContrasena(clave)) {
                        Toast.makeText(ReestablecerContrasena.this, "La clave debe cumplir los siguientes requerimientos", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ReestablecerContrasena.this, "La clave debe tener al menos una letra y un numero", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ReestablecerContrasena.this, "La clave debe estar en el rango de 6 a 50 caracteres", Toast.LENGTH_SHORT).show();
                    } else {
                        Usuario recuperacion = new Usuario();
                        recuperacion.setClave(clave);
                        recuperacion.setId(VariablesGlobales.getIdUsuario());

                        // Llama al método Restablecer clave
                        int res = recuperacion.ActualizarContraseña();
                        if (res == 1) {
                            // Usuario y ping son válidos, permite al usuario restablecer su contraseña
                            // Redirige a la actividad de restablecimiento de contraseña
                            Intent intent = new Intent(ReestablecerContrasena.this, Login.class);
                            startActivity(intent);
                            Toast.makeText(ReestablecerContrasena.this, "Su clave ha sido correctamente cambiada", Toast.LENGTH_SHORT).show();
                        } else {
                            // Usuario o ping inválido, muestra un mensaje de error al usuario
                            Toast.makeText(ReestablecerContrasena.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception ex){
                    Toast.makeText(ReestablecerContrasena.this,"Error: " + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}