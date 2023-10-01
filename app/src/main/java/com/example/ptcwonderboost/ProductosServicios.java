package com.example.ptcwonderboost;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProductosServicios extends AppCompatActivity {
    private EditText txtServicio, txtPrecio, txtDescripcion;
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

    public final void LeerDatos(){
        Servicios servicio = new Servicios();
        servicio.idPersonas = VariablesGlobales.getIdPersona();
        ResultSet rsL = servicio.mostrarServicios();
        // Procesa los datos del ResultSet y agrégalos a la lista "datos"
        datos = new ArrayList<>();
        try {
            while (rsL.next()) {
                // Aquí, puedes obtener los valores de las columnas que necesites del ResultSet
                int idServicio = rsL.getInt("idServicio");
                String nombreServicio = rsL.getString("Nombre del servicio");
                double precioServicio = rsL.getDouble("Precio del servicio");

                // Agrega los datos a la lista "datos"
                String item =  idServicio +  " Servicio: " + nombreServicio + " Precio: $" + precioServicio;
                datos.add(item);
            }
        } catch (Exception e) {
            Toast.makeText(ProductosServicios.this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
        // Crea un ArrayAdapter y configúralo con los datos
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);

                // Cambia el color del texto a blanco
                TextView textView = itemView.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);

                return itemView;
            }
        };
        // Establece el adaptador en el ListView
        listView.setAdapter(adaptador);

    }

    public final void AgregarDatos(){
        Servicios servicio = new Servicios();
        if(Validaciones.Vacio(txtServicio) || Validaciones.Vacio(txtPrecio) || Validaciones.Vacio(txtDescripcion)){
            Toast.makeText(ProductosServicios.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
        }else if(!Validaciones.Precio(txtPrecio)){
            Toast.makeText(ProductosServicios.this, "Se debe respetar el formato del precio", Toast.LENGTH_SHORT).show();
        } else{
            try{
                String Servicio = txtServicio.getText().toString();
                String PrecioTexto = txtPrecio.getText().toString().trim();
                BigDecimal Precio = new BigDecimal(PrecioTexto);
                String Descripcion = txtDescripcion.getText().toString();
                servicio.setServicio(Servicio);
                servicio.setPrecio(Precio);
                try {
                    servicio.setFotoServicio(imagenBytes);
                }catch (Exception ex) {
                    servicio.setFotoServicio(null);
                }
                servicio.setDescripcion(Descripcion);
                servicio.setIdCategoria(idCategoria);
                servicio.setIdEstadoServicio(1);
                servicio.setIdTipoPrecio(idTipoPrecio);
                servicio.setIdPersonas(VariablesGlobales.getIdPersona());
                int valor = servicio.AgregarServicio();
                if(valor == 1){
                    Toast.makeText(ProductosServicios.this, "Se han ingresado los datos", Toast.LENGTH_SHORT).show();
                }else if(valor == 0){
                    Toast.makeText(ProductosServicios.this, "El valor es 0", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ProductosServicios.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
                }
            }catch (Exception ex){

            }
        }
    }

    public final void EliminarServicios(){
        Servicios servicio = new Servicios();
        try {
            servicio.setIdServicio(VariablesGlobales.getIdProducto());
            int valor = servicio.EliminarServicio();
            if(valor == 1){
                Toast.makeText(ProductosServicios.this, "Se han eliminado los datos", Toast.LENGTH_SHORT).show();
            }else if(valor == 0){
                Toast.makeText(ProductosServicios.this, "El valor es 0", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ProductosServicios.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
            }
        }catch (NullPointerException ex){
            Toast.makeText(getApplicationContext(), "Error no se ha seleccionado nada", Toast.LENGTH_LONG).show();
        }
    }

    public final void ActualizarServicios(){
        Servicios servicio = new Servicios();
        if(Validaciones.Vacio(txtServicio) || Validaciones.Vacio(txtPrecio) || Validaciones.Vacio(txtDescripcion)){
            Toast.makeText(ProductosServicios.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
        }else if(!Validaciones.Precio(txtPrecio)){
            Toast.makeText(ProductosServicios.this, "Se debe respetar el formato del precio", Toast.LENGTH_SHORT).show();
        }else{
            try{
                String Servicio = txtServicio.getText().toString();
                String PrecioTexto = txtPrecio.getText().toString().trim();
                BigDecimal Precio = new BigDecimal(PrecioTexto);
                String Descripcion = txtDescripcion.getText().toString();
                servicio.setServicio(Servicio);
                servicio.setPrecio(Precio);
                try {
                    servicio.setFotoServicio(imagenBytes);
                }catch (Exception ex) {
                    servicio.setFotoServicio(null);
                }
                servicio.setDescripcion(Descripcion);
                servicio.setIdCategoria(idCategoria);
                servicio.setIdEstadoServicio(1);
                servicio.setIdTipoPrecio(idTipoPrecio);
                servicio.setIdPersonas(VariablesGlobales.getIdPersona());
                servicio.setIdServicio(VariablesGlobales.getIdProducto());
                int valor = servicio.EditarServicioImagen();
                if(valor == 1){
                    Toast.makeText(ProductosServicios.this, "Se han ingresado los datos", Toast.LENGTH_SHORT).show();
                }else if(valor == 0){
                    Toast.makeText(ProductosServicios.this, "El valor es 0", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ProductosServicios.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
                }
            }catch (Exception ex){

            }
        }
    }

    public final void LimpiarCampos(){
        txtServicio.setText("");
        txtPrecio.setText("");
        txtDescripcion.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_servicios);
        listView = findViewById(R.id.listViewIngresarServicios);
        txtServicio = findViewById(R.id.txtServicioP);
        txtPrecio = findViewById(R.id.txtPrecioSER);
        txtDescripcion = findViewById(R.id.txtDescripcionS);
        agregarS = findViewById(R.id.btnAgregarSer);
        eleminarS = findViewById(R.id.btnEliminarSer);
        actualizarS = findViewById(R.id.btnActualizarSer);
        Productos producto = new Productos();
        LeerDatos();

        // Click en la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    // Obtén el nombre del elemento seleccionado
                    String nombreProductoSeleccionado = datos.get(position);

                    // Extrae el idProducto del texto seleccionado
                    String[] partes = nombreProductoSeleccionado.split(" ");
                    if (partes.length > 1) {
                        String idProducto = partes[0]; // El primer elemento será el idProducto
                        // Muestra un mensaje con el idProducto seleccionado
                        Toast.makeText(getApplicationContext(), "ID del producto seleccionado: " + idProducto, Toast.LENGTH_SHORT).show();
                        VariablesGlobales.idProducto = Integer.parseInt(idProducto);
                        CargarDatosTablaServicio();
                    }
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error" + ex, Toast.LENGTH_LONG).show();
                }
            }
        });

        categoria = findViewById(R.id.spinnerServicioCa);
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
        agregarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(VariablesGlobales.getPermisoVenta() == 0){
                    Toast.makeText(ProductosServicios.this, "Usted no tiene permiso para vender", Toast.LENGTH_LONG).show();
                }else {
                    AgregarDatos();
                    LeerDatos();
                    LimpiarCampos();
                }
            }
        });
        eleminarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(VariablesGlobales.getPermisoVenta() == 0){
                        Toast.makeText(ProductosServicios.this, "Usted no tiene permiso para vender", Toast.LENGTH_LONG).show();
                    }else {
                        EliminarServicios();
                        LeerDatos();
                        LimpiarCampos();
                    }
                }catch(Exception ex){
                    Toast.makeText(ProductosServicios.this, "No se ha seleccionado nada. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        actualizarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(VariablesGlobales.getPermisoVenta() == 0){
                        Toast.makeText(ProductosServicios.this, "Usted no tiene permiso para vender", Toast.LENGTH_LONG).show();
                    }else {
                        ActualizarServicios();
                        LeerDatos();
                        LimpiarCampos();
                    }
                }catch(Exception ex){
                    Toast.makeText(ProductosServicios.this, "No se ha seleccionado nada ", Toast.LENGTH_SHORT).show();
                }
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

    final void CargarDatosTablaServicio(){
        Servicios servicio = new Servicios();
        try{
            servicio.setIdServicio(VariablesGlobales.getIdProducto());
            ResultSet rs = servicio.MostrarDatosServicios();
            while(rs.next()){
                txtServicio.setText(rs.getString("Servicio"));
                BigDecimal precio = rs.getBigDecimal("Precio");
                txtPrecio.setText(precio.toString());
                txtDescripcion.setText(rs.getString("Descripcion"));
                categoria.setSelection(rs.getInt("idCategoria"));
                tipoPrecio.setSelection(rs.getInt("idTipoPrecio"));
                imagenBytes = rs.getBytes("FotoServicio");
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
                imagen.setImageBitmap(bitmap);
            }
        }catch(Exception ex){
        }
    }
}