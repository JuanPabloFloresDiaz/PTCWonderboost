package com.example.ptcwonderboost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class agregar_servicios extends AppCompatActivity {

    private Button btnSeleimg;
    private Button agregar;
    private Button eleminar;
    private Button actualizar;

    private ImageView imgProd;

    protected static final int REQUEST_CODE_IMAGE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_servicios);

        btnSeleimg = findViewById(R.id.btnSeleImg);
        imgProd = findViewById(R.id.imgProd);
        agregar = findViewById(R.id.btnAgregarSer);
        eleminar = findViewById(R.id.btnEliminarSer);
        actualizar = findViewById(R.id.btnActualizarSer);
        Servicio servicio = new Servicio();
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicio.ingresarServicios();
            }
        });
        eleminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicio.eliminarServicio();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicio.actualizarDatos();
            }
        });




        btnSeleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        //Spinner Categorias
        Servicio SERVICIO =  new Servicio();
        Spinner categorias1 = findViewById(R.id.spinnerServicioCa);
        ResultSet resultSet = SERVICIO.ingresarServicios();
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
                categorias1.setAdapter(adapterN);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        categorias1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    Toast.makeText(agregar_servicios.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(agregar_servicios.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });
        //valor que se cargara en un inicio
        categorias1.post(new Runnable() {
            @Override
            public void run() {
                categorias1.setSelection(0);
            }
        });



        //------------------------------------------------------------

        //------------------------------------------------------------



        //Spinner TipoPrecio
        Producto producto2 =  new Producto();
        Spinner TipoPrecios = findViewById(R.id.spinnerCategoriaP);
        ResultSet resultSet2 = producto2.ingresarProductos();
        if (resultSet2 != null) {
            List<String> datos = new ArrayList<>();
            datos.add("Seleccionar");
            try {
                while (resultSet.next()) {
                    // Obtén el valor de la columna "Nacionalidad" y agrégalo a la lista
                    String TipoPrecio = resultSet2.getString("TipoPrecio");
                    datos.add(TipoPrecio);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos);
                adapterN1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                TipoPrecios.setAdapter(adapterN1);
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
                    Toast.makeText(agregar_servicios.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(agregar_servicios.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
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