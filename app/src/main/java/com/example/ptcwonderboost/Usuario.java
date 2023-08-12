package com.example.ptcwonderboost;
import android.content.Context;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

public class Usuario {

    private String usuario;
    private String clave;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    Encriptacion e = new Encriptacion();

    public void insertarUsuario(Context context) {
        Connection connection = Conexion.getConnection(null);
        try{
            String insertQuery = "INSERT INTO TbUsuarios(Usuario, Clave, idTipoUsuario, idEstadoUsuario) VALUES (?,?,3,2)";

            String claveEncriptada = e.encriptarContrasenaSHA256(clave);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, claveEncriptada);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            showToast(context,e.toString());

        }
    }

    public boolean Login() {
        Connection connection = Conexion.getConnection(null);
        try {
            String insertQuery = "SELECT Usuario, Clave FROM TbUsuarios  WHERE Usuario = ? AND Clave = ?";

            String claveEncriptada = e.encriptarContrasenaSHA256(clave);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, claveEncriptada);
            preparedStatement.executeQuery();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
