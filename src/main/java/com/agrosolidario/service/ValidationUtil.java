package com.agrosolidario.service;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    static void validarTexto(String url, String url_documento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    static void validarId(int fincaId, String idFinca) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private ValidationUtil() {}

    public static void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("El correo no tiene un formato válido.");
        }
    }

    public static void validarPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener mínimo 6 caracteres.");
        }
    }

    public static void validarRol(String rol) {
        if (rol == null || rol.isBlank()) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de perfil.");
        }
        if (!rol.equals("DUENO") && !rol.equals("PERSONA")) {
            throw new IllegalArgumentException("Tipo de perfil inválido.");
        }
    }

    public static void validarTextoObligatorio(String valor, String nombreCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio.");
        }
    }

    public static void validarEnteroNoNegativo(int valor, String nombreCampo) {
        if (valor < 0) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede ser negativo.");
        }
    }
}
