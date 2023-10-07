package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Envio {
    private int idVendedor;
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
    private String UbicacionOrigen;
    private String UbicacionDestino;
    private String fecha;
    private int estado;
    private int idEnvio;

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdNegociacion() {
        return idNegociacion;
    }

    public void setIdNegociacion(int idNegociacion) {
        this.idNegociacion = idNegociacion;
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

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String getUbicacionOrigen() {
        return UbicacionOrigen;
    }

    public void setUbicacionOrigen(String ubicacionOrigen) {
        UbicacionOrigen = ubicacionOrigen;
    }

    public String getUbicacionDestino() {
        return UbicacionDestino;
    }

    public void setUbicacionDestino(String ubicacionDestino) {
        UbicacionDestino = ubicacionDestino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    public int AgregarEnvio(){
        PreparedStatement ps;
        Connection con = null;
        try {
            con = Conexion.getConnection(null);
            String query = "INSERT INTO tbEnvios(idNegociacion, idEstadoEnvio, UbicacionOrigen, UbicacionDestino, FechaEstipulada) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, idNegociacion);
            ps.setInt(2, estado);
            ps.setString(3, UbicacionOrigen);
            ps.setString(4, UbicacionDestino);
            ps.setString(5, fecha);
            ps.executeUpdate();
            return 1;
        }catch (Exception e) {
            System.out.println(""+ e);
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

    public ResultSet CargarEstadoEnvio(){
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT * FROM tbEstadosEnvio");
            ResultSet respuesta=ps.executeQuery();
            return respuesta;
        } catch (Exception e) {
            return null;
        }
    }

    public int ActualizarEnvio(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "UPDATE tbEnvios SET idEstadoEnvio = ?, UbicacionOrigen = ?, UbicacionDestino = ?, FechaEstipulada = ? WHERE idEnvio = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, estado);
            ps.setString(2, UbicacionOrigen);
            ps.setString(3, UbicacionDestino);
            ps.setString(4, fecha);
            ps.setInt(5, idEnvio);
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

    public List<Envio> cargarTablaIngresarEnvios(int idVendedor) throws SQLException {
        List<Envio> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            String query = "SELECT n.idNegociacion, CASE WHEN n.idProducto IS NOT NULL THEN p.FotoProducto WHEN n.idServicio IS NOT NULL THEN s.FotoServicio END AS 'Foto', CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Nombre', CASE WHEN n.idProducto IS NOT NULL THEN p.Precio WHEN n.idServicio IS NOT NULL THEN s.Precio END AS 'Precio original', COALESCE(n.PrecioOfrecido, 0) AS 'Precio ofrecido', COALESCE(n.PrecioVenta, 0) AS 'Precio de venta', CASE WHEN n.idProducto IS NOT NULL THEN PP.Nombres + ' ' + PP.Apellidos WHEN n.idServicio IS NOT NULL THEN SP.Nombres + ' ' + SP.Apellidos END AS Cliente FROM tbNegociaciones n LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio LEFT JOIN TbPersonas PP ON PP.idPersonas = n.idPersonas LEFT JOIN TbPersonas SP ON SP.idPersonas = n.idPersonas WHERE (p.idPersonas = ? OR s.idPersonas = ?) AND n.idEstadoNegociacion = 2;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idVendedor);
            ps.setInt(2, idVendedor);
            ResultSet respuesta = ps.executeQuery();

            while (respuesta.next()) {
                Envio Neg = new Envio();
                Neg.setIdNegociacion(respuesta.getInt("idNegociacion"));
                Neg.setFoto(respuesta.getBytes("Foto"));
                Neg.setNombre(respuesta.getString("Nombre"));
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

    public ResultSet MostrarDatosGestionEnvio(){
        Connection con;
        PreparedStatement ps;
        try{
            con= Conexion.getConnection(null);
            String query = "SELECT n.idNegociacion, CASE WHEN n.idProducto IS NOT NULL THEN p.FotoProducto WHEN n.idServicio IS NOT NULL THEN s.FotoServicio END AS 'Foto', CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Producto o Servicio', CASE WHEN n.idProducto IS NOT NULL THEN PP.Nombres + ' ' + PP.Apellidos WHEN n.idServicio IS NOT NULL THEN SP.Nombres + ' ' + SP.Apellidos END AS Cliente, CASE WHEN n.idProducto IS NOT NULL THEN Pe.Nombres + ' ' + Pe.Apellidos WHEN n.idServicio IS NOT NULL THEN Ps.Nombres + ' ' + ps.Apellidos END AS Vendedor, CASE WHEN n.idProducto IS NOT NULL THEN p.Precio WHEN n.idServicio IS NOT NULL THEN s.Precio END AS 'Precio original', COALESCE(n.PrecioOfrecido, 0) AS 'Precio ofrecido', COALESCE(n.PrecioVenta, 0) AS 'Precio de venta', CASE WHEN n.idProducto IS NOT NULL THEN PC.Categoria WHEN n.idServicio IS NOT NULL THEN SC.Categoria END AS Categoria, e.UbicacionOrigen AS Origen, e.UbicacionDestino AS Destino, e.FechaEstipulada AS Fecha, e.idEstadoEnvio AS idEstado FROM tbEnvios e LEFT JOIN tbNegociaciones n ON e.idNegociacion = n.idNegociacion LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio LEFT JOIN TbPersonas pe ON pe.idPersonas = p.idPersonas LEFT JOIN TbPersonas ps ON ps.idPersonas = n.idPersonas LEFT JOIN TbPersonas PP ON PP.idPersonas = n.idPersonas LEFT JOIN tbCategorias PC ON P.idCategoria = PC.idCategoria LEFT JOIN tbCategorias SC ON S.idCategoria = SC.idCategoria LEFT JOIN TbPersonas SP ON SP.idPersonas = n.idPersonas WHERE e.idEnvio = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idEnvio);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }


    public List<Envio> cargarTablaGestionarEnvios(int idVendedor) throws SQLException {
        List<Envio> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            String query = "SELECT e.idEnvio, n.idNegociacion, CASE WHEN n.idProducto IS NOT NULL THEN p.FotoProducto WHEN n.idServicio IS NOT NULL THEN s.FotoServicio END AS 'Foto', CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Nombre', CASE WHEN n.idProducto IS NOT NULL THEN p.Precio WHEN n.idServicio IS NOT NULL THEN s.Precio END AS 'Precio original', COALESCE(n.PrecioOfrecido, 0) AS 'Precio ofrecido', COALESCE(n.PrecioVenta, 0) AS 'Precio de venta', ee.estadoEnvio AS 'Estado de envío' FROM tbEnvios e INNER JOIN tbNegociaciones n ON e.idNegociacion = n.idNegociacion LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio INNER JOIN tbEstadosEnvio ee ON e.idEstadoEnvio = ee.idEstadoEnvio WHERE (p.idPersonas = ? OR s.idPersonas = ?) AND (e.idEstadoEnvio = 2 OR e.idEstadoEnvio = 1);";
            ps = con.prepareStatement(query);
            ps.setInt(1, idVendedor);
            ps.setInt(2, idVendedor);
            ResultSet respuesta = ps.executeQuery();

            while (respuesta.next()) {
                Envio Neg = new Envio();
                Neg.setIdNegociacion(respuesta.getInt("idEnvio"));
                Neg.setFoto(respuesta.getBytes("Foto"));
                Neg.setNombre(respuesta.getString("Nombre"));
                Neg.setPrecioOriginal(respuesta.getDouble("Precio original"));
                Neg.setPrecioOfrecido(respuesta.getDouble("Precio ofrecido"));
                Neg.setPrecioVenta(respuesta.getDouble("Precio de venta"));
                Neg.setVendedor(respuesta.getString("Estado de envío"));
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

    public List<Envio> cargarTablaHistorialEnvios(int idVendedor) throws SQLException {
        List<Envio> productos = new ArrayList<>();
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            String query = "SELECT e.idEnvio, n.idNegociacion, CASE WHEN n.idProducto IS NOT NULL THEN p.FotoProducto WHEN n.idServicio IS NOT NULL THEN s.FotoServicio END AS 'Foto', CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Nombre', CASE WHEN n.idProducto IS NOT NULL THEN p.Precio WHEN n.idServicio IS NOT NULL THEN s.Precio END AS 'Precio original', COALESCE(n.PrecioOfrecido, 0) AS 'Precio ofrecido', COALESCE(n.PrecioVenta, 0) AS 'Precio de venta', ee.estadoEnvio AS 'Estado de envío' FROM tbEnvios e INNER JOIN tbNegociaciones n ON e.idNegociacion = n.idNegociacion LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio INNER JOIN tbEstadosEnvio ee ON e.idEstadoEnvio = ee.idEstadoEnvio WHERE (p.idPersonas = ? OR s.idPersonas = ?);";
            ps = con.prepareStatement(query);
            ps.setInt(1, idVendedor);
            ps.setInt(2, idVendedor);
            ResultSet respuesta = ps.executeQuery();

            while (respuesta.next()) {
                Envio Neg = new Envio();
                Neg.setIdNegociacion(respuesta.getInt("idEnvio"));
                Neg.setFoto(respuesta.getBytes("Foto"));
                Neg.setNombre(respuesta.getString("Nombre"));
                Neg.setPrecioOriginal(respuesta.getDouble("Precio original"));
                Neg.setPrecioOfrecido(respuesta.getDouble("Precio ofrecido"));
                Neg.setPrecioVenta(respuesta.getDouble("Precio de venta"));
                Neg.setVendedor(respuesta.getString("Estado de envío"));
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
