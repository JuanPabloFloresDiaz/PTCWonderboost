package com.example.ptcwonderboost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class agregar_servicios extends AppCompatActivity {

    private Button btnSeleimg;
    private Button agregar;
    private Button eleminar;
    private Button actualizar;

    private ImageView imgProd;

    protected static final int REQUEST_CODE_IMAGE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_servicios);

        btnSeleimg = findViewById(R.id.btnSeleImg);
        imgProd = findViewById(R.id.imgProd);
        agregar = findViewById(R.id.btnAgregarSer);
        eleminar = findViewById(R.id.btnEliminarSer);
        actualizar = findViewById(R.id.btnActualizarSer);



        btnSeleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imgProd.setImageURI(imageUri);
        }
    }

}