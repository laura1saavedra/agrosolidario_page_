package com.agrosolidario.model;

import java.time.LocalDateTime;

public class Usuario {

    private int idUsuario;
    private String email;
    private String passwordHash;
    private String rol;
    private LocalDateTime fechaRegistro;
    private String estado;

    public Usuario() {
    }


    public Usuario(String email, String passwordHash, String rol, String estado) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.estado = estado;
    }


    public Usuario(int idUsuario, String email, String passwordHash, String rol,
                   LocalDateTime fechaRegistro, String estado) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

