package com.example.ptcwonderboost;
import android.content.Context;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class Usuario {

    private String usuario;
    private String clave;
    private int idTipoUsuario;
    private int idEstadoUsuario;
    private int id;

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

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public int getIdEstadoUsuario() {
        return idEstadoUsuario;
    }

    public void setIdEstadoUsuario(int idEstadoUsuario) {
        this.idEstadoUsuario = idEstadoUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public int Login(Context context) {
        Connection con = Conexion.getConnection(null);
        int i = 0;
        PreparedStatement ps;
        try {
            String claveEncriptada = e.encriptarContrasenaSHA256(clave);

            ps = con.prepareStatement("SELECT idUsuarios, Usuario, Clave, idTipoUsuario, idEstadoUsuario FROM TbUsuarios  WHERE Usuario = ? AND Clave = ?");
            ps.setString(1, usuario);
            ps.setString(2, claveEncriptada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                i = 1;
                this.idTipoUsuario = rs.getInt("idTipoUsuario");
                this.idEstadoUsuario = rs.getInt("idEstadoUsuario");
                this.id = rs.getInt("idUsuarios");
            } else {
                i = 0;
            }
            return i;
        } catch (Exception e) {
            return 0;
        }
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
