package com.example.ptcwonderboost;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class gestionar extends AppCompatActivity {
    ListView lista;
    private int idSeleccionado;
    private void abrirNegociacion() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, gestionar_envios.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar);
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