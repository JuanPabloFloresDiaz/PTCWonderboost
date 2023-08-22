package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Servicio {
    private int id;

    private String servicio;

    private float precioser;

    private String descripcionser;

    private int idcategoria;
    private int TipoPrecio;
    private int idPersonas;


    public Servicio(String servicio, float precioser, String descripcionser, int idcategoria, int TipoPrecio, int idPersonas){
        // Inicializa los campos
    }

    public Servicio() {

    }
    //Metodo spinner servicio
    public ResultSet CargarCategoriasSer() {
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT * FROM tbServicios");
            ResultSet respuesta=ps.executeQuery();
            return respuesta;
        } catch (Exception e) {
            return null;
        }



    }
    // metodo spinner tipoPrecio
    public ResultSet CargarTipoPrecioSer() {
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT * FROM tbServicios");
            ResultSet respuesta2=ps.executeQuery();
            return respuesta2;
        } catch (Exception e) {
            return null;
        }



    }

    public ResultSet ingresarServicios() {

        String url = "jdbc:sqlserver://<servidor>:<>;databaseName=<bdPrototipoPTC>;user=<sa>;password=<itr2023 >";

        try (Connection connection = DriverManager.getConnection(url)) {
            String insertQuery = "INSERT INTO tbServicios (Servicio, Precio, Descripcion, idCategoria, idTipoPrecio) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, servicio);
            preparedStatement.setFloat(2, precioser);
            preparedStatement.setString(3, descripcionser);
            preparedStatement.setInt(4, idcategoria);
            preparedStatement.setInt(5, TipoPrecio);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }


        return null;
    }
    public void eliminarServicio() {
        String url1 = "jdbc:sqlserver://<servidor>:<>;databaseName=<bdPrototipoPTC>;user=<sa>;password=<itr2023 >";

        try (Connection connection = DriverManager.getConnection(url1)) {
            String deleteQuery = "DELETE FROM tbServicios WHERE Servicio = ? AND Precio = ? AND Descripcion = ? AND idCategoria = ? AND idTipoPrecio = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, servicio);
            preparedStatement.setDouble(2, precioser);
            preparedStatement.setString(3, descripcionser);
            preparedStatement.setInt(4, idcategoria);
            preparedStatement.setInt(5, TipoPrecio);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Se eliminaron " + rowsDeleted + " filas de la tabla tbServicios.");
            } else {
                System.out.println("No se eliminaron filas de la tabla tbServicios.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar los datos de la tabla tbServicios: " + e.getMessage());
        }
    }

    public void actualizarDatos(){

        String url1 = "jdbc:sqlserver://<servidor>:<>;databaseName=<bdPrototipoPTC>;user=<sa>;password=<itr2023 >";

        try(Connection connection = DriverManager.getConnection(url1)){
            String query = "UPDATE tbServicios SET Servicio=?, Precio=?, Descripcion=?, idCategoria=?, idTipoPrecio=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.servicio);
            preparedStatement.setDouble(2, this.precioser);
            preparedStatement.setString(3, this.descripcionser);
            preparedStatement.setInt(4, this.idcategoria);
            preparedStatement.setInt(5, this.TipoPrecio);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();

        }




    }


}

