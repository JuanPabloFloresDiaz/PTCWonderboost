package com.example.ptcwonderboost;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class Validaciones {

    public static boolean Vacio(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    public static boolean Numeros(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.matches("\\d+");
    }

    public static boolean Letras(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.matches("[a-zA-Z]+");
    }

    public static boolean Precio(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.matches("^\\d+(\\.\\d{1,2})?$");
    }

    public boolean validarFormatos(String dui, String contrasena, String correo) {
        // Validar campos vacíos
        if (TextUtils.isEmpty(dui) || TextUtils.isEmpty(contrasena) || TextUtils.isEmpty(correo)) {
            // mostrar mensaje de error
            return false;
        }

        // Validar formato del DUI
        if (!dui.matches("[0-9]+")) {
            // mostrar mensaje de error
            return false;
        }

        // Agregar guion al DUI
        String duiConGuion = dui.substring(0, 8) + "-" + dui.substring(8);

        // Validar contraseña segura
        if (!contrasena.matches("^(?=.[0-9])(?=.[a-zA-Z])([a-zA-Z0-9]+)$")) {
            // mostrar mensaje de error
            return false;
        }

        // Validar formato del correo electrónico
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            // mostrar mensaje de error
            return false;
        }

        // Si todas las validaciones pasaron, retornar verdadero
        return true;
    }
}
