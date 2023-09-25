package com.example.ptcwonderboost;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Pagos {
    private int idVendedor;

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    //Cargar tabla de seguimiento de pagos
    public ResultSet cargarTablaPagos(){
        Connection con;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT e.idEnvio, n.idNegociacion, CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Nombre', ee.estadoEnvio AS 'Estado de envio', CASE WHEN n.idProducto IS NOT NULL THEN p.Precio WHEN n.idServicio IS NOT NULL THEN s.Precio END AS 'Precio original', COALESCE(n.PrecioOfrecido, 0) AS 'Precio ofrecido', COALESCE(n.PrecioVenta, 0) AS 'Precio de venta' FROM  tbEnvios e INNER JOIN tbNegociaciones n ON e.idNegociacion = n.idNegociacion LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio INNER JOIN tbEstadosEnvio ee ON e.idEstadoEnvio = ee.idEstadoEnvio WHERE (p.idPersonas = ? OR s.idPersonas = ?) AND e.idEstadoEnvio = 3;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idVendedor);
            ps.setInt(2, idVendedor);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            System.out.println(e);
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
}
