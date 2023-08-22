package com.example.ptcwonderboost;
import java.sql.*;
public class Perfil {
    public ResultSet mostrardatosdePerfil(int id){
        Connection con;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT p.Nombres + ' ' + p.Apellidos AS 'Nombre perfil', u.Usuario, p.Descripcion FROM TbPersonas p INNER JOIN TbUsuarios u ON u.idUsuarios = p.idUsuarios WHERE p.idUsuarios = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception ex){
            return null;
        }
    }

    public ResultSet mostrarfotodePerfil(int id){
        Connection con;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT Foto FROM TbPersonas WHERE idUsuarios = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception ex){
            return null;
        }
    }

}
