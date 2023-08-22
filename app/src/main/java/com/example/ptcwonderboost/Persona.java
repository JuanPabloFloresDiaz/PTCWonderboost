package com.example.ptcwonderboost;

import java.sql.*;

public class Persona {
    private int id;
    private String nombres;
    private String apellidos;
    private byte genero;
    private String nacimiento;
    private String direccion;
    private String telefono;
    private String correo;
    private String dui;
    private byte[] foto;
    private String descripcion;
    private int idUsuarios;
    private int idNacionalidad;
    private byte modoColor;
    private int idIdioma;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public byte getGenero() {
        return genero;
    }

    public void setGenero(byte genero) {
        this.genero = genero;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public int getIdNacionalidad() {
        return idNacionalidad;
    }

    public void setIdNacionalidad(int idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public byte isModoColor() {
        return modoColor;
    }

    public void setModoColor(byte modoColor) {
        this.modoColor = modoColor;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }

    public int RegistrarPersona(){
        Connection con = null;
        PreparedStatement ps;
        try{
            con = Conexion.getConnection(null);

            String query = "INSERT INTO TbPersonas(Nombres, Apellidos, Genero, Nacimiento, Direccion, Telefono, Correo, DUI, Descripcion, idUsuarios, idNacionalidad, ModoColor, idIdioma) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setByte(3, genero);
            ps.setString(4, nacimiento);
            ps.setString(5, direccion);
            ps.setString(6,telefono);
            ps.setString(7, correo);
            ps.setString(8, dui);
            ps.setString(9, descripcion);
            ps.setInt(10, idUsuarios);
            ps.setInt(11,idNacionalidad);
            ps.setByte(12, modoColor);
            ps.setInt(13, idIdioma);
            ps.executeUpdate();

            return 1;
        }
        catch(Exception ex){
            return 0;
        }
    }

    public ResultSet CargarNacionalidad(){
        PreparedStatement ps;
        Connection con;
        try {
            con = Conexion.getConnection(null);
            ps = con.prepareStatement("SELECT * FROM TbNacionalidades");
            ResultSet respuesta=ps.executeQuery();
            return respuesta;
        } catch (Exception e) {
            return null;
        }
    }

}
