package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;

public class DetallesProducto extends AppCompatActivity {

    TextView producto, precio, estado, unidades, vendedor;
    Button nego, carro, agregar;
    RatingBar estrellas;
    EditText comentarios;
    ListView listViewComentarios;
    private int calificacion;
    ImageView imagen;
    Uri imageUri;
    private static final int REQUEST_CODE_IMAGE = 100;
    private byte[] imagenBytes;
    public final void CargarDetallesProductos(){
        Comentarios comentario = new Comentarios();
        try {
            comentario.setIdProducto(VariablesGlobales.getIdProducto());
            ResultSet rs = comentario.MostrarProducto();
            while (rs.next()){
                producto.setText(rs.getString("Producto"));
                BigDecimal precios = rs.getBigDecimal("Precio original");
                precio.setText("$" + precios.toString() + " USD");
                estado.setText(rs.getString("Estado"));
                vendedor.setText(rs.getString("Vendedor"));
                int cantidad = rs.getInt("Cantidad");
                unidades.setText("" + cantidad);
                imagenBytes = rs.getBytes("Foto");
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
                imagen.setImageBitmap(bitmap);
            }
        }catch (Exception ex){
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_LONG).show();
        }
    }

    private void abrirNegociacion() {
        // Aquí puedes abrir una nueva pantalla, por ejemplo:
        Intent intent = new Intent(this, negociacion.class);
        startActivity(intent);
    }

    private void AgregarCarrito(){
        Producto carrito = new Producto();
        try{
            carrito.setIdPersona(VariablesGlobales.getIdPersona());
            carrito.setIdProducto(VariablesGlobales.getIdProducto());
            carrito.setPrecioUnitario(VariablesGlobales.getPrecioUnitario());
            int valor = carrito.AgregarAlCarritoProducto();
            if(valor == 1){
                Toast.makeText(this,"Se ha agregado al carrito", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"ERROR ", Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex){
            Toast.makeText(this,"ERROR: " + ex, Toast.LENGTH_LONG).show();
        }
    }

    public final void AgregarComentarios(){
        Comentarios comentario = new Comentarios();
        try {
            if(Validaciones.Vacio(comentarios)){
                Toast.makeText(this,"Existen campos vacios", Toast.LENGTH_SHORT).show();
            }else {
                String opinion = comentarios.getText().toString();
                comentario.setCalificacion(calificacion);
                comentario.setOpinion(opinion);
                comentario.setIdPersona(VariablesGlobales.getIdPersona());
                comentario.setIdProducto(VariablesGlobales.getIdProducto());
                int valor = comentario.AgregarCometarioProducto();
                if(valor == 1){
                    Toast.makeText(this,"Se ha ingresado el comentario de forma correcta", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception ex){
            Toast.makeText(this,"ERROR: " + ex, Toast.LENGTH_LONG).show();
        }
    }
    public final void LimpiarCampos(){
        comentarios.setText("");
    }

    public final void CargarComentarios(){
        Comentarios comentario = new Comentarios();
        try {
            comentario.setIdProducto(VariablesGlobales.getIdProducto());
            // Obtén la lista de comentarios desde tu base de datos
            List<Comment> comentarios = comentario.obtenerComentariosProducto();
            // Crea un adaptador personalizado para mostrar los comentarios
            CommentAdapter adapter = new CommentAdapter(this, R.layout.item_comentario, comentarios);
            // Configura el adaptador en tu ListView
            listViewComentarios.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(this,"ERROR: " + ex, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_producto);
        producto = findViewById(R.id.txtProductoDP);
        precio = findViewById(R.id.txtPrecioDP);
        estado = findViewById(R.id.txtEstadoVenta);
        unidades = findViewById(R.id.txtUnidades);
        vendedor = findViewById(R.id.txtVendedorDP);
        estrellas = findViewById(R.id.ratingBar);
        comentarios = findViewById(R.id.txtComentario);
        listViewComentarios = findViewById(R.id.listViewComentarios);
        imagen = (ImageView)findViewById(R.id.imgProductoDP);
        nego = findViewById(R.id.btnNegociar);
        carro = findViewById(R.id.btnCarrito);
        agregar = findViewById(R.id.btnComentar);
        CargarDetallesProductos();
        calificacion = 0;

        // Agregar un oyente de cambios al RatingBar
        estrellas.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Convertir la calificación en un número entero
                calificacion = (int) rating;
                // Mostrar la calificación en un Toast (puedes hacer lo que quieras con ella aquí)
                Toast.makeText(DetallesProducto.this, "Calificación: " + calificacion, Toast.LENGTH_SHORT).show();
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarComentarios();
                CargarComentarios();
                LimpiarCampos();
            }
        });
        nego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNegociacion();
            }
        });
        carro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarCarrito();
            }
        });
        CargarComentarios();
    }
}