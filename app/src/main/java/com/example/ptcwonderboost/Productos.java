package com.example.ptcwonderboost;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Productos {
    public int idProducto;
    public String producto;
    public BigDecimal Precio;
    public byte[] fotoProducto;
    public String Descripcion;
    public int Cantidad;
    public BigDecimal GastoDeProduccion;
    public int idCategoria;
    public int idEstadoProducto;
    public int idTipoPrecio;
    public int idPersonas;
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigDecimal getPrecio() {
        return Precio;
    }

    public void setPrecio(BigDecimal precio) {
        Precio = precio;
    }

    public byte[] getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(byte[] fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public BigDecimal getGastoDeProduccion() {
        return GastoDeProduccion;
    }

    public void setGastoDeProduccion(BigDecimal gastoDeProduccion) {
        GastoDeProduccion = gastoDeProduccion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdEstadoProducto() {
        return idEstadoProducto;
    }

    public void setIdEstadoProducto(int idEstadoProducto) {
        this.idEstadoProducto = idEstadoProducto;
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

    public ResultSet MostrarDatosProductos(){
        Connection con;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT idProducto, Producto, Precio, FotoProducto, Descripcion, Cantidad, GastodeProduccion, idCategoria, idEstadoProducto, idTipoPrecio FROM tbProductos WHERE idProducto = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception ex){
            return null;
        }
    }

    public ResultSet mostrarNegociacionProducto(){
        Connection con;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT p.Producto, p.Precio AS 'Precio original', pe.Nombres + ' ' + pe.Apellidos AS 'Vendedor', p.Cantidad FROM tbProductos p INNER JOIN TbPersonas pe ON pe.idPersonas = p.idPersonas WHERE p.idProducto = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public ResultSet mostrarProductos(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con= Conexion.getConnection(null);
            String query = "SELECT idProducto, Producto AS 'Nombre del producto', Precio AS 'Precio del producto', Cantidad AS 'Cantidad' FROM tbProductos WHERE idPersonas = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersonas);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            return null;
        }
    }

    public ResultSet CargarCategoria(){
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT * FROM tbCategorias");
            ResultSet respuesta=ps.executeQuery();
            return respuesta;
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet CargarFormaPago(){
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT * FROM tbFormasDePagos");
            ResultSet respuesta=ps.executeQuery();
            return respuesta;
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet CargarTipoPrecio(){
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT * FROM tbTiposPrecio");
            ResultSet respuesta=ps.executeQuery();
            return respuesta;
        } catch (Exception e) {
            return null;
        }
    }
    public int AgregarProducto(){
        PreparedStatement ps;
        Connection con = null;
        try {
            con = Conexion.getConnection(null);
            String query = "INSERT INTO tbProductos(Producto, Precio, FotoProducto, Descripcion, Cantidad, GastodeProduccion, idCategoria, idEstadoProducto, idTipoPrecio, idPersonas) VALUES (?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, producto);
            ps.setBigDecimal(2, Precio);
            ps.setBytes(3, fotoProducto);
            ps.setString(4, Descripcion);
            ps.setInt(5, Cantidad);
            ps.setBigDecimal(6, GastoDeProduccion);
            ps.setInt(7, idCategoria);
            ps.setInt(8, idEstadoProducto);
            ps.setInt(9, idTipoPrecio);
            ps.setInt(10, idPersonas);
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

    public int EditarProductoImagen(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "UPDATE tbProductos SET Producto= ?, Precio = ?, FotoProducto = ?, Descripcion = ?, Cantidad = ?, GastodeProduccion = ?, idCategoria = ?, idEstadoProducto = ?, idTipoPrecio = ?, idPersonas = ? WHERE idProducto = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, producto);
            ps.setBigDecimal(2, Precio);
            ps.setBytes(3, fotoProducto);
            ps.setString(4, Descripcion);
            ps.setInt(5, Cantidad);
            ps.setBigDecimal(6, GastoDeProduccion);
            ps.setInt(7, idCategoria);
            ps.setInt(8, idEstadoProducto);
            ps.setInt(9, idTipoPrecio);
            ps.setInt(10, idPersonas);
            ps.setInt(11, idProducto);
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

    public int EliminarProducto(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "DELETE tbProductos WHERE idProducto = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
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
