package com.example.ptcwonderboost;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Producto {
    private int id;

   private String nombreProd;

   private float precioProd;

   private String descripcionProd;

    private int cantidadProd;

    private float gastoProduccion;

    private int idcategoria;
    private int EstadoProducto;
    private int TipoPrecio;
    private int idPersonas;



    public ResultSet CargarCategorias() {
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

    private Producto(String nombreProd, float precioProd, String descripcionProd, int cantidadProd, float gastoProduccion, int idcategoria, int EstadoProducto, int  TipoPrecio, int idPersonas){
        // Inicializa los campos
    }


            public void ingresarProductos() {

                String url = "jdbc:sqlserver://<servidor>:<>;databaseName=<bdPrototipoPTC>;user=<sa>;password=<itr2023 >";

                try (Connection connection = DriverManager.getConnection(url)) {
                    String insertQuery = "INSERT INTO TbProductos (Producto, Precio, Descripcion, Cantidad, GastodeProduccion, idCategoria, idEstadoProducto, idTipoPrecio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, nombreProd);
                    preparedStatement.setFloat(2, precioProd);
                    preparedStatement.setString(3, descripcionProd);
                    preparedStatement.setInt(4, cantidadProd);
                    preparedStatement.setFloat(5, gastoProduccion);
                    preparedStatement.setInt(6, idcategoria);
                    preparedStatement.setInt(7, EstadoProducto);
                    preparedStatement.setInt(8, TipoPrecio);

                    preparedStatement.executeUpdate();

                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }



            }
    public void eliminarProductos() {
        String url1 = "jdbc:sqlserver://<servidor>:<>;databaseName=<bdPrototipoPTC>;user=<sa>;password=<itr2023 >";

        try (Connection connection = DriverManager.getConnection(url1)) {
            String deleteQuery = "DELETE FROM TbProductos WHERE Producto = ? AND Precio = ? AND Descripcion = ? AND Cantidad = ? AND GastodeProduccion = ? AND idCategoria = ? AND idEstadoProducto = ? AND idTipoPrecio = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, nombreProd);
            preparedStatement.setDouble(2, precioProd);
            preparedStatement.setString(3, descripcionProd);
            preparedStatement.setInt(4, cantidadProd);
            preparedStatement.setDouble(5, gastoProduccion);
            preparedStatement.setInt(6, idcategoria);
            preparedStatement.setInt(7, EstadoProducto);
            preparedStatement.setInt(8, TipoPrecio);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Se eliminaron " + rowsDeleted + " filas de la tabla tbProductos.");
            } else {
                System.out.println("No se eliminaron filas de la tabla tbProductos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar los datos de la tabla tbProductos: " + e.getMessage());
        }
    }


    public void actualizarDatos(){

        String url1 = "jdbc:sqlserver://<servidor>:<>;databaseName=<bdPrototipoPTC>;user=<sa>;password=<itr2023 >";

        try(Connection connection = DriverManager.getConnection(url1)){
            String query = "UPDATE tbProductos SET Producto=?, Precio=?, Descripcion=?, Cantidad=?, GastodeProduccion=?, idCategoria=?, idEstadoProducto=?, idTipoPrecio=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.nombreProd);
            preparedStatement.setDouble(2, this.precioProd);
            preparedStatement.setString(3, this.descripcionProd);
            preparedStatement.setInt(4, this.cantidadProd);
            preparedStatement.setDouble(5, this.gastoProduccion);
            preparedStatement.setInt(6, this.idcategoria);
            preparedStatement.setInt(7, this.EstadoProducto);
            preparedStatement.setInt(8, this.TipoPrecio);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();

        }

    }
}






