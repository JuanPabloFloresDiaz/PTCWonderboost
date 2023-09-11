package com.example.ptcwonderboost;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String IP = "192.168.1.28:62274";
    private static final String DATABASE_NAME = "bdPrototipoPTC";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "jupadi";

    /*private static final String IP = "192.168.42.117:49862";
    private static final String DATABASE_NAME = "bdPrototipoPTC";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "jupadi";*/


    public static Connection getConnection(Context context){
        Connection con = null;
        try{
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ";databaseName="+ DATABASE_NAME+";user="+USERNAME+";password="+PASSWORD);
        }catch (Exception ex){
          showToast(context, ex.getMessage());
        }
        return con;
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

