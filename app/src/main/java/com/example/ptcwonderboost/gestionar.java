package com.example.ptcwonderboost;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class gestionar extends AppCompatActivity {
    ListView lista;

    private BottomNavigationView bottomNavigationView;
    private int idSeleccionado;
    private void abrirNegociacion() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, gestionar_envios.class);
        startActivity(intent);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar);
        bottomNavigationView = findViewById(R.id.bottom_navigationG);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.EnvioIngreso) {
                    startActivity(new Intent(gestionar.this, Envios.class));
                    return true;
                } else if (item.getItemId() == R.id.Gestionar) {
                    startActivity(new Intent(gestionar.this, gestionar.class));
                    return true;
                } else if (item.getItemId() == R.id.enviohistorial) {
                    startActivity(new Intent(gestionar.this, historial.class));
                    return true;
                }
                return false;
                }
        });
        CargarLista();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    // Obtén el id de negociación seleccionado
                    Envio NegociacionSeleccionado = (Envio) parent.getItemAtPosition(position);
                    // Guarda el valor del campo "id" en la variable idSeleccionado
                    idSeleccionado = NegociacionSeleccionado.getIdNegociacion();
                    VariablesGlobales.setIdNegociacion(idSeleccionado);
                    Toast.makeText(gestionar.this, "id: " + idSeleccionado, Toast.LENGTH_LONG).show();
                }catch (Exception ex){
                    Toast.makeText(gestionar.this, "ERROR: " + ex, Toast.LENGTH_LONG).show();
                }
                mostrarDialogoDeOpciones();
            }
        });
    }
    private void mostrarDialogoDeOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción")
                .setItems(new CharSequence[]{"Gestionar envio"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí puedes manejar la selección del usuario
                        switch (which) {
                            case 0:
                                abrirNegociacion();
                                break;
                        }
                    }
                })
                .create()
                .show();
    }
    public final void CargarLista(){
        try {
            lista = findViewById(R.id.listviewGestion);
            Envio negociacion = new Envio();
            List<Envio> negocio = negociacion.cargarTablaGestionarEnvios(VariablesGlobales.getIdPersona());

            if (negocio != null) {
                EnvioAdapter adapter = new EnvioAdapter(this, negocio);
                lista.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se ha podido cargar la tabla", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}