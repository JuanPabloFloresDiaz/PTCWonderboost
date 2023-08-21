package com.example.ptcwonderboost;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistrarsePersona extends AppCompatActivity {

    private EditText fechaEditText, txtNombre, txtApellidos, txtDireccion, txtCorreo, txtDescripcion, txtTelefono, txtDUI;
    private Calendar calendar = Calendar.getInstance();
    private Button botonImagen, botonRegistrarPersona;

    private int idGenero, idNacionalidad;
    private Spinner genero, nacionalidades;
    ImageView imagen;




    private static final int REQUEST_CODE_IMAGE = 101;
    String[] generos = {"Masculino", "Femenino"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_persona);
        txtNombre = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDUI = findViewById(R.id.txtDUI);

        genero = findViewById(R.id.spinnerGenero);
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
                    Toast.makeText(RegistrarsePersona.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejo cuando no se selecciona nada
            }
        });

        Persona persona = new Persona();
        nacionalidades = findViewById(R.id.spinnerNacionalidad);
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
                    Toast.makeText(RegistrarsePersona.this, "Seleccionaste: " + selectedItem + " " +id, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistrarsePersona.this, "No haz seleccionado nada ", Toast.LENGTH_SHORT).show();
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
                nacionalidades.setSelection(54);
            }
        });


        fechaEditText = findViewById(R.id.txtFecha);
        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        botonImagen = findViewById(R.id.selectImageButton);
        imagen = (ImageView) findViewById(R.id.imgFotoPerfil);

        botonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        botonRegistrarPersona = findViewById(R.id.btnAgregarPersona);
        botonRegistrarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String Nombres = txtNombre.getText().toString();
                    String Apellidos = txtApellidos.getText().toString();
                    String Correo = txtCorreo.getText().toString();
                    String Direccion = txtDireccion.getText().toString();
                    String telefono = txtTelefono.getText().toString();
                    String DUI = txtDUI.getText().toString();
                    String Descripcion = txtDescripcion.getText().toString();


                    persona.setNombres(Nombres);
                    persona.setApellidos(Apellidos);
                    persona.setGenero((byte) idGenero);
                    persona.setNacimiento(fechaEditText.getText().toString());
                    persona.setDireccion(Direccion);
                    persona.setTelefono(telefono);
                    persona.setCorreo(Correo);
                    persona.setDui(DUI);
                    persona.setDescripcion(Descripcion);
                    persona.setIdUsuarios(VariablesGlobales.getIdRegistro());
                    persona.setIdNacionalidad(idNacionalidad);
                    persona.setModoColor((byte)0);
                    persona.setIdIdioma(1);

                    int valor = persona.RegistrarPersona();
                    if(valor == 1){
                        Toast.makeText(RegistrarsePersona.this, "Se han ingresado los datos", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(RegistrarsePersona.this, Login.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegistrarsePersona.this, "Ha ocurrido un error", Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    Toast.makeText(RegistrarsePersona.this, "Error: " + e, Toast.LENGTH_SHORT);
                }
            }
        });
    }


    private void cargarImagen() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/");
            startActivityForResult(intent.createChooser(intent, "Seleccione la imagen"), 10);
        } catch (Exception ex) {
            Toast.makeText(RegistrarsePersona.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK){
            Uri path=data.getData();
            Toast.makeText(RegistrarsePersona.this,"Ruta: " + path,Toast.LENGTH_LONG).show();
            imagen.setImageURI(path);
        }
        else{
            Toast.makeText(RegistrarsePersona.this,"Ruta nula ",Toast.LENGTH_LONG).show();
        }
        }catch (Exception ex){
            Toast.makeText(RegistrarsePersona.this,ex.getMessage(),Toast.LENGTH_LONG).show();
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
                Toast.makeText(RegistrarsePersona.this, "Seleccione una fecha menor o igual a la fecha actual", Toast.LENGTH_SHORT).show();
            } else if (selectedDate.before(minDate)) {
                Toast.makeText(RegistrarsePersona.this, "Seleccione una fecha mayor a 1900", Toast.LENGTH_SHORT).show();
            } else {
                String selectedDateStr = dayOfMonth + "/" + (month + 1) + "/" + year;
                fechaEditText.setText(selectedDateStr);
            }
        }
    };



}