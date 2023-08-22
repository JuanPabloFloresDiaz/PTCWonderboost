package com.example.ptcwonderboost;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class agregar_servicios extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextPrecio;
    private Spinner spinnerTipoPrecio;
    private EditText editTextDescripcion;
    private Spinner spinnerCantidad;
    private Button btnSeleimg;
    private ImageView imgProd;


    protected static final int REQUEST_CODE_IMAGE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_servicios);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        spinnerTipoPrecio = findViewById(R.id.spinnerTipoPrecio);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        spinnerCantidad = findViewById(R.id.spinnerCantidad);
        btnSeleimg = findViewById(R.id.btnSeleImg);
        imgProd = findViewById(R.id.imgProd);

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

    public void guardarProducto(View view) {
        // Obtener los datos ingresados por el usuario
        String nombre = editTextNombre.getText().toString();
        double precio = Double.parseDouble(editTextPrecio.getText().toString());
        String tipoPrecio = spinnerTipoPrecio.getSelectedItem().toString();
        String descripcion = editTextDescripcion.getText().toString();
        int cantidad = Integer.parseInt(spinnerCantidad.getSelectedItem().toString());

        String sql = "INSERT INTO productos (nombre, precio, tipo_precio, descripcion, cantidad, imagen) VALUES (?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, nombre);
        statement.setDouble(2, precio);
        statement.setString(3, tipoPrecio);
        statement.setString(4, descripcion);
        statement.setInt(5, cantidad);

        // Convertir la imagen a un arreglo de bytes para almacenarla en la base de datos
        Bitmap bitmap = ((BitmapDrawable) imgProd.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imagenBytes = stream.toByteArray();
        statement.setBytes(6, imagenBytes);

        // Ejecutar la instrucción INSERT
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
            // Limpiar los campos de entrada y la vista previa de la imagen
            editTextNombre.setText("");
            editTextPrecio.setText("");
            spinnerTipoPrecio.setSelection(0);
            editTextDescripcion.setText("");
            spinnerCantidad.setSelection(0);
            imgProd.setImageResource(R.drawable.ic_add_photo);
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }

