package com.example.ptcwonderboost;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosServicios extends AppCompatActivity {

    private int idCategoria, idTipoPrecio;
    private Spinner categoria, tipoPrecio;
    ImageView imagen;
    Uri imageUri;
    private byte[] imagenBytes;
    private Button agregarS;
    private Button eleminarS;
    private Button actualizarS;
    private ListView listView;
    private List<String> datos;
    private static final int REQUEST_CODE_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_servicios);
        Productos producto = new Productos();
        categoria = findViewById(R.id.SpinnerGeneroEP);
        ResultSet resultSet = producto.CargarCategoria();
        if (resultSet != null) {
            List<String> datos = new ArrayList<>();
            datos.add("Seleccionar");
            try {
                while (resultSet.next()) {
                    // Obtén el valor de la columna "Nacionalidad" y agrégalo a la lista
                    String categoria1 = resultSet.getString("Categoria");
                    datos.add(categoria1);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos);
                adapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoria.setAdapter(adapterN);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    idCategoria = (int) id;
                    Toast.makeText(ProductosServicios.this, "Seleccionaste: " + selectedItem + " " +id, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProductosServicios.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });

        tipoPrecio = findViewById(R.id.spinnerTipoPrecioS);
        ResultSet rs = producto.CargarTipoPrecio();
        if (rs != null) {
            List<String> datos1 = new ArrayList<>();
            datos1.add("Seleccionar");
            try {
                while (rs.next()) {
                    // Obtén el valor de la columna "Nacionalidad" y agrégalo a la lista
                    String nacionalidad = rs.getString("TipoPrecio");
                    datos1.add(nacionalidad);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos1);
                adapterN1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tipoPrecio.setAdapter(adapterN1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        tipoPrecio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    idTipoPrecio = (int) id;
                    Toast.makeText(ProductosServicios.this, "Seleccionaste: " + selectedItem + " " +id, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProductosServicios.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });

        imagen = (ImageView)findViewById(R.id.imgSer);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });


    }


    private void cargarImagen() {
        try {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.setType("image/*");
            startActivityForResult(gallery, REQUEST_CODE_IMAGE);
        } catch (Exception ex) {
            Toast.makeText(ProductosServicios.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMAGE){
                imageUri = data.getData();
                imagen.setImageURI(imageUri);
                // Convierte la imagen en un array de bytes
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }
                    imagenBytes = byteArrayOutputStream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(ProductosServicios.this,"Ruta nula ",Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(ProductosServicios.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}