package com.example.ptcwonderboost;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventarioActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> datos;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        CargarLista();
    }

    public final void CargarLista(){
        try {
            listView = findViewById(R.id.listViewInventario);
            Producto producto = new Producto();
            producto.setIdPersona(VariablesGlobales.getIdPersona());
            List<Producto> productos = producto.CargarInventario();

            if (productos != null) {
                ImagenListAdapter adapter = new ImagenListAdapter(this, productos);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(InventarioActivity.this, "No se ha podido cargar la tabla", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(InventarioActivity.this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}