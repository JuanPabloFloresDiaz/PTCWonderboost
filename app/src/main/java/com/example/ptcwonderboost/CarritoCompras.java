package com.example.ptcwonderboost;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarritoCompras extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterCarrito adapter;
    private List<Carrito> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);
        recyclerView = findViewById(R.id.recyclerViewCarrito);
        dataList = new ArrayList<>();
        Carrito carro = new Carrito();
        carro.setIdPersona(VariablesGlobales.getIdPersona());
        // Llama a tu método para obtener los datos de tu base de datos
        ResultSet resultSet = carro.mostrarCartasCarrito();

        // Verifica si resultSet no es nulo
        if (resultSet != null) {
            try {
                // Itera a través de los resultados y agrega los datos a dataList
                while (resultSet.next()) {
                    // Crea un objeto Carrito y agrega los datos a la lista
                    Carrito carrito = new Carrito();
                    carrito.setFoto(resultSet.getBytes("Foto"));
                    carrito.setNombre(resultSet.getString("Nombre"));
                    carrito.setPrecio(resultSet.getBigDecimal("Precio"));
                    carrito.setCategoria(resultSet.getString("Categoria"));
                    carrito.setEstado(resultSet.getString("Estado"));

                    dataList.add(carrito);
                }

                // Inicializa el adaptador y configura el RecyclerView
                adapter = new AdapterCarrito(this, dataList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } catch (SQLException e) {
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}