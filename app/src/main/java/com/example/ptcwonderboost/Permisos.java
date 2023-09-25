package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Permisos {
    public int idPermiso;
    public int idPersona;
    public String descripcion;
    public String empresa;
    public String NRC;
    public String enfoque;

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNRC() {
        return NRC;
    }

    public void setNRC(String NRC) {
        this.NRC = NRC;
    }

    public String getEnfoque() {
        return enfoque;
    }

    public void setEnfoque(String enfoque) {
        this.enfoque = enfoque;
    }

    //Solicitar permiso de venta
    public int SolicitarPermisoVenta(){
        PreparedStatement ps;
        Connection con = null;
        try{
            con = Conexion.getConnection(null);
            String query = "INSERT INTO tbPermisoVenta(idPersonas, DescripcionEmprendimiento, NombreEmpresa, NRC, Enfoque) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersona);
            ps.setString(2, descripcion);
            ps.setString(3, empresa);
            ps.setString(4, NRC);
            ps.setString(5, enfoque);
            ps.execute();
            return 1;
        }catch(Exception e){
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
