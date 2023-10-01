package com.example.ptcwonderboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class historial extends AppCompatActivity {
    ListView lista;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        bottomNavigationView = findViewById(R.id.bottom_navigationH);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.EnvioIngreso) {
                    startActivity(new Intent(historial.this, Envios.class));
                    return true;
                } else if (item.getItemId() == R.id.Gestionar) {
                    startActivity(new Intent(historial.this, gestionar.class));
                    return true;
                } else if (item.getItemId() == R.id.enviohistorial) {
                    startActivity(new Intent(historial.this, historial.class));
                    return true;
                }
                return false;
            }
        });
        CargarLista();
    }

    public final void CargarLista(){
        try {
            lista = findViewById(R.id.listviewHistorial);
            Envio negociacion = new Envio();
            List<Envio> negocio = negociacion.cargarTablaHistorialEnvios(VariablesGlobales.getIdPersona());

            if (negocio != null) {
                EnvioAdapter adapter = new EnvioAdapter(this, negocio);
                lista.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se ha podido cargar la tabla", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}