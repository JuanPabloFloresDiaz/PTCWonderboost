package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Solicitar_Permiso extends AppCompatActivity {
    private EditText Enfoque, Empresa, Descripcion, NRC;
    private Button solicitar;

    final void SolicitarPermisoVenta(){
        Permisos permiso = new Permisos();
        if(Validaciones.Vacio(Empresa) || Validaciones.Vacio(Enfoque) || Validaciones.Vacio(NRC) ||  Validaciones.Vacio(Descripcion)){
            Toast.makeText(Solicitar_Permiso.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
        }else{
            try {
                String empresa = Empresa.getText().toString();
                String enfoque = Enfoque.getText().toString().trim();
                String nrc = NRC.getText().toString();
                String descripcion = Descripcion.getText().toString().trim();
                permiso.setIdPersona(VariablesGlobales.getIdPersona());
                permiso.setEmpresa(empresa);
                permiso.setEnfoque(enfoque);
                permiso.setNRC(nrc);
                permiso.setDescripcion(descripcion);
                int valor = permiso.SolicitarPermisoVenta();
                if(valor == 1){
                    Toast.makeText(Solicitar_Permiso.this, "Se han ingresado los datos", Toast.LENGTH_SHORT).show();
                }else if(valor == 0){
                    Toast.makeText(Solicitar_Permiso.this, "El valor es 0", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Solicitar_Permiso.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception ex){
                Toast.makeText(Solicitar_Permiso.this, "Ha ocurrido un error: " + ex, Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_permiso);
        Empresa = findViewById(R.id.txtEmpresa);
        Enfoque = findViewById(R.id.txtEnfoque);
        NRC = findViewById(R.id.txtNRC);
        Descripcion = findViewById(R.id.txtDescripcionSP);
        solicitar = findViewById(R.id.btnSolicitar);
        solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(VariablesGlobales.getPermisoVenta() == 1){
                    Toast.makeText(Solicitar_Permiso.this, "Usted ya tiene permiso para vender", Toast.LENGTH_SHORT).show();
                }else{
                    SolicitarPermisoVenta();
                }
            }
        });
    }
}