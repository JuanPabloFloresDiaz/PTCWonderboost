package com.example.ptcwonderboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.ResultSet;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.txtusuario);
        EditText passwordEditText = findViewById(R.id.txtcontra);

        Button loginButton = findViewById(R.id.btningresar);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                try {
                    usuario.setUsuario(usernameEditText.getText().toString());
                    usuario.setClave(passwordEditText.getText().toString());
                    int respuesta = usuario.Login(Login.this);
                    if(respuesta == 1){
                        Toast.makeText(Login.this, "Aviso credenciales correctas", Toast.LENGTH_SHORT).show();
                        int idTipoUsuario = usuario.getIdTipoUsuario();
                        int idEstadoUsuario = usuario.getIdEstadoUsuario();
                        int idUsuario = usuario.getId();

                        VariablesGlobales.idUsuario = idUsuario;
                        VariablesGlobales.idTipoUsuario = idTipoUsuario;
                        VariablesGlobales.idEstado = idEstadoUsuario;
                        usuario.setId(VariablesGlobales.idUsuario);
                        int valor = usuario.ActualizarEstadoActivo();
                        if(valor == 1){
                            Toast.makeText(Login.this, "Se actualizo su estado a activo:" + idUsuario, Toast.LENGTH_SHORT).show();
                        }
                        ResultSet identificador = usuario.CapturarIDPersona();
                        try{
                            while (identificador.next()){
                                VariablesGlobales.idPersona = identificador.getInt("idPersonas");
                                VariablesGlobales.permisoVenta = identificador.getByte("permisoVenta");
                                VariablesGlobales.idioma = identificador.getByte("idIdioma");
                                VariablesGlobales.modoColor = identificador.getByte("ModoColor");
                            }
                        }catch(Exception ex){
                            Toast.makeText(Login.this, "No se pudo capturar el ID", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(Login.this, MainActivity2.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Login.this, "Error credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(Login.this, "Error con el login" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button testConnectionButton = findViewById(R.id.testConnectionButton);
        testConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDatabaseConnection();
            }
        });

        TextView registrarse = findViewById(R.id.twcuenta);
            registrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login.this, Registrarse.class);
                    startActivity(intent);
                }
            });

           TextView recuperacion = findViewById(R.id.twclave);
           recuperacion.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(Login.this, ReestablecerClave.class);
                   startActivity(intent);
               }
            });
        }




    private void testDatabaseConnection() {
        Connection connection = Conexion.getConnection(this);
        if (connection != null) {
            Toast.makeText(this, "Conexión exitosa", Toast.LENGTH_SHORT).show();
            Conexion.closeConnection(connection);
        } else {
            Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show();
        }
    }


}