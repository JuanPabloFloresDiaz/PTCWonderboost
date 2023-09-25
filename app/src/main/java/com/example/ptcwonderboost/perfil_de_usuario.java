package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.List;

public class perfil_de_usuario extends AppCompatActivity {

    private Button btnEditarPerfil, btnCerrarSesion;
    private TextView username, profilename;
    private TextView description;
    private ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_de_usuario);
        CargarDatosPerfil();
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil);
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil_de_usuario.this, editarperfil.class);
                startActivity(intent);
            }
        });
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil_de_usuario.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private Bitmap getRoundedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = Math.min(width, height);

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Calcula el radio del círculo, que es la mitad del tamaño más pequeño (ancho o alto)
        float radius = size / 2f;

        // Calcula el centro del círculo (coordenadas x e y)
        float centerX = width / 2f;
        float centerY = height / 2f;

        // Dibuja el círculo redondeado sin recortar la imagen original
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Dibuja la imagen original sobre el círculo
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return output;
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
                Bitmap roundedBitmap = getRoundedBitmap(bitmap);
                foto.setImageBitmap(roundedBitmap);
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