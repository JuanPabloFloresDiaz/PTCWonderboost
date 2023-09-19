package com.example.ptcwonderboost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class editarPerfil extends AppCompatActivity {

    private EditText txtNombreEP, txtApellidosEP, txtCorreoEP, txtNacimientoEP, txtDireccionEP,txtTelefonoEP,txtDuiEP;
    private Button btnGuardarCambios;
    private Spinner SpinnerGeneroEP;


    private int id;
    private String nombres;
    private String apellidos;
    private byte permisoVenta;
    private String nombrePerfil;
    private String usuario;
    private byte genero;
    private String nacimiento;
    private String direccion;
    private String telefono;
    private String correo;
    private String dui;
    private byte[] foto;
    private String descripcion;
    private int idUsuarios;
    private int idNacionalidad;
    private byte modoColor;
    private int idIdioma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        txtNombreEP = findViewById(R.id.txtNombreEP);
        txtApellidosEP = findViewById(R.id.txtApellidosEP);
        txtCorreoEP = findViewById(R.id.txtCorreoEP);
        txtNacimientoEP = findViewById(R.id.txtNacimientoEP);
        txtDireccionEP = findViewById(R.id.txtDireccionEP);
        txtTelefonoEP = findViewById(R.id.txtTelefonoEP);
        txtDuiEP = findViewById(R.id.txtDuiEP);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        SpinnerGeneroEP = findViewById(R.id.SpinnerGeneroEP);

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarPerfil();
            }
        });


    }
    public int EditarPerfil(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "UPDATE TbPersonas SET Nombres= ?, Apellidos = ?, Genero = ?, Nacimiento = ?, Direccion = ?, Telefono = ?, Correo = ?, DUI = ?, Foto = ?, Descripcion = ?, idNacionalidad = ?  WHERE idUsuarios = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setByte(3, genero);
            ps.setString(4, nacimiento);
            ps.setString(5, direccion);
            ps.setString(6, telefono);
            ps.setString(7, correo);
            ps.setString(8, dui);
            ps.setBytes(9, foto);
            ps.setString(10, descripcion);
            ps.setInt(11, idNacionalidad);
            ps.setInt(12, id);
            ps.execute();
            return 1;
        }catch(Exception ex){
            return 0;
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}