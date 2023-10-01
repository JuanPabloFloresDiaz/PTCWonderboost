package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class Balance extends AppCompatActivity {

    TextView ganancias, perdidas, balances;
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
            }
        }catch (Exception ex){
            Toast.makeText(this,"Error ", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ganancias = findViewById(R.id.txtGananciasB);
        perdidas = findViewById(R.id.txtPerdidasB);
        balances = findViewById(R.id.txtBalance);
        CargarBalance();
    }
}