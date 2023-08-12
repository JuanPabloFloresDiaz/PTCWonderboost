package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Registrarse extends AppCompatActivity {

    private EditText etPass;
    private EditText etUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        etUser = (EditText) findViewById(R.id.txtusuarioR);
        etPass=(EditText) findViewById(R.id.txtpass);

        Button Cancel = findViewById(R.id.btncancelar);
        Button Regist = findViewById(R.id.btnregistrar);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registrarse.this, Login.class);
                startActivity(intent);
            }
        });
    }

}
