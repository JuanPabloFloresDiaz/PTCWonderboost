package com.example.ptcwonderboost;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Necociaciones extends AppCompatActivity {
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_necociaciones);
        CargarLista();
    }

    public final void CargarLista(){
        try {
            lista = findViewById(R.id.listviewNeg);
            Negociaciones negociacion = new Negociaciones();
            List<Negociaciones> negocio = negociacion.CargarNegociaciones(VariablesGlobales.getIdPersona());

            if (negocio != null) {
                CartAdapter adapter = new CartAdapter(this, negocio);
                lista.setAdapter(adapter);
            } else {
                Toast.makeText(Necociaciones.this, "No se ha podido cargar la tabla", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(Necociaciones.this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}