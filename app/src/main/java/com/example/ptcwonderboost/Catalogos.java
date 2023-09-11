package com.example.ptcwonderboost;
import android.content.Context;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
public class Catalogos {


//    public ResultSet CargarCatalogos(){
//        PreparedStatement ps;
//        Connection con;
//        try {
//            con = Conexion.getConnection(null);
//            ps = con.prepareStatement("SELECT p.idProducto, p.FotoProducto, p.Producto, p.Precio,ep.EstadoProducto, tp.TipoPrecio, p.Cantidad, pe.Nombres + ' ' + pe.Apellidos AS 'Vendedor' FROM tbProductos p INNER JOIN tbEstadosProductos ep ON ep.idEstadoProducto = p.idEstadoProducto INNER JOIN tbTiposPrecio tp ON tp.idTipoPrecio = p.idTipoPrecio INNER JOIN tbPersonas pe ON pe.idPersonas = p.idPersonas");
//            ResultSet respuesta=ps.executeQuery();
//            return respuesta;
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
