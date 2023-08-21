package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*---------------------Hooks------------------------*/
        drawerLayout=findViewById(R.id.drawer_Layout);
        navigationView=findViewById(R.id.nav_view);
        textView=findViewById(R.id.textViewMenu);
        toolbar=findViewById(R.id.toolbar);
        /*---------------------Tool Bar------------------------*/
        setSupportActionBar(toolbar);
        /*---------------------Navigation Drawer Menu------------------------*/
        Menu menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try{
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_login) {
            Intent intent = new Intent(MainActivity2.this, InventarioActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity2.this, Catalogo.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity2.this, ProductosIngreso.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_logout2) {
            Intent intent = new Intent(MainActivity2.this, ProductosServicios.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_share) {
            Intent intent = new Intent(MainActivity2.this, Pedidos.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_rate) {
            Intent intent = new Intent(MainActivity2.this, ClientesFrecuentes.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_rate1) {
            Intent intent = new Intent(MainActivity2.this, Envios.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_rate2) {
            Intent intent = new Intent(MainActivity2.this, Calendario.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_share3) {
            Intent intent = new Intent(MainActivity2.this, Necociaciones.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_rate4) {
            Intent intent = new Intent(MainActivity2.this, CarritoCompras.class);
            startActivity(intent);
        }else if (itemId == R.id.nav_config) {
            Intent intent = new Intent(MainActivity2.this, configuracion.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_perfil) {
            Intent intent = new Intent(MainActivity2.this, perfil_de_usuario.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;}
        catch(NullPointerException e){
            Toast.makeText(MainActivity2.this,"Error +: " + e, Toast.LENGTH_SHORT).show();
            return false;
        }catch(NetworkOnMainThreadException e){
            Toast.makeText(MainActivity2.this,"Error +: " + e, Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e) {
            Toast.makeText(MainActivity2.this, "Error +: " + e, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}