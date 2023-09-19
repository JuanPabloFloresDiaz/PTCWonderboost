package com.example.ptcwonderboost;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PagoSeguimientos extends AppCompatActivity {
    Button btnDatePicker1;
    Button btnDatePicker2;
    private Button botonSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_seguimientos);
        btnDatePicker1 = findViewById(R.id.btnDatePicker1);
        btnDatePicker2 = findViewById(R.id.btnDatePicker2);

        // Asignar el mismo OnClickListener a ambos botones
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botonSeleccionado = (Button) v; // Guarda el botón seleccionado
                mostrarSelectorFecha();
            }
        };

        btnDatePicker1.setOnClickListener(onClickListener);
        btnDatePicker2.setOnClickListener(onClickListener);
    }

    private void mostrarSelectorFecha() {
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog selectorFecha = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Aquí puedes manejar la fecha seleccionada por el usuario
                        String fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        // Puedes mostrar la fecha en un TextView o hacer lo que desees con ella
                        if (botonSeleccionado != null) {
                            botonSeleccionado.setText(fechaSeleccionada);
                            botonSeleccionado = null; // Restablece el botón seleccionado
                        }
                    }
                }, año, mes, dia);

        // Muestra el selector de fecha
        selectorFecha.show();
    }
}