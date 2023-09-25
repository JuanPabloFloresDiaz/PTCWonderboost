package com.example.ptcwonderboost;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Producto {
    private int idPersona;
    private int idProducto;
    private byte[] fotoProducto;
    private String producto;
    private double precio;
    private double precioUnitario;
    private String estadoProducto;
    private String tipoPrecio;
    private int cantidad;
    private String vendedor;

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public byte[] getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(byte[] fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(String estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public String getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(String tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public List<Producto> CargarCatalogos() throws SQLException{
        List<Producto> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT p.idProducto, p.FotoProducto, p.Producto, p.Precio,ep.EstadoProducto, tp.TipoPrecio, p.Cantidad, pe.Nombres + ' ' + pe.Apellidos AS 'Vendedor' FROM tbProductos p INNER JOIN tbEstadosProductos ep ON ep.idEstadoProducto = p.idEstadoProducto INNER JOIN tbTiposPrecio tp ON tp.idTipoPrecio = p.idTipoPrecio INNER JOIN tbPersonas pe ON pe.idPersonas = p.idPersonas"); // Tu consulta SQL aquí
            ResultSet respuesta = ps.executeQuery();

            while (respuesta.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(respuesta.getInt("idProducto"));
                producto.setFotoProducto(respuesta.getBytes("FotoProducto"));
                producto.setProducto(respuesta.getString("Producto"));
                producto.setPrecio(respuesta.getDouble("Precio"));
                producto.setEstadoProducto(respuesta.getString("EstadoProducto"));
                producto.setTipoPrecio(respuesta.getString("TipoPrecio"));
                producto.setCantidad(respuesta.getInt("Cantidad"));
                producto.setVendedor(respuesta.getString("Vendedor"));
                productos.add(producto);
            }
            return productos;
        }catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    //Metodo agregar producto al carrito
    public int AgregarAlCarritoProducto(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "INSERT INTO tbCarrito (idPersonas, idProducto, PrecioUnitario) VALUES (?, ?, ?);";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersona);
            ps.setInt(2, idProducto);
            ps.setDouble(3, precioUnitario);
            ps.executeUpdate();
            return 1;
        }catch(Exception ex){
            System.out.println("Error: " + ex);
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

    public List<Producto> CargarInventario() throws SQLException{
        List<Producto> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT p.idProducto, p.FotoProducto, p.Producto, p.Precio,ep.EstadoProducto, tp.TipoPrecio, p.Cantidad, pe.Nombres + ' ' + pe.Apellidos AS 'Vendedor' FROM tbProductos p INNER JOIN tbEstadosProductos ep ON ep.idEstadoProducto = p.idEstadoProducto INNER JOIN tbTiposPrecio tp ON tp.idTipoPrecio = p.idTipoPrecio INNER JOIN tbPersonas pe ON pe.idPersonas = p.idPersonas WHERE p.idPersonas = ?"); // Tu consulta SQL aquí
            ps.setInt(1, idPersona);
            ResultSet respuesta = ps.executeQuery();

            while (respuesta.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(respuesta.getInt("idProducto"));
                producto.setFotoProducto(respuesta.getBytes("FotoProducto"));
                producto.setProducto(respuesta.getString("Producto"));
                producto.setPrecio(respuesta.getDouble("Precio"));
                producto.setEstadoProducto(respuesta.getString("EstadoProducto"));
                producto.setTipoPrecio(respuesta.getString("TipoPrecio"));
                producto.setCantidad(respuesta.getInt("Cantidad"));
                producto.setVendedor(respuesta.getString("Vendedor"));
                productos.add(producto);
            }
            return productos;
        }catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public List<Producto> BuscarCatalogos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT p.idProducto, p.FotoProducto, p.Producto, p.Precio,ep.EstadoProducto, tp.TipoPrecio, p.Cantidad, pe.Nombres + ' ' + pe.Apellidos AS 'Vendedor' FROM tbProductos p INNER JOIN tbEstadosProductos ep ON ep.idEstadoProducto = p.idEstadoProducto INNER JOIN tbTiposPrecio tp ON tp.idTipoPrecio = p.idTipoPrecio INNER JOIN tbPersonas pe ON pe.idPersonas = p.idPersonas WHERE p.Producto LIKE ?"); // Tu consulta SQL aquí
            ps.setString(1, "%" + producto + "%");
            ResultSet respuesta = ps.executeQuery();

            while (respuesta.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(respuesta.getInt("idProducto"));
                producto.setFotoProducto(respuesta.getBytes("FotoProducto"));
                producto.setProducto(respuesta.getString("Producto"));
                producto.setPrecio(respuesta.getDouble("Precio"));
                producto.setEstadoProducto(respuesta.getString("EstadoProducto"));
                producto.setTipoPrecio(respuesta.getString("TipoPrecio"));
                producto.setCantidad(respuesta.getInt("Cantidad"));
                producto.setVendedor(respuesta.getString("Vendedor"));
                productos.add(producto);
            }
            return productos;
        } catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
