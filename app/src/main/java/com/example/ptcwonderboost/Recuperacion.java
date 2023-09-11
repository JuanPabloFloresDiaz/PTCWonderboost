package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Recuperacion {

    public int idUsuario;
    public String Usuario;
    public int ping;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public int RecuperacionPing(){
        PreparedStatement ps;
        Connection con;
        try{
            con = Conexion.getConnection(null);
            int i = 0;
            ps = con.prepareStatement("SELECT idUsuarios FROM TbUsuarios WHERE Usuario = ? AND Ping = ?");
            ps.setString(1, Usuario);
            ps.setInt(2, ping);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                i = 1;
                this.idUsuario = rs.getInt("idUsuarios");
            } else {
                i = 0;
            }
            return i;
        }catch(Exception ex){
            return 0;
        }
    }


}
