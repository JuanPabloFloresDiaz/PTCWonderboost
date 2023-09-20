package com.example.ptcwonderboost;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class Validaciones {

    // Método para validar si un campo de texto está vacío.
    public static boolean Vacio(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    // Método para validar si un campo de texto contiene solo números.
    public static boolean Numeros(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.matches("\\d+");
    }

    // Método para validar si un campo de texto contiene solo letras.
    public static boolean Letras(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.matches("[\\p{L}\\s]+");
    }

    // Método para validar si un campo de texto contiene un precio válido con máximo 2 decimales.
    public static boolean Precio(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.matches("^\\d+(\\.\\d{1,2})?$");
    }

    public static boolean ValidarContrasena(String contrasena) {
        // Validar contraseña segura (al menos una letra y un número)
        return contrasena.matches("^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$");
    }

    public static boolean ValidarTelefono(String telefono) {
        // Validar contraseña segura (al menos una letra y un número)
        return telefono.matches("\\+\\d{1,3} \\d{4}-\\d{4}");
    }

    public static boolean ValidarDUI(String dui) {
        // Validar formato del DUI (8 numeros un - y el ultimo numero)
        return dui.matches("^\\d{8}-\\d$");
    }
    public static boolean ValidarCorreo(String correo) {
        // Validar formato del correo electrónico utilizando Patterns.EMAIL_ADDRESS
        return Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }
}
