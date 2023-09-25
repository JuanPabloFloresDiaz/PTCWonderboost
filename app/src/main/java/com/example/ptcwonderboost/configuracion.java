package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.InputStream;

public class configuracion extends AppCompatActivity {
    ImageButton AdministraClave, Politica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        AdministraClave = findViewById(R.id.btnAdministrarContraseña);
        Politica = findViewById(R.id.btnPDF);

        AdministraClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes abrir una nueva pantalla, por ejemplo:
                Intent intent = new Intent(configuracion.this, ReestablecerContrasena.class);
                startActivity(intent);
            }
        });
        Politica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes abrir una nueva pantalla, por ejemplo:

            }
        });
    }
}