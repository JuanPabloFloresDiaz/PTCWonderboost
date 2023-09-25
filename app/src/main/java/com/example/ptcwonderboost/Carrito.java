package com.example.ptcwonderboost;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Carrito {

    private int idPersona;
    private String nombre;
    private BigDecimal precio;
    private String estado;
    private byte[] foto;
    private String categoria;

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }




    //Metodo para traer los datos de la base, que seran utilizadas para darle datos a la CARD
    public ResultSet mostrarCartasCarrito(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT CASE WHEN Ca.idProducto IS NOT NULL THEN P.Producto WHEN Ca.idServicio IS NOT NULL THEN S.Servicio END AS Nombre, CASE WHEN Ca.idProducto IS NOT NULL THEN PP.Nombres + ' ' + PP.Apellidos WHEN Ca.idServicio IS NOT NULL THEN SP.Nombres + ' ' + SP.Apellidos END AS Vendedor, Ca.PrecioUnitario AS Precio, CASE WHEN Ca.idProducto IS NOT NULL THEN P.FotoProducto WHEN Ca.idServicio IS NOT NULL THEN S.FotoServicio END AS Foto, CASE WHEN Ca.idProducto IS NOT NULL THEN EP.EstadoProducto WHEN Ca.idServicio IS NOT NULL THEN ES.EstadoServicio END AS Estado, CASE WHEN Ca.idProducto IS NOT NULL THEN PC.Categoria WHEN Ca.idServicio IS NOT NULL THEN SC.Categoria END AS Categoria, CASE WHEN Ca.idProducto IS NOT NULL THEN P.Descripcion WHEN Ca.idServicio IS NOT NULL THEN S.Descripcion END AS Descripcion FROM tbCarrito Ca LEFT JOIN tbProductos P ON Ca.idProducto = P.idProducto LEFT JOIN tbServicios S ON Ca.idServicio = S.idServicio LEFT JOIN tbEstadosProductos EP ON P.idEstadoProducto = EP.idEstadoProducto LEFT JOIN tbEstadosServicios ES ON S.idEstadoServicio = ES.idEstadoServicio LEFT JOIN tbCategorias PC ON P.idCategoria = PC.idCategoria LEFT JOIN tbCategorias SC ON S.idCategoria = SC.idCategoria LEFT JOIN TbPersonas PP ON PP.idPersonas = P.idPersonas LEFT JOIN TbPersonas SP ON SP.idPersonas = S.idPersonas WHERE Ca.idPersonas = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersona);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            return null;
        }
    }
}
