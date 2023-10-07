package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    private void Grafica(){
        Grafica ccar = new Grafica();
        try{
            PieChart pieChart = findViewById(R.id.chart1);

            ArrayList<PieEntry> entries = new ArrayList<>();
            ResultSet rs = ccar.llenarGraficas();
            while(rs.next()){
            entries.add(new PieEntry(rs.getInt("TotalCompras"), rs.getString("Producto")));
            }

            PieDataSet dataSet = new PieDataSet(entries, "Productos mas vendidos");
            ArrayList<Integer> colors = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < entries.size(); i++) {
                int color = Color.rgb(random.nextInt(154), random.nextInt(154), random.nextInt(154));
                colors.add(color);
            }
            dataSet.setColors(colors); // Asigna la lista de colores aleatorios
            dataSet.setValueTextSize(10f); // Tamaño del texto

            PieData pieData = new PieData(dataSet);

            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false); // Deshabilitar descripción
            pieChart.setCenterText("Productos mas vendidos"); // Texto central
            // Establece el color del texto central en blanco
            pieChart.setCenterTextColor(android.graphics.Color.WHITE);
            // Establece el color del fondo del círculo en negro
            pieChart.setHoleColor(android.graphics.Color.BLACK);
            pieChart.animateY(1000); // Animación de entrada
        }catch (Exception ex){
            Toast.makeText(this,"Error: "+ex , Toast.LENGTH_SHORT).show();        }
    }

    private void GraficaDeBarras() {
        Grafica ccar = new Grafica();
        try {
            BarChart barChart = findViewById(R.id.chart1); // Reemplaza con el ID de tu gráfico de barras en el diseño XML

            ArrayList<BarEntry> entries = new ArrayList<>();
            ResultSet rs = ccar.llenarGraficas();
            int i = 0;
            ArrayList<String> productNames = new ArrayList<>();
            while (rs.next()) {
                entries.add(new BarEntry(i++, rs.getInt("TotalCompras")));
                productNames.add(rs.getString("Producto"));
            }

            BarDataSet dataSet = new BarDataSet(entries, "Productos más vendidos");
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS); // Colores predeterminados alegres
            dataSet.setValueTextSize(10f); // Tamaño del texto

            BarData barData = new BarData(dataSet);
            barChart.setData(barData);

            // Personalización adicional del gráfico de barras
            barChart.getDescription().setEnabled(false); // Deshabilitar descripción
            barChart.setDrawValueAboveBar(true); // Muestra los valores encima de las barras
            barChart.setFitBars(true); // Ajusta automáticamente el ancho de las barras
            barChart.animateY(1000); // Animación de entrada
            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(productNames));
            barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // Coloca el eje X en la parte inferior
            barChart.getXAxis().setLabelRotationAngle(45f);
            barChart.getLegend().setEnabled(false); // Deshabilitar leyenda

            // Actualiza el gráfico
            barChart.invalidate();

        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

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
                abrirNuevaPantalla1();
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNuevaPantalla2();
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNuevaPantalla3();
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNuevaPantalla4();
            }
        });
        GraficaDeBarras();
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