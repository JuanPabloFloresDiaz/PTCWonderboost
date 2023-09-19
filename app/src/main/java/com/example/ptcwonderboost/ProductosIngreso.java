package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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

public class ProductosIngreso extends AppCompatActivity {

    private EditText txtProducto, txtPrecio, txtDescripcion, txtGasto, txtCantidad;
    private int idCategoria, idTipoPrecio;
    private Spinner categoria, tipoPrecio;
    ImageView imagen;
    Uri imageUri;
    private Button agregarP;
    private Button eleminarP;
    private Button actualizarP;
    private byte[] imagenBytes;
    private ListView listView;
    private List<String> datos;
    private static final int REQUEST_CODE_IMAGE = 100;

    public final void LeerDatos(){
        Productos producto = new Productos();
        producto.idPersonas = VariablesGlobales.getIdPersona();
        ResultSet rsL = producto.mostrarProductos();
        // Procesa los datos del ResultSet y agrégalos a la lista "datos"
        datos = new ArrayList<>();
        try {
            while (rsL.next()) {
                // Aquí, puedes obtener los valores de las columnas que necesites del ResultSet
                int idProducto = rsL.getInt("idProducto");
                String nombreProducto = rsL.getString("Nombre del producto");
                double precioProducto = rsL.getDouble("Precio del producto");
                int cantidad = rsL.getInt("Cantidad");

                // Agrega los datos a la lista "datos"
                String item =  idProducto +  " Producto: " + nombreProducto + " Precio: " + precioProducto + " Cantidad: " + cantidad;
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

    public final void AgregarDatos(){
        Productos producto = new Productos();
        if(Validaciones.Vacio(txtProducto) || Validaciones.Vacio(txtPrecio) || Validaciones.Vacio(txtDescripcion) || Validaciones.Vacio(txtGasto) || Validaciones.Vacio(txtCantidad)){
            Toast.makeText(ProductosIngreso.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
        }else if(!Validaciones.Precio(txtPrecio) || !Validaciones.Precio(txtGasto)){
            Toast.makeText(ProductosIngreso.this, "Se debe respetar el formato del precio", Toast.LENGTH_SHORT).show();
        } else if(!Validaciones.Numeros(txtCantidad)){
            Toast.makeText(ProductosIngreso.this, "Solo debes poner numeros para cantidad", Toast.LENGTH_SHORT).show();
        } else{
            try{
                String Producto = txtProducto.getText().toString();
                String PrecioTexto = txtPrecio.getText().toString().trim();
                BigDecimal Precio = new BigDecimal(PrecioTexto);
                String Descripcion = txtDescripcion.getText().toString();
                String GastoTexto = txtGasto.getText().toString().trim();
                BigDecimal Gasto = new BigDecimal(GastoTexto);
                String Cantidad = txtCantidad.getText().toString();
                producto.setProducto(Producto);
                producto.setPrecio(Precio);
                try {
                    producto.setFotoProducto(imagenBytes);
                }catch (Exception ex) {
                    producto.setFotoProducto(null);
                }
                producto.setDescripcion(Descripcion);
                producto.setCantidad(Integer.parseInt(Cantidad));
                producto.setGastoDeProduccion(Gasto);
                producto.setIdCategoria(idCategoria);
                producto.setIdEstadoProducto(1);
                producto.setIdTipoPrecio(idTipoPrecio);
                producto.setIdPersonas(VariablesGlobales.getIdPersona());
                int valor = producto.AgregarProducto();
                if(valor == 1){
                    Toast.makeText(ProductosIngreso.this, "Se han ingresado los datos", Toast.LENGTH_SHORT).show();
                }else if(valor == 0){
                    Toast.makeText(ProductosIngreso.this, "El valor es 0", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ProductosIngreso.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
                }
            }catch (Exception ex){

            }
        }
    }

    public final void EliminarProductos(){
        Productos producto = new Productos();
        try {
            producto.setIdProducto(VariablesGlobales.getIdProducto());
            int valor = producto.EliminarProducto();
            if(valor == 1){
                Toast.makeText(ProductosIngreso.this, "Se han eliminado los datos", Toast.LENGTH_SHORT).show();
            }else if(valor == 0){
                Toast.makeText(ProductosIngreso.this, "El valor es 0", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ProductosIngreso.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
            }
        }catch (NullPointerException ex){
            Toast.makeText(getApplicationContext(), "Error no se ha seleccionado nada", Toast.LENGTH_LONG).show();
        }
    }

    public final void ActualizarProductos(){
        Productos producto = new Productos();
        if(Validaciones.Vacio(txtProducto) || Validaciones.Vacio(txtPrecio) || Validaciones.Vacio(txtDescripcion) || Validaciones.Vacio(txtGasto) || Validaciones.Vacio(txtCantidad)){
            Toast.makeText(ProductosIngreso.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
        }else if(!Validaciones.Precio(txtPrecio) || !Validaciones.Precio(txtGasto)){
            Toast.makeText(ProductosIngreso.this, "Se debe respetar el formato del precio", Toast.LENGTH_SHORT).show();
        } else if(!Validaciones.Numeros(txtCantidad)){
            Toast.makeText(ProductosIngreso.this, "Solo debes poner numeros para cantidad", Toast.LENGTH_SHORT).show();
        } else{
            try{
                String Producto = txtProducto.getText().toString();
                String PrecioTexto = txtPrecio.getText().toString().trim();
                BigDecimal Precio = new BigDecimal(PrecioTexto);
                String Descripcion = txtDescripcion.getText().toString();
                String GastoTexto = txtGasto.getText().toString().trim();
                BigDecimal Gasto = new BigDecimal(GastoTexto);
                String Cantidad = txtCantidad.getText().toString();
                producto.setProducto(Producto);
                producto.setPrecio(Precio);
                try {
                    producto.setFotoProducto(imagenBytes);
                }catch (Exception ex) {
                    producto.setFotoProducto(null);
                }
                producto.setDescripcion(Descripcion);
                producto.setCantidad(Integer.parseInt(Cantidad));
                producto.setGastoDeProduccion(Gasto);
                producto.setIdCategoria(idCategoria);
                producto.setIdEstadoProducto(1);
                producto.setIdTipoPrecio(idTipoPrecio);
                producto.setIdPersonas(VariablesGlobales.getIdPersona());
                producto.setIdProducto(VariablesGlobales.getIdProducto());
                int valor = producto.EditarProductoImagen();
                if(valor == 1){
                    Toast.makeText(ProductosIngreso.this, "Se han ingresado los datos", Toast.LENGTH_SHORT).show();
                }else if(valor == 0){
                    Toast.makeText(ProductosIngreso.this, "El valor es 0", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ProductosIngreso.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
                }
            }catch (Exception ex){

            }
        }
    }

    public final void LimpiarCampos(){
        txtProducto.setText("");
        txtPrecio.setText("");
        txtDescripcion.setText("");
        txtGasto.setText("");
        txtCantidad.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_ingreso);
        Productos producto = new Productos();
        listView = findViewById(R.id.listViewIngresarProductos);
        txtProducto = findViewById(R.id.txtNombreP);
        txtPrecio = findViewById(R.id.txtPrecioP);
        txtDescripcion = findViewById(R.id.txtDescripcionP);
        txtGasto = findViewById(R.id.txtGastoProdP);
        txtCantidad = findViewById(R.id.txtCantidadP);
        agregarP = findViewById(R.id.btnAgregarP);
        eleminarP = findViewById(R.id.btnEliminarP);
        actualizarP = findViewById(R.id.btnActualizarP);

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
                    CargarDatosTablaProducto();
                }
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error" + ex, Toast.LENGTH_LONG).show();
                }
            }
        });
        categoria = findViewById(R.id.spinnerCategoriaP);
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

                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });

        tipoPrecio = findViewById(R.id.spinnerTipoPrecioP);
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
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });

        imagen = (ImageView)findViewById(R.id.imgProd);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        agregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarDatos();
                LeerDatos();
                LimpiarCampos();
            }
        });
        eleminarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    EliminarProductos();
                    LeerDatos();
                    LimpiarCampos();
                }catch(Exception ex){
                    Toast.makeText(ProductosIngreso.this, "No se ha seleccionado nada. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        actualizarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ActualizarProductos();
                    LeerDatos();
                    LimpiarCampos();
                }catch(Exception ex){
                    Toast.makeText(ProductosIngreso.this, "No se ha seleccionado nada ", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ProductosIngreso.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    final void CargarDatosTablaProducto(){
        Productos producto = new Productos();
        try{
            producto.setIdProducto(VariablesGlobales.getIdProducto());
            ResultSet rs = producto.MostrarDatosProductos();
            while(rs.next()){
                txtProducto.setText(rs.getString("Producto"));
                BigDecimal precio = rs.getBigDecimal("Precio");
                txtPrecio.setText(precio.toString());
                int cantidad = rs.getInt("Cantidad");
                String catidadTexto = String.valueOf(cantidad);
                txtCantidad.setText(catidadTexto);
                txtDescripcion.setText(rs.getString("Descripcion"));
                BigDecimal Gasto = rs.getBigDecimal("GastodeProduccion");
                txtGasto.setText(Gasto.toString());
                categoria.setSelection(rs.getInt("idCategoria"));
                tipoPrecio.setSelection(rs.getInt("idTipoPrecio"));
                imagenBytes = rs.getBytes("FotoProducto");
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
                imagen.setImageBitmap(bitmap);
            }
        }catch(Exception ex){
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
                Toast.makeText(ProductosIngreso.this,"Ruta nula ",Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(ProductosIngreso.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}