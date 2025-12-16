package com.agrosolidario.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PersonaDesplazada {

    private int idPersona;
    private int usuarioId;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String ubicacion;
    private String telefono;
    private int aniosExperiencia;
    private LocalDateTime fechaRegistro;

    public PersonaDesplazada() {
    }

    public PersonaDesplazada(int usuarioId, String nombre, LocalDate fechaNacimiento,
                             String ubicacion, String telefono, int aniosExperiencia) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.ubicacion = ubicacion;
        this.telefono = telefono;
        this.aniosExperiencia = aniosExperiencia;
    }

    public PersonaDesplazada(int idPersona, int usuarioId, String nombre,
                             LocalDate fechaNacimiento, String ubicacion,
                             String telefono, int aniosExperiencia,
                             LocalDateTime fechaRegistro) {
        this.idPersona = idPersona;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.ubicacion = ubicacion;
        this.telefono = telefono;
        this.aniosExperiencia = aniosExperiencia;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
