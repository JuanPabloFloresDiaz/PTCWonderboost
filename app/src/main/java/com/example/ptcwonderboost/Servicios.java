package com.example.ptcwonderboost;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Servicios {
    public int idServicio;
    public String servicio;
    public BigDecimal precio;
    public byte[] fotoServicio;
    public String Descripcion;
    public int idCategoria;
    public int idEstadoServicio;
    public int idTipoPrecio;
    public int idPersonas;

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public byte[] getFotoServicio() {
        return fotoServicio;
    }

    public void setFotoServicio(byte[] fotoServicio) {
        this.fotoServicio = fotoServicio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdEstadoServicio() {
        return idEstadoServicio;
    }

    public void setIdEstadoServicio(int idEstadoServicio) {
        this.idEstadoServicio = idEstadoServicio;
    }

    public int getIdTipoPrecio() {
        return idTipoPrecio;
    }

    public void setIdTipoPrecio(int idTipoPrecio) {
        this.idTipoPrecio = idTipoPrecio;
    }

    public int getIdPersonas() {
        return idPersonas;
    }

    public void setIdPersonas(int idPersonas) {
        this.idPersonas = idPersonas;
    }

    public ResultSet MostrarDatosServicios(){
        Connection con;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT idServicio, Servicio, Precio, FotoServicio, Descripcion, idCategoria, idEstadoServicio, idTipoPrecio FROM tbServicios WHERE idServicio = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idServicio);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception ex){
            return null;
        }
    }

    public ResultSet mostrarServicios(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con= Conexion.getConnection(null);
            String query = "SELECT idServicio, Servicio AS 'Nombre del servicio', Precio AS 'Precio del servicio' FROM tbServicios WHERE idPersonas = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersonas);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            return null;
        }
    }

    public int AgregarServicio(){
        PreparedStatement ps;
        Connection con = null;
        try {
            con = Conexion.getConnection(null);
            String query = "INSERT INTO tbServicios(Servicio, Precio, FotoServicio, Descripcion, idCategoria, idEstadoServicio, idTipoPrecio, idPersonas) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, servicio);
            ps.setBigDecimal(2, precio);
            ps.setBytes(3, fotoServicio);
            ps.setString(4, Descripcion);
            ps.setInt(5, idCategoria);
            ps.setInt(6, idEstadoServicio);
            ps.setInt(7, idTipoPrecio);
            ps.setInt(8, idPersonas);
            ps.executeUpdate();
            return 1;
        }catch (Exception e) {
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

    public int EditarServicioImagen(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "UPDATE tbServicios SET Servicio= ?, Precio = ?, FotoServicio = ?, Descripcion = ?, idCategoria = ?, idEstadoServicio = ?, idTipoPrecio = ?, idPersonas = ? WHERE idServicio = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, servicio);
            ps.setBigDecimal(2, precio);
            ps.setBytes(3, fotoServicio);
            ps.setString(4, Descripcion);
            ps.setInt(5, idCategoria);
            ps.setInt(6, idEstadoServicio);
            ps.setInt(7, idTipoPrecio);
            ps.setInt(8, idPersonas);
            ps.setInt(9, idServicio);
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

    public int EliminarServicio(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "DELETE tbServicios WHERE idServicio = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idServicio);
            ps.execute();
            return 1;
        }
        catch(Exception e){
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
