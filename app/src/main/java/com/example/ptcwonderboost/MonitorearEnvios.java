package com.example.ptcwonderboost;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MonitorearEnvios extends AppCompatActivity {

    private EditText fechaEditText, UbicacionOrigen, UbicacionDestino;
    private Button btnGuardar;
    private Calendar calendar = Calendar.getInstance();
    public final void IngresarEnvio(){
        try{
            Envio envio = new Envio();
            if(Validaciones.Vacio(UbicacionOrigen) || Validaciones.Vacio(UbicacionDestino) || Validaciones.Vacio(fechaEditText)){
                Toast.makeText(this,"Existen campos vacios", Toast.LENGTH_SHORT).show();
            }else{
            String Origen = UbicacionOrigen.getText().toString();
            String Destino = UbicacionDestino.getText().toString();
            envio.setIdNegociacion(VariablesGlobales.getIdNegociacion());
            envio.setEstado(1);
            envio.setUbicacionOrigen(Origen);
            envio.setUbicacionDestino(Destino);
            envio.setFecha(fechaEditText.getText().toString());
            int valor = envio.AgregarEnvio();
            if(valor == 1){
                Toast.makeText(this,"Se agrego el envio", Toast.LENGTH_SHORT ).show();
                abrirNegociacion();
            }
            }
        }catch(Exception ex){
            Toast.makeText(this,"Error: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirNegociacion() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, Envios.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitorear_envios);
        UbicacionOrigen = findViewById(R.id.txtUbicacionOrigen);
        UbicacionDestino = findViewById(R.id.txtUbicacionDestino);
        fechaEditText = findViewById(R.id.txtFechaEnvio);
        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnGuardar = findViewById(R.id.btnGuardarCambiosM);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    IngresarEnvio();
                }catch (Exception ex){
                    Toast.makeText(MonitorearEnvios.this,"Error: " + ex, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MonitorearEnvios.this, "Seleccione una fecha mayor a la fecha actual", Toast.LENGTH_SHORT).show();
            } else if (selectedDate.before(minDate)) {
                Toast.makeText(MonitorearEnvios.this, "Seleccione una fecha mayor a 1900", Toast.LENGTH_SHORT).show();
            } else {
                String selectedDateStr = year + "-" + (month + 1) + "-" + dayOfMonth;
                fechaEditText.setText(selectedDateStr);
            }
        }
    };
}