package com.example.ptcwonderboost;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Correo {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResultSet NotificarCorreo(){
        PreparedStatement ps;
        Connection con;
        try{
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT p.Correo, u.Usuario FROM TbPersonas p INNER JOIN TbUsuarios u ON u.idUsuarios = p.idUsuarios WHERE p.idPersonas = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception ex){
            return null;
        }
    }

    public ResultSet CapturaIDdeNegociacion(){
        PreparedStatement ps;
        Connection con;
        try{
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT n.idPersonas, CASE WHEN n.idProducto IS NOT NULL THEN p.Producto WHEN n.idServicio IS NOT NULL THEN s.Servicio END AS 'Producto o Servicio', COALESCE(n.PrecioVenta, 0) AS 'Precio de venta' FROM tbNegociaciones n LEFT JOIN tbProductos p ON p.idProducto = n.idProducto LEFT JOIN tbServicios s ON s.idServicio = n.idServicio WHERE idNegociacion = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception ex){
            return null;
        }
    }

    public ResultSet CapturaIDEnvio(){
        PreparedStatement ps;
        Connection con;
        try{
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT idNegociacion FROM tbEnvios WHERE idEnvio = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception ex){
            return null;
        }
    }
}
