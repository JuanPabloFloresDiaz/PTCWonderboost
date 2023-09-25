package com.example.ptcwonderboost;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PagoSeguimientos extends AppCompatActivity {
    private ListView listView;
    private Spinner formaPago;
    private EditText fechaEditText;
    private int idFormaPago;
    private List<String> datos;
    private Button btnGuardar;
    private Calendar calendar = Calendar.getInstance();

    public final void GuardarPago(){

    }
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_seguimientos);
        listView = findViewById(R.id.listViewSeguimientoPago);
        LeerDatos();
        Pagos pagos = new Pagos();
        formaPago = findViewById(R.id.spinnerEstadoPago);
        ResultSet rs = pagos.CargarEstadoPago();
        if (rs != null) {
            List<String> datos1 = new ArrayList<>();
            datos1.add("Seleccionar");
            try {
                while (rs.next()) {
                    String nacionalidad = rs.getString("EstadoPago");
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
        fechaEditText = findViewById(R.id.txtFechaPago);
        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnGuardar = findViewById(R.id.btnGuardarPago);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    GuardarPago();
                }catch (Exception ex){

                }
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar currentDate = Calendar.getInstance();
            Calendar minDate = Calendar.getInstance();
            minDate.set(1900, Calendar.JANUARY, 1);
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);

            if (selectedDate.after(currentDate)) {
                Toast.makeText(PagoSeguimientos.this, "Seleccione una fecha menor o igual a la fecha actual", Toast.LENGTH_SHORT).show();
            } else if (selectedDate.before(minDate)) {
                Toast.makeText(PagoSeguimientos.this, "Seleccione una fecha mayor a 1900", Toast.LENGTH_SHORT).show();
            } else {
                String selectedDateStr = dayOfMonth + "/" + (month + 1) + "/" + year;
                fechaEditText.setText(selectedDateStr);
            }
        }
    };

}