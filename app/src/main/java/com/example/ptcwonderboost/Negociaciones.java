package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Negociaciones {
    private int idNegociacion;
    private byte[] Foto;
    private String Nombre;
    private double precioOriginal;
    private double precioOfrecido;
    private double precioVenta;
    private String vendedor;

    public int getIdNegociacion() {
        return idNegociacion;
    }

    public void setIdNegociacion(int idNegociacion) {
        this.idNegociacion = idNegociacion;
    }

    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(byte[] foto) {
        Foto = foto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getPrecioOriginal() {
        return precioOriginal;
    }

    public void setPrecioOriginal(double precioOriginal) {
        this.precioOriginal = precioOriginal;
    }

    public double getPrecioOfrecido() {
        return precioOfrecido;
    }

    public void setPrecioOfrecido(double precioOfrecido) {
        this.precioOfrecido = precioOfrecido;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public List<Negociaciones> CargarNegociaciones(int idPersona) throws SQLException {
        List<Negociaciones> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT n.idNegociacion, CASE WHEN n.idProducto IS NOT NULL THEN p.FotoProducto WHEN n.idServicio IS NOT NULL THEN s.FotoServicio END AS 'Foto', CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Producto o Servicio', CASE WHEN n.idProducto IS NOT NULL THEN PP.Nombres + ' ' + PP.Apellidos WHEN n.idServicio IS NOT NULL THEN SP.Nombres + ' ' + SP.Apellidos END AS Vendedor, p.Precio AS 'Precio original', COALESCE(n.PrecioOfrecido, 0) AS 'Precio ofrecido', COALESCE(n.PrecioVenta, 0) AS 'Precio de venta' FROM tbNegociaciones n LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio LEFT JOIN TbPersonas PP ON PP.idPersonas = P.idPersonas LEFT JOIN TbPersonas SP ON SP.idPersonas = S.idPersonas WHERE n.idPersonas = ?;"); // Tu consulta SQL aquí
            ps.setInt(1, idPersona);
            ResultSet respuesta = ps.executeQuery();

            while (respuesta.next()) {
                Negociaciones Neg = new Negociaciones();
                Neg.setIdNegociacion(respuesta.getInt("idNegociacion"));
                Neg.setFoto(respuesta.getBytes("Foto"));
                Neg.setNombre(respuesta.getString("Producto o Servicio"));
                Neg.setPrecioOriginal(respuesta.getDouble("Precio original"));
                Neg.setPrecioOfrecido(respuesta.getDouble("Precio ofrecido"));
                Neg.setPrecioVenta(respuesta.getDouble("Precio de venta"));
                Neg.setVendedor(respuesta.getString("Vendedor"));
                productos.add(Neg);
            }
            return productos;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
