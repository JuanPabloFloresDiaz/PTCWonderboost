package com.example.ptcwonderboost;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Pedido {
    public int idNegociacion;
    public BigDecimal precioventa;

    public int getIdNegociacion() {
        return idNegociacion;
    }

    public void setIdNegociacion(int idNegociacion) {
        this.idNegociacion = idNegociacion;
    }

    public BigDecimal getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(BigDecimal precioventa) {
        this.precioventa = precioventa;
    }

    public int AceptarPedido(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con= Conexion.getConnection(null);
            String query = "UPDATE tbNegociaciones SET idEstadoNegociacion = 2, PrecioVenta = ? WHERE idNegociacion = ?;";
            ps = con.prepareStatement(query);
            ps.setBigDecimal(1, precioventa);
            ps.setInt(2, idNegociacion);
            ps.execute();
            return 1;
        }catch(Exception e){
            System.out.println(e);
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

    public int RechazarPedido(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con= Conexion.getConnection(null);
            String query = "UPDATE tbNegociaciones SET idEstadoNegociacion = 3 WHERE idNegociacion = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idNegociacion);
            ps.execute();
            return 1;
        }catch(Exception e){
            System.out.println(e);
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
