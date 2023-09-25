package com.example.ptcwonderboost;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class historial extends AppCompatActivity {
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
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