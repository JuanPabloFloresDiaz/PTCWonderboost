package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class editarPerfil extends AppCompatActivity {

    private EditText txtNombreEP, txtApellidosEP, txtCorreoEP, txtNacimientoEP, txtDireccionEP,txtTelefonoEP,txtDuiEP;
    private Button btnGuardarCambios;
    private Spinner SpinnerGeneroEP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
    }


}