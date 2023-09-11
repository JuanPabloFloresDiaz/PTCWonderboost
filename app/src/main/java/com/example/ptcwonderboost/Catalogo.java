package com.example.ptcwonderboost;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Catalogo extends AppCompatActivity {

    ListView lista;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        CargarLista();

    }

    public final void CargarLista(){
        try {
            lista = findViewById(R.id.listaCatalogo);
            Producto producto = new Producto();
            List<Producto> productos = producto.CargarCatalogos();

            if (productos != null) {
                ImagenListAdapter adapter = new ImagenListAdapter(this, productos);
                lista.setAdapter(adapter);
            } else {
                Toast.makeText(Catalogo.this, "No se ha podido cargar la tabla", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(Catalogo.this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}