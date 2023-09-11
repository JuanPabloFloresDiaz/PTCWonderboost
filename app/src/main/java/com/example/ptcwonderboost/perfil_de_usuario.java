package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.List;

public class perfil_de_usuario extends AppCompatActivity {

    private TextView username, profilename;
    private TextView description;
    private ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_de_usuario);
        CargarDatosPerfil();
    }

    public final void CargarDatosPerfil(){
        try {
            username = findViewById(R.id.username);
            profilename = findViewById(R.id.profileName);
            description = findViewById(R.id.description);
            foto = findViewById(R.id.profileImage);
            Perfil perfil = new Perfil();
            ResultSet rs = perfil.mostrardatosdePerfil(VariablesGlobales.getIdUsuario());
            if (rs != null && rs.next()) {
                String nombrePerfil = rs.getString("Nombre perfil");
                String usuario = rs.getString("Usuario");
                String descripcion = rs.getString("Descripcion");
                byte[] fotoBytes = rs.getBytes("Foto");
                Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                foto.setImageBitmap(bitmap);
                profilename.setText(nombrePerfil);
                username.setText(usuario);
                description.setText(descripcion);
            } else {
                Toast.makeText(this, "No se encontraron datos de perfil para este usuario", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(perfil_de_usuario.this, "Error desconocido: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}