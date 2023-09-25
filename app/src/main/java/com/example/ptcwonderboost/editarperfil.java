package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class editarperfil extends AppCompatActivity {

    private EditText fechaEditText, txtNombre, txtApellidos, txtDireccion, txtCorreo, txtDescripcion, txtTelefono, txtDUI;
    private Calendar calendar = Calendar.getInstance();
    private Button btnEditarPerfil;
    private int idGenero, idNacionalidad;
    private Spinner genero, nacionalidades;
    ImageView imagen;
    Uri imageUri;
    private byte[] imagenBytes;
    private static final int REQUEST_CODE_IMAGE = 100;
    String[] generos = {"Masculino", "Femenino"};

    public final void CargarDatosEditarPerfil(){
        try{
            Persona persona = new Persona();
            persona.setId(VariablesGlobales.getIdUsuario());
            ResultSet rs = persona.mostrarDatosEditarPerfil();
            if (rs != null && rs.next()) {
                String nombres = rs.getString("Nombres");
                String apellidos = rs.getString("Apellidos");
                String descripcion = rs.getString("Descripcion");
                String nacimiento = rs.getString("Nacimiento");
                String direccion = rs.getString("Direccion");
                String telefono = rs.getString("Telefono");
                String correo = rs.getString("Correo");
                String dui = rs.getString("DUI");
                int gen = rs.getInt("Genero");
                int nac = rs.getInt("idNacionalidad");
                imagenBytes = rs.getBytes("Foto");
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
                imagen.setImageBitmap(bitmap);
                txtNombre.setText(nombres);
                txtApellidos.setText(apellidos);
                txtDireccion.setText(direccion);
                txtCorreo.setText(correo);
                txtDescripcion.setText(descripcion);
                txtTelefono.setText(telefono);
                txtDUI.setText(dui);
                fechaEditText.setText(nacimiento);
                genero.setSelection(gen);
                nacionalidades.setSelection(nac);
            } else {
                Toast.makeText(this, "No se encontraron datos de perfil para este usuario", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex) {
            Toast.makeText(editarperfil.this,"Error: " + ex,Toast.LENGTH_LONG).show();
        }
    }
    final void EditarPerfil(){
        Persona persona = new Persona();
        String Nombres = txtNombre.getText().toString();
        String Apellidos = txtApellidos.getText().toString();
        String Correo = txtCorreo.getText().toString();
        String Direccion = txtDireccion.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String DUI = txtDUI.getText().toString();
        String Descripcion = txtDescripcion.getText().toString();
        if(Validaciones.Vacio(txtNombre) || Validaciones.Vacio(txtApellidos) || Validaciones.Vacio(txtCorreo) || Validaciones.Vacio(txtDescripcion) || Validaciones.Vacio(txtDUI) || Validaciones.Vacio(txtDireccion) || Validaciones.Vacio(txtTelefono)){
            Toast.makeText(editarperfil.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
        }else if(!Validaciones.Letras(txtNombre) || !Validaciones.Letras(txtApellidos)){
            Toast.makeText(editarperfil.this, "Nombre y apellidos solo deben tener letras", Toast.LENGTH_SHORT).show();
        }else if(!Validaciones.ValidarTelefono(telefono)){
            Toast.makeText(editarperfil.this, "El teléfono solo debe contener números", Toast.LENGTH_SHORT).show();
        }else if(!Validaciones.ValidarCorreo(Correo)){
            Toast.makeText(editarperfil.this, "Formato incorrecto para el correo", Toast.LENGTH_SHORT).show();
        }else if(!Validaciones.ValidarDUI(DUI)){
            Toast.makeText(editarperfil.this, "Formato de dui incorrecto", Toast.LENGTH_SHORT).show();
        }else{
            try {

                persona.setNombres(Nombres);
                persona.setApellidos(Apellidos);
                persona.setGenero((byte) idGenero);
                persona.setNacimiento(fechaEditText.getText().toString());
                persona.setDireccion(Direccion);
                persona.setTelefono(telefono);
                persona.setCorreo(Correo);
                persona.setDui(DUI);
                try {
                    persona.setFoto(imagenBytes);
                }catch (Exception ex) {
                    persona.setFoto(null);
                }
                persona.setDescripcion(Descripcion);
                persona.setIdNacionalidad(idNacionalidad);
                persona.setId(VariablesGlobales.getIdUsuario());
                int valor = persona.EditarPerfil();
                if(valor == 1){
                    Toast.makeText(editarperfil.this, "Se han editado los datos", Toast.LENGTH_SHORT).show();
                }else if(valor == 0){
                    Toast.makeText(editarperfil.this, "ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(editarperfil.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(editarperfil.this, "Error: " + e, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfil);
        txtNombre = findViewById(R.id.txtNombreEP);
        txtApellidos = findViewById(R.id.txtApellidosEP);
        txtDireccion = findViewById(R.id.txtDireccionEP);
        txtCorreo = findViewById(R.id.txtCorreoEP);
        txtDescripcion = findViewById(R.id.txtDescripcionEP);
        txtTelefono = findViewById(R.id.txtTelefonoEP);
        txtDUI = findViewById(R.id.txtDuiEP);

        genero = findViewById(R.id.SpinnerGeneroEP);
        ArrayAdapter<String> adapterG = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generos);
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genero.setAdapter(adapterG);
        genero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(genero.getSelectedItem() == "Masculino"){
                    idGenero = 0;
                }else if(genero.getSelectedItem() == "Femenino"){
                    idGenero = 1;
                }else{
                    Toast.makeText(editarperfil.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });

        Persona persona = new Persona();
        nacionalidades = findViewById(R.id.SpinnerNacionalidadEP);
        ResultSet resultSet = persona.CargarNacionalidad();
        if (resultSet != null) {
            List<String> datos = new ArrayList<>();
            datos.add("Seleccionar");
            try {
                while (resultSet.next()) {
                    // Obtén el valor de la columna "Nacionalidad" y agrégalo a la lista
                    String nacionalidad = resultSet.getString("Nacionalidad");
                    datos.add(nacionalidad);
                }
                // Crea el adaptador y asigna los datos al Spinner
                ArrayAdapter<String> adapterN = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datos);
                adapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nacionalidades.setAdapter(adapterN);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        nacionalidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!selectedItem.equals("Seleccionar")) {
                    // Realiza acciones basadas en el elemento seleccionado
                    idNacionalidad = (int) id;
                    Toast.makeText(editarperfil.this, "Seleccionaste: " + selectedItem + " " +id, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(editarperfil.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });
        nacionalidades.post(new Runnable() {
            @Override
            public void run() {

            }
        });


        fechaEditText = findViewById(R.id.txtNacimientoEP);
        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        imagen = (ImageView)findViewById(R.id.imgFotoPerfilEP);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        CargarDatosEditarPerfil();
        btnEditarPerfil = findViewById(R.id.btnGuardarCambios);
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                EditarPerfil();
                }catch (Exception ex){

                }
            }
        });

    }

    private void cargarImagen() {
        try {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.setType("image/*");
            startActivityForResult(gallery, REQUEST_CODE_IMAGE);
        } catch (Exception ex) {
            Toast.makeText(editarperfil.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_IMAGE){
                imageUri = data.getData();
                imagen.setImageURI(imageUri);
                // Convierte la imagen en un array de bytes
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }
                    imagenBytes = byteArrayOutputStream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(editarperfil.this,"Ruta nula ",Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(editarperfil.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar currentDate = Calendar.getInstance();
            Calendar minDate = Calendar.getInstance();
            minDate.set(1900, Calendar.JANUARY, 1);
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);

            if (selectedDate.after(currentDate)) {
                Toast.makeText(editarperfil.this, "Seleccione una fecha menor o igual a la fecha actual", Toast.LENGTH_SHORT).show();
            } else if (selectedDate.before(minDate)) {
                Toast.makeText(editarperfil.this, "Seleccione una fecha mayor a 1900", Toast.LENGTH_SHORT).show();
            } else {
                String selectedDateStr = dayOfMonth + "-" + (month + 1) + "-" + year;
                fechaEditText.setText(selectedDateStr);
            }
        }
    };
}