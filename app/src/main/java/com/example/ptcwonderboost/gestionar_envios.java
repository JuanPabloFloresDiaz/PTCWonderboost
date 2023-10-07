package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class gestionar_envios extends AppCompatActivity {
    private EditText fechaEditText, UbicacionOrigen, UbicacionDestino;
    private int idestadoEnvio;
    private Button btnGuardar;
    private Spinner estadoEnvio;
    private Calendar calendar = Calendar.getInstance();
    public final void ActualizarEnvio(){
        try{
            Envio envio = new Envio();
            if(Validaciones.Vacio(UbicacionOrigen) || Validaciones.Vacio(UbicacionDestino) || Validaciones.Vacio(fechaEditText)){
                Toast.makeText(this,"Existen campos vacios", Toast.LENGTH_SHORT).show();
            }else{
                String Origen = UbicacionOrigen.getText().toString();
                String Destino = UbicacionDestino.getText().toString();
                envio.setEstado(idestadoEnvio);
                envio.setUbicacionOrigen(Origen);
                envio.setUbicacionDestino(Destino);
                envio.setFecha(fechaEditText.getText().toString());
                envio.setIdEnvio(VariablesGlobales.getIdNegociacion());
                int valor = envio.ActualizarEnvio();
                if(valor == 1){
                    Toast.makeText(this,"Se actualizo el envio", Toast.LENGTH_SHORT ).show();
                    abrirNegociacion();
                }
            }
        }catch(Exception ex){
            Toast.makeText(this,"Error: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    public final void CargarDatosGestionarEnvios(){
        Envio envio = new Envio();
        try {
            envio.setIdEnvio(VariablesGlobales.getIdProducto());
            ResultSet rs = envio.MostrarDatosGestionEnvio();
            while (rs.next()){
                UbicacionOrigen.setText(rs.getString("Origen"));
                UbicacionDestino.setText(rs.getString("Destino"));
                fechaEditText.setText(rs.getString("Fecha"));
                estadoEnvio.setSelection(rs.getInt("idEstado"));
            }
        }catch (Exception ex){

        }
    }

    private void abrirNegociacion() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, Envios.class);
        startActivity(intent);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_envios);
        UbicacionOrigen = findViewById(R.id.txtUbicacionOrigenG);
        UbicacionDestino = findViewById(R.id.txtUbicacionDestinoG);
        Envio envio = new Envio();
        estadoEnvio = findViewById(R.id.spinnerEstadoEnvioG);
        ResultSet rs = envio.CargarEstadoEnvio();
        if (rs != null) {
            List<String> datos1 = new ArrayList<>();
            datos1.add("Seleccionar");
            try {
                while (rs.next()) {
                    String nacionalidad = rs.getString("estadoEnvio");
                    datos1.add(nacionalidad);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos1);
                adapterN1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                estadoEnvio.setAdapter(adapterN1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        estadoEnvio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    idestadoEnvio = (int) id;
                    Toast.makeText(gestionar_envios.this,"Estado numero" + idestadoEnvio, Toast.LENGTH_SHORT).show();
                }else{

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });
        fechaEditText = findViewById(R.id.txtFechaEnvioG);
        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        CargarDatosGestionarEnvios();
        btnGuardar = findViewById(R.id.btnGuardarCambiosG);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ActualizarEnvio();
                }catch (Exception ex){
                    Toast.makeText(gestionar_envios.this,"Error: " + ex, Toast.LENGTH_SHORT).show();
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

            if (selectedDate.before(currentDate)) {
                Toast.makeText(gestionar_envios.this, "Seleccione una fecha mayor a la fecha actual", Toast.LENGTH_SHORT).show();
            } else if (selectedDate.before(minDate)) {
                Toast.makeText(gestionar_envios.this, "Seleccione una fecha mayor a 1900", Toast.LENGTH_SHORT).show();
            } else {
                String selectedDateStr = year + "-" + (month + 1) + "-" + dayOfMonth;
                fechaEditText.setText(selectedDateStr);
            }
        }
    };

}