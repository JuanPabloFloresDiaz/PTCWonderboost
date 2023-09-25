package com.example.ptcwonderboost;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PagoSeguimientos extends AppCompatActivity {
    private ListView listView;
    private Spinner formaPago;

    private List<String> datos;
    private Button btnGuardar;

    public final void LeerDatos(){
        Pagos pagos = new Pagos();
        pagos.setIdVendedor(VariablesGlobales.getIdPersona());
        ResultSet rsL = pagos.cargarTablaPagos();
        // Procesa los datos del ResultSet y agrégalos a la lista "datos"
        datos = new ArrayList<>();
        try {
            while (rsL.next()) {
                // Aquí, puedes obtener los valores de las columnas que necesites del ResultSet
                int idEnvio = rsL.getInt("idEnvio");
                String nombre = rsL.getString("Nombre");
                String envio = rsL.getString("Estado de envio");
                BigDecimal precioOriginal = rsL.getBigDecimal("Precio original");
                BigDecimal precioOfrecido = rsL.getBigDecimal("Precio ofrecido");
                BigDecimal precioVenta = rsL.getBigDecimal("Precio de venta");

                // Agrega los datos a la lista "datos"
                String item =  idEnvio + "\n" +  " Nombre: " + nombre + "\n"+ " Estado de envio: " + envio + "\n"+ " Precio original: " + precioOriginal + "\n"+ " Precio ofrecido: " + precioOfrecido + "\n"+  " Precio de venta " + precioVenta;
                datos.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_seguimientos);
        listView = findViewById(R.id.listViewSeguimientoPago);
        LeerDatos();
    }

}