package com.example.ptcwonderboost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Ganancias extends AppCompatActivity {

    TextView ganancias;
    ListView listView;
    private List<String> datos;
    final void CargarGanancias(){
        Grafica grafica = new Grafica();
        try {
            grafica.setIdPersona(VariablesGlobales.getIdPersona());
            ResultSet rs = grafica.TotalGanancias();
            while (rs.next()){
                BigDecimal ganancia = rs.getBigDecimal("Ganancias");
                ganancias.setText("$" +ganancia.toString());
            }
        }catch (Exception ex){
            Toast.makeText(this,"Error ", Toast.LENGTH_SHORT).show();
        }
    }

    public final void CargarLista(){
        Grafica grafica = new Grafica();
        grafica.setIdPersona(VariablesGlobales.getIdPersona());
        ResultSet rs = grafica.ListaGanancias();
        datos = new ArrayList<>();
        try {
            while (rs.next()) {
                // Aquí, puedes obtener los valores de las columnas que necesites del ResultSet
                int idProducto = rs.getInt("idProducto");
                String nombreProducto = rs.getString("Producto");
                BigDecimal precioProducto = rs.getBigDecimal("Ganancias");
                // Agrega los datos a la lista "datos"
                String item =  idProducto +  " Producto: " + nombreProducto + " Precio: $" + precioProducto;
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
        setContentView(R.layout.activity_ganancias);
        listView = findViewById(R.id.listViewGanancias);
        ganancias = findViewById(R.id.txtGanancias);
        CargarLista();
        CargarGanancias();
    }
}