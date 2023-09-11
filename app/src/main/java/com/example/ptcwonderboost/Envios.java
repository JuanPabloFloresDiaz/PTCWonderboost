package com.example.ptcwonderboost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class Envios extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envios);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.EnvioIngreso) {
                    startActivity(new Intent(Envios.this, Envios.class));
                    return true;
                } else if (item.getItemId() == R.id.Gestionar) {
                    startActivity(new Intent(Envios.this, gestionar.class));
                    return true;
                } else if (item.getItemId() == R.id.enviohistorial) {
                    startActivity(new Intent(Envios.this, historial.class));
                    return true;
                }
                return false;
            }
        });
    }
}