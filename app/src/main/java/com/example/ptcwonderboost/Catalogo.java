package com.example.ptcwonderboost;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Catalogo extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private int idSeleccionado;
    private double precioUnitario;
    ListView lista;
    SearchView buscador;
    private void abrirNegociacion() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, negociacion.class);
        startActivity(intent);
    }

    private void AgregarCarrito(){
        Producto carrito = new Producto();
        try{
            carrito.setIdPersona(VariablesGlobales.getIdPersona());
            carrito.setIdProducto(VariablesGlobales.getIdProducto());
            carrito.setPrecioUnitario(precioUnitario);
            int valor = carrito.AgregarAlCarritoProducto();
            if(valor == 1){
                Toast.makeText(this,"Se ha agregado al carrito", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"ERROR ", Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex){
            Toast.makeText(this,"ERROR: " + ex, Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        CargarLista();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    // Obtén el producto seleccionado
                    Producto productoSeleccionado = (Producto) parent.getItemAtPosition(position);
                    // Guarda el valor del campo "id" en la variable idSeleccionado
                    idSeleccionado = productoSeleccionado.getIdProducto();
                    precioUnitario = productoSeleccionado.getPrecio();
                    VariablesGlobales.idProducto = idSeleccionado;
                    Toast.makeText(Catalogo.this, "id: " + VariablesGlobales.idProducto, Toast.LENGTH_LONG).show();
                }catch (Exception ex){
                    Toast.makeText(Catalogo.this, "ERROR: " + ex, Toast.LENGTH_LONG).show();
                }
                // Aquí puedes mostrar un AlertDialog con opciones
                mostrarDialogoDeOpciones();
            }
        });

        // Configura el SearchView
        buscador = findViewById(R.id.searchView);
        buscador.setOnQueryTextListener(this);
    }
    private void mostrarDialogoDeOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción")
                .setItems(new CharSequence[]{"Ir a negociaciones", "Agregar al carrito"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí puedes manejar la selección del usuario
                        switch (which) {
                            case 0:
                                abrirNegociacion();
                                break;
                            case 1:
                                AgregarCarrito();
                                break;
                        }
                    }
                })
                .create()
                .show();
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

    private void buscarProductos(String query) {
        try {
            lista = findViewById(R.id.listaCatalogo);
            Producto producto = new Producto();
            producto.setProducto(query); // Establece el término de búsqueda en tu objeto Producto
            List<Producto> productos = producto.BuscarCatalogos();

            if (productos != null) {
                ImagenListAdapter adapter = new ImagenListAdapter(this, productos);
                lista.setAdapter(adapter);
            } else {
                Toast.makeText(Catalogo.this, "No se ha encontrado ningún producto.", Toast.LENGTH_SHORT).show();
                CargarLista();
            }
        } catch (Exception ex) {
            Toast.makeText(Catalogo.this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Realiza la búsqueda y actualiza la lista según el término de búsqueda
        buscarProductos(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        // Puedes realizar acciones mientras el usuario escribe en el SearchView si lo deseas
        buscarProductos(query);
        return false;
    }
}