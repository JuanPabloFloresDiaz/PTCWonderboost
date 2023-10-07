package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class Balance extends AppCompatActivity {

    TextView ganancias, perdidas, balances;
    public int gananciaInt;
    public int perdidaInt;
    final void CargarBalance(){
        Grafica grafica = new Grafica();
        try {
            grafica.setIdPersona(VariablesGlobales.getIdPersona());
            ResultSet rs = grafica.BalanceEconomico();
            while (rs.next()){
                BigDecimal ganancia = rs.getBigDecimal("Ganancias");
                ganancias.setText("$" +ganancia.toString());
                BigDecimal perdida = rs.getBigDecimal("Perdidas");
                perdidas.setText("$-" +perdida.toString());
                BigDecimal balance = rs.getBigDecimal("Balance");
                balances.setText("$" +balance.toString());


                gananciaInt = ganancia.intValue();
                perdidaInt = perdida.intValue();
            }
        }catch (Exception ex){
            Toast.makeText(this,"Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private void Grafica(){
        try{
            PieChart pieChart = findViewById(R.id.chart2);

            ArrayList<PieEntry> entries = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
                entries.add(new PieEntry(gananciaInt, "Ganancias"));
                entries.add(new PieEntry(perdidaInt, "Perdidas"));
                colors.add( Color.rgb(0, 180, 0));
                colors.add(Color.rgb(200, 0, 0));
                PieDataSet dataSet = new PieDataSet(entries, "Balance economico");
            dataSet.setColors(colors); // Asigna la lista de colores aleatorios
            dataSet.setValueTextSize(10f); // Tamaño del texto
            PieData pieData = new PieData(dataSet);
            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false); // Deshabilitar descripción
            pieChart.setCenterText("Mi balance"); // Texto central
            pieChart.setCenterTextColor(android.graphics.Color.WHITE);
            pieChart.setHoleColor(android.graphics.Color.BLACK);
            pieChart.animateY(1000); // Animación de entrada
        }catch (Exception ex){
            Toast.makeText(this,"Error: "+ex , Toast.LENGTH_SHORT).show();        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ganancias = findViewById(R.id.txtGananciasB);
        perdidas = findViewById(R.id.txtPerdidasB);
        balances = findViewById(R.id.txtBalance);
        CargarBalance();
        Grafica();

    }
}