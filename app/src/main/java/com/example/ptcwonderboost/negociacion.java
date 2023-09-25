package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class negociacion extends AppCompatActivity {
    private TextView comprador, vendedor, producto, precioOriginal;
    private EditText cantidad, precioOfrecido;
    private Spinner formaPago;
    private int idFormaPago;
    private Button Negociar;

    final void ConfirmarNegociacion(){
        try {
            Negociaciones n = new Negociaciones();
            if(Validaciones.Vacio(cantidad) || Validaciones.Vacio(precioOfrecido)){
                Toast.makeText(this,"Ay campos vacios", Toast.LENGTH_SHORT).show();
            }else if(idFormaPago == 0){
                Toast.makeText(this,"No se ha seleccionado forma de pago", Toast.LENGTH_SHORT).show();
            }else if(!Validaciones.Precio(precioOfrecido)){
                Toast.makeText(this,"Se debe respetar el formato del precio", Toast.LENGTH_SHORT).show();
            }else if(!Validaciones.Numeros(cantidad)){
                Toast.makeText(this,"Solo debes poner numeros para cantidad", Toast.LENGTH_SHORT).show();
            }else{
                String preciotext = precioOfrecido.getText().toString();
                String Cantidad = cantidad.getText().toString();
                n.setIdProducto(VariablesGlobales.getIdProducto());
                n.setIdEstado(1);
                n.setIdPersona(VariablesGlobales.getIdPersona());
                n.setCantidad(Integer.parseInt(Cantidad));
                double precio = Double.parseDouble(preciotext);
                n.setPrecioOfrecido(precio);
                n.setPrecioVenta(0.00);
                n.setIdFormaPago(idFormaPago);
                int valor = n.ConfirmarNegociacionProducto();
                if (valor == 1){
                    Toast.makeText(this,"Negociación realizada", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception ex){
            Toast.makeText(this,"Error: " + ex, Toast.LENGTH_SHORT).show();
        }
    }
        public final void CargarComprador(){
        try {
            comprador = findViewById(R.id.txtComprador);
            Perfil perfil = new Perfil();
            ResultSet rs = perfil.mostrardatosdePerfil(VariablesGlobales.getIdUsuario());
            if (rs != null && rs.next()) {
                String nombrePerfil = rs.getString("Nombre perfil");
                comprador.setText(nombrePerfil);
            } else {
                Toast.makeText(this, "No se encontraron datos de perfil para este usuario", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(negociacion.this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    final void CargarDatosProducto(){
        Productos productos = new Productos();
        try{
            productos.setIdProducto(VariablesGlobales.getIdProducto());
            ResultSet rs = productos.mostrarNegociacionProducto();
            while(rs.next()){
                producto.setText(rs.getString("Producto"));
                BigDecimal precio = rs.getBigDecimal("Precio original");
                precioOriginal.setText(precio.toString());
                vendedor.setText(rs.getString("Vendedor"));
            }
        }catch(Exception ex){
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negociacion);
        producto = findViewById(R.id.txtProductosPro);
        precioOriginal = findViewById(R.id.txtPrecioOriginal);
        vendedor = findViewById(R.id.txtVendedor);
        precioOfrecido = findViewById(R.id.txtPrecioOfrecido);
        cantidad = findViewById(R.id.txtCantidadPro);
        Negociar = findViewById(R.id.btnGuardarNegociacion);
        Productos productos = new Productos();
        formaPago = findViewById(R.id.SpinnerForma);
        ResultSet rs = productos.CargarFormaPago();
        if (rs != null) {
            List<String> datos1 = new ArrayList<>();
            datos1.add("Seleccionar");
            try {
                while (rs.next()) {
                    // Obtén el valor de la columna "Nacionalidad" y agrégalo a la lista
                    String nacionalidad = rs.getString("FormadePago");
                    datos1.add(nacionalidad);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos1);
                adapterN1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                formaPago.setAdapter(adapterN1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        formaPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    idFormaPago = (int) id;
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });
        CargarDatosProducto();
        CargarComprador();

        Negociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmarNegociacion();
            }
        });
    }
}