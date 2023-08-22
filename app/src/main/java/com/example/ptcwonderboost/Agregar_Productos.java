package com.example.ptcwonderboost;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Agregar_Productos extends AppCompatActivity {

    private Button btnSeleimg;

    private ImageView imgProd;

    protected static final int REQUEST_CODE_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productos);

        btnSeleimg = findViewById(R.id.btnSeleImg);
       imgProd = findViewById(R.id.imgProd);


        btnSeleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });


        //Spinner Categorias
        Producto producto =  new Producto();
                Spinner categorias = findViewById(R.id.spinnerCategoriaP);
        ResultSet resultSet = producto.ingresarProductos();
        if (resultSet != null) {
            List<String> datos = new ArrayList<>();
            datos.add("Seleccionar");
            try {
                while (resultSet.next()) {
                    // Obtén el valor de la columna "Nacionalidad" y agrégalo a la lista
                    String categoria = resultSet.getString("Categoria");
                    datos.add(categoria);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos);
                adapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorias.setAdapter(adapterN);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    Toast.makeText(Agregar_Productos.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Agregar_Productos.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });
        //valor que se cargara en un inicio
        categorias.post(new Runnable() {
            @Override
            public void run() {
                categorias.setSelection(0);
            }
        });


        //Spinner TipoPrecio
        Producto producto1 =  new Producto();
        Spinner TipoPrecios = findViewById(R.id.spinnerCategoriaP);
        ResultSet resultSet1 = producto1.ingresarProductos();
        if (resultSet1 != null) {
            List<String> datos = new ArrayList<>();
            datos.add("Seleccionar");
            try {
                while (resultSet.next()) {
                    // Obtén el valor de la columna "Nacionalidad" y agrégalo a la lista
                    String TipoPrecio = resultSet1.getString("TipoPrecio");
                    datos.add(TipoPrecio);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos);
                adapterN1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorias.setAdapter(adapterN1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        TipoPrecios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    Toast.makeText(Agregar_Productos.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Agregar_Productos.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });
        //valor que se cargara en un inicio
        TipoPrecios.post(new Runnable() {
            @Override
            public void run() {
                TipoPrecios.setSelection(0);
            }
        });


        //Spinner TipoPrecio
        Producto producto2 =  new Producto();
        Spinner EstadoProd = findViewById(R.id.spinnerCategoriaP);
        ResultSet resultSet2 = producto1.ingresarProductos();
        if (resultSet2 != null) {
            List<String> datos = new ArrayList<>();
            datos.add("Seleccionar");
            try {
                while (resultSet.next()) {
                    // Obtén el valor de la columna "Nacionalidad" y agrégalo a la lista
                    String EstadoP = resultSet2.getString("EstadoProducto");
                    datos.add(EstadoP);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos);
                adapterN2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorias.setAdapter(adapterN2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        EstadoProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    Toast.makeText(Agregar_Productos.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Agregar_Productos.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });
        //valor que se cargara en un inicio
        EstadoProd.post(new Runnable() {
            @Override
            public void run() {
                EstadoProd.setSelection(0);
            }
        });

    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imgProd.setImageURI(imageUri);
        }
    }




    }


