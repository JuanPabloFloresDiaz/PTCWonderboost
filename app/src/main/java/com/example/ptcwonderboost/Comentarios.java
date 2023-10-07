package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Comentarios {
    private int idProducto;
    private int calificacion;
    private String opinion;
    private int idPersona;


    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    //Metodo para traer los datos de la base, que seran utilizadas para darle datos a la CARD
    public List<Comment> obtenerComentariosProducto() {
        List<Comment> comentarios = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps;
        try {
            con = Conexion.getConnection(null);
            String query = "SELECT p.Nombres + ' ' + p.Apellidos AS 'Nombre perfil', p.Foto, o.Opinion, o.Calificacion FROM tbCalificacionesyOpiniones o INNER JOIN TbPersonas p ON p.idPersonas = o.idPersonas WHERE idProducto = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String authorName = rs.getString("Nombre perfil");
                byte[] authorImageBytes = rs.getBytes("Foto");
                int rating = rs.getInt("Calificacion");
                String opinion = rs.getString("Opinion");

                Comment comment = new Comment(authorName, authorImageBytes, rating, opinion);
                comentarios.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cierra la conexión y otros recursos si es necesario
        }

        return comentarios;
    }
    //Metodo para cargar los datos de un producto
    public ResultSet MostrarProducto(){
        Connection con;
        PreparedStatement ps;
        try{
            con= Conexion.getConnection(null);
            String query = "SELECT p.idPersonas AS id, p.Producto, p.Precio AS 'Precio original', pe.Nombres + ' ' + pe.Apellidos AS 'Vendedor', p.FotoProducto AS Foto, c.Categoria, p.Cantidad, ep.EstadoProducto AS 'Estado' FROM tbProductos p INNER JOIN TbPersonas pe ON pe.idPersonas = p.idPersonas INNER JOIN tbCategorias c ON c.idCategoria = p.idCategoria INNER JOIN tbEstadosProductos ep ON ep.idEstadoProducto = p.idEstadoProducto WHERE p.idProducto = ?;";
            ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    //Metodo para agregar algún comentario hecho por algún usuario
    public int AgregarCometarioProducto(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);
            String query = "INSERT INTO tbCalificacionesyOpiniones VALUES(?, ?, GETDATE(), ?, ?, NULL);";
            ps = con.prepareStatement(query);
            ps.setInt(1, calificacion);
            ps.setString(2, opinion);
            ps.setInt(3, idPersona);
            ps.setInt(4, idProducto);
            ps.executeUpdate();
            return 1;
        }
        catch(Exception e){
            System.out.println("Error: " + e);
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
