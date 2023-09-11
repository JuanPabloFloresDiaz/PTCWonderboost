package com.example.ptcwonderboost;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        // Inicializa el ListView
        listView = findViewById(R.id.listViewInventario);

        // Llamar al método mostrarInventarioProductos() para obtener los datos
        Producto producto = new Producto();
        ResultSet rs = producto.mostrarinventarioProductos();

        // Procesa los datos del ResultSet y agrégalos a la lista "datos"
        datos = new ArrayList<>();
        try {
            while (rs.next()) {
                // Aquí, puedes obtener los valores de las columnas que necesites del ResultSet
                String nombreProducto = rs.getString("Nombre del producto");
                double precioProducto = rs.getDouble("Precio del producto");
                String descripcionProducto = rs.getString("Descripcion del producto");
                double gastoDeProduccion = rs.getDouble("Gasto de produccion");
                int cantidad = rs.getInt("Cantidad");
                String vendedor = rs.getString("Vendedor");

                // Agrega los datos a la lista "datos"
                String item = "Nombre: " + nombreProducto + "\nPrecio: " + precioProducto + "\nDescripción: " + descripcionProducto + "\nGasto de Producción: " + gastoDeProduccion + "\nCantidad: " + cantidad + "\nVendedor: " + vendedor;
                datos.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crea un ArrayAdapter y configúralo con los datos
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);

        // Establece el adaptador en el ListView
        listView.setAdapter(adaptador);
    }
}