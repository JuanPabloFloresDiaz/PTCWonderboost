package com.example.ptcwonderboost;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encriptacion {
    //Metodo para encriptar contraseña con SHA-256
    public String encriptarContrasenaSHA256(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}