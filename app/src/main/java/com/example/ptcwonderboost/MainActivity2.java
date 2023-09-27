package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
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
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;

import java.sql.ResultSet;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    //Metodo para redondear una imagen desde codigo
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
    private void abrirNuevaPantalla1() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, Ganancias.class);
        startActivity(intent);
    }
    private void abrirNuevaPantalla2() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, Balance.class);
        startActivity(intent);
    }
    private void abrirNuevaPantalla3() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, Tasa_Economica.class);
        startActivity(intent);
    }
    private void abrirNuevaPantalla4() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, Solicitar_Permiso.class);
        startActivity(intent);
    }
    //Metodo usado para dar imagen al toolbar
    final void ObtenerImagen(){
        try {
            //Clase perfil
            Perfil perfil = new Perfil();
            //Traer de la base de datos
            ResultSet rs = perfil.mostrardatosdePerfil(VariablesGlobales.getIdUsuario());
            if (rs != null && rs.next()) {
                byte[] fotoBytes = rs.getBytes("Foto");
                if (fotoBytes != null) {
                    //Convertir la imagen
                    Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                    toolbar.setNavigationIcon(new BitmapDrawable(getResources(), bitmap));
                } else {
                    // Si no se encuentra el ícono en la base de datos, puedes establecer un ícono predeterminado aquí
                    toolbar.setNavigationIcon(R.drawable.menu_24);
                }
            } else {
                Toast.makeText(this, "No se encontraron datos de perfil para este usuario", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Toast.makeText(this, "Ocurrio un error: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

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
        CardView cardView1 = findViewById(R.id.cardGanancias);
        CardView cardView2 = findViewById(R.id.cardBalance);
        CardView cardView3 = findViewById(R.id.cardTasa);
        CardView cardView4 = findViewById(R.id.cardPermiso);
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
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrirNuevaPantalla1();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrirNuevaPantalla2();
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrirNuevaPantalla3();
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNuevaPantalla4();
            }
        });
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
        } else if (itemId == R.id.nav_rate1) {
            Intent intent = new Intent(MainActivity2.this, Envios.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_rate2) {
            Intent intent = new Intent(MainActivity2.this, PagoSeguimientos.class);
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