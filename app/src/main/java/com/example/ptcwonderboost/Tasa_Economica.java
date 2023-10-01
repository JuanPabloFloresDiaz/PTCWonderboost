package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class Tasa_Economica extends AppCompatActivity {
    TextView ganancias, interes;

    final void CargarTasa(){
        Grafica grafica = new Grafica();
        DecimalFormat df = new DecimalFormat("#.##");
        try {
            grafica.setIdPersona(VariablesGlobales.getIdPersona());
            ResultSet rs = grafica.TasaEconomica();
            while (rs.next()){
                BigDecimal ganancia = rs.getBigDecimal("Ganancias");
                ganancias.setText("$" +ganancia.toString());
                BigDecimal perdida = rs.getBigDecimal("Tasa");
                interes.setText("$-" + df.format(perdida));
            }
        }catch (Exception ex){
            Toast.makeText(this,"Error " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasa_economica);
        ganancias = findViewById(R.id.txtGananciasT);
        interes = findViewById(R.id.txtTasa);
        CargarTasa();
    }
}