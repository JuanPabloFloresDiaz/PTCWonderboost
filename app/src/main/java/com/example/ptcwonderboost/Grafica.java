package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Grafica {
    private int idPersona;

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public ResultSet llenarGraficas(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT TOP 5 pro.idProducto, pro.Producto, SUM(N.CantidadPedida) AS TotalCompras FROM tbSeguimientosPago SP LEFT JOIN tbEnvios E ON SP.idEnvio = E.idEnvio LEFT JOIN tbEstadosPago EP ON SP.idEstadosPago = EP.idEstadoPago LEFT JOIN tbNegociaciones N ON E.idNegociacion = N.idNegociacion LEFT JOIN tbProductos pro ON pro.idProducto = N.idProducto LEFT JOIN tbServicios ser ON ser.idServicio = N.idServicio LEFT JOIN tbPersonas P ON N.idPersonas = P.idPersonas WHERE EP.idEstadoPago = 1 AND N.idProducto IS NOT NULL GROUP BY pro.idProducto, pro.Producto ORDER BY TotalCompras DESC;";
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            return null;
        }
    }

    public ResultSet TotalGanancias(){
        Connection con = null;
        PreparedStatement ps ;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT COALESCE(SUM(N.PrecioVenta), 0) AS Ganancias FROM tbSeguimientosPago SP LEFT JOIN tbEnvios E ON SP.idEnvio = E.idEnvio LEFT JOIN tbEstadosPago EP ON SP.idEstadosPago = EP.idEstadoPago LEFT JOIN tbNegociaciones N ON E.idNegociacion = N.idNegociacion LEFT JOIN tbProductos pro ON pro.idProducto = N.idProducto LEFT JOIN tbServicios ser ON ser.idServicio = N.idServicio LEFT JOIN tbPersonas P ON N.idPersonas = P.idPersonas WHERE EP.idEstadoPago = 1 AND N.idProducto IS NOT NULL AND pro.idPersonas = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersona);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            return null;
        }
    }

    public ResultSet ListaGanancias(){
        Connection con = null;
        PreparedStatement ps ;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT pro.idProducto, pro.Producto, COALESCE(SUM(N.PrecioVenta), 0) AS Ganancias FROM tbSeguimientosPago SP LEFT JOIN tbEnvios E ON SP.idEnvio = E.idEnvio LEFT JOIN tbEstadosPago EP ON SP.idEstadosPago = EP.idEstadoPago LEFT JOIN tbNegociaciones N ON E.idNegociacion = N.idNegociacion LEFT JOIN tbProductos pro ON pro.idProducto = N.idProducto LEFT JOIN tbServicios ser ON ser.idServicio = N.idServicio LEFT JOIN tbPersonas P ON N.idPersonas = P.idPersonas WHERE EP.idEstadoPago = 1 AND N.idProducto IS NOT NULL AND pro.idPersonas = ? GROUP BY pro.idProducto, pro.Producto ORDER BY Ganancias DESC;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersona);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            return null;
        }
    }

    public ResultSet BalanceEconomico(){
        Connection con = null;
        PreparedStatement ps ;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT COALESCE(SUM(N.PrecioVenta), 0) AS Ganancias, COALESCE(SUM(pro.GastodeProduccion), 0) AS Perdidas, (COALESCE(SUM(N.PrecioVenta), 0) - COALESCE(SUM(pro.GastodeProduccion),0)) AS Balance FROM tbSeguimientosPago SP LEFT JOIN tbEnvios E ON SP.idEnvio = E.idEnvio LEFT JOIN tbEstadosPago EP ON SP.idEstadosPago = EP.idEstadoPago LEFT JOIN tbNegociaciones N ON E.idNegociacion = N.idNegociacion LEFT JOIN tbProductos pro ON pro.idProducto = N.idProducto LEFT JOIN tbServicios ser ON ser.idServicio = N.idServicio LEFT JOIN tbPersonas P ON N.idPersonas = P.idPersonas WHERE EP.idEstadoPago = 1 AND N.idProducto IS NOT NULL AND pro.idPersonas = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersona);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            return null;
        }
    }

    public ResultSet TasaEconomica(){
        Connection con = null;
        PreparedStatement ps ;
        try{
            con = Conexion.getConnection(null);
            String query = "SELECT COALESCE(SUM(N.PrecioVenta), 0) AS Ganancias, CASE WHEN SUM(N.PrecioVenta) > 500 THEN SUM(N.PrecioVenta) * 0.05 ELSE 0 END AS Tasa FROM tbSeguimientosPago SP LEFT JOIN tbEnvios E ON SP.idEnvio = E.idEnvio LEFT JOIN tbEstadosPago EP ON SP.idEstadosPago = EP.idEstadoPago LEFT JOIN tbNegociaciones N ON E.idNegociacion = N.idNegociacion LEFT JOIN tbProductos pro ON pro.idProducto = N.idProducto LEFT JOIN tbServicios ser ON ser.idServicio = N.idServicio LEFT JOIN tbPersonas P ON N.idPersonas = P.idPersonas WHERE EP.idEstadoPago = 1 AND N.idProducto IS NOT NULL AND pro.idPersonas = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idPersona);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            return null;
        }
    }

}
