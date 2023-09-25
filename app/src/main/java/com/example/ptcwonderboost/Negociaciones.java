package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Negociaciones {
    private int idNegociacion;
    private int idProducto;
    private int idEstado;
    private int idPersona;
    private int idFormaPago;
    private int cantidad;
    private byte[] Foto;
    private String Nombre;
    private double precioOriginal;
    private double precioOfrecido;
    private double precioVenta;
    private String vendedor;
    private String comprador;
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

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    //Metodo para negociaciones para productos
    public int ConfirmarNegociacionProducto(){
        PreparedStatement ps;
        Connection con = null;
        try {
            con = Conexion.getConnection(null);
            String query = "INSERT INTO tbNegociaciones(idProducto, idEstadoNegociacion, idPersonas, CantidadPedida, PrecioOfrecido, PrecioVenta, idFormaPago) VALUES (?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
            ps.setInt(2, idEstado);
            ps.setInt(3, idPersona);
            ps.setInt(4, cantidad);
            ps.setDouble(5, precioOfrecido);
            ps.setDouble(6, precioVenta);
            ps.setInt(7, idFormaPago);
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

    public List<Negociaciones> CargarNegociaciones(int idPersona) throws SQLException {
        List<Negociaciones> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT n.idNegociacion, CASE WHEN n.idProducto IS NOT NULL THEN p.FotoProducto WHEN n.idServicio IS NOT NULL THEN s.FotoServicio END AS 'Foto', CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Producto o Servicio', CASE WHEN n.idProducto IS NOT NULL THEN PP.Nombres + ' ' + PP.Apellidos WHEN n.idServicio IS NOT NULL THEN SP.Nombres + ' ' + SP.Apellidos END AS Vendedor, CASE WHEN n.idProducto IS NOT NULL THEN p.Precio WHEN n.idServicio IS NOT NULL THEN s.Precio END AS 'Precio original', COALESCE(n.PrecioOfrecido, 0) AS 'Precio ofrecido', COALESCE(n.PrecioVenta, 0) AS 'Precio de venta' FROM tbNegociaciones n LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio LEFT JOIN TbPersonas PP ON PP.idPersonas = P.idPersonas LEFT JOIN TbPersonas SP ON SP.idPersonas = S.idPersonas WHERE n.idPersonas = ?;"); // Tu consulta SQL aquí
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

    public List<Negociaciones> CargarPedidos(int idPersona) throws SQLException {
        List<Negociaciones> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT n.idNegociacion, CASE WHEN n.idProducto IS NOT NULL THEN p.FotoProducto WHEN n.idServicio IS NOT NULL THEN s.FotoServicio END AS 'Foto', CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Producto o Servicio', CASE WHEN n.idProducto IS NOT NULL THEN PP.Nombres + ' ' + PP.Apellidos WHEN n.idServicio IS NOT NULL THEN SP.Nombres + ' ' + SP.Apellidos END AS Cliente, CASE WHEN n.idProducto IS NOT NULL THEN p.Precio WHEN n.idServicio IS NOT NULL THEN s.Precio END AS 'Precio original', COALESCE(n.PrecioOfrecido, 0) AS 'Precio ofrecido',COALESCE(n.PrecioVenta, 0) AS 'Precio de venta' FROM tbNegociaciones n LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio LEFT JOIN TbPersonas PP ON PP.idPersonas = n.idPersonas LEFT JOIN TbPersonas SP ON SP.idPersonas = n.idPersonas WHERE (p.idPersonas = ? OR s.idPersonas = ?) AND n.idEstadoNegociacion = 1;"); // Tu consulta SQL aquí
            ps.setInt(1, idPersona);
            ps.setInt(2, idPersona);
            ResultSet respuesta = ps.executeQuery();

            while (respuesta.next()) {
                Negociaciones Neg = new Negociaciones();
                Neg.setIdNegociacion(respuesta.getInt("idNegociacion"));
                Neg.setFoto(respuesta.getBytes("Foto"));
                Neg.setNombre(respuesta.getString("Producto o Servicio"));
                Neg.setPrecioOriginal(respuesta.getDouble("Precio original"));
                Neg.setPrecioOfrecido(respuesta.getDouble("Precio ofrecido"));
                Neg.setPrecioVenta(respuesta.getDouble("Precio de venta"));
                Neg.setVendedor(respuesta.getString("Cliente"));
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
