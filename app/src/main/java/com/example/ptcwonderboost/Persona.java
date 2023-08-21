package com.example.ptcwonderboost;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Persona {
    private int id;
    private String nombres;
    private String apellidos;
    private boolean genero;
    private Date nacimiento;
    private String direccion;
    private String telefono;
    private String correo;
    private String dui;
    private String descripcion;
    private int idUsuarios;
    private int idNacionalidad;
    private boolean modoColor;
    private int idIdioma;


    public Persona(String nombres, String apellidos, boolean genero, Date nacimiento, String telefono, String dui, int idUsuarios) {
        // Inicializa los campos
    }


    public void insertarPersona() {
        String url = "jdbc:sqlserver://<servidor>:<>;databaseName=<bdPrototipoPTC>;user=<sa>;password=<itr2023 >";

        try (Connection connection = DriverManager.getConnection(url)) {
            String insertQuery = "INSERT INTO TbPersonas (Nombres, Apellidos, Genero, Nacimiento, Telefono, DUI, idUsuarios) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, nombres);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setBoolean(3, genero);
            preparedStatement.setDate(4, new java.sql.Date(nacimiento.getDate()));
            preparedStatement.setString(5, telefono);
            preparedStatement.setString(6, dui);
            preparedStatement.setInt(7, idUsuarios);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }



}
