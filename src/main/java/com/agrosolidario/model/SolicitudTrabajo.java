package com.agrosolidario.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SolicitudTrabajo {

    private int idSolicitud;
    private int ofertaId;
    private int personaId;


    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaRespuesta;

    private String estado;

    // NO van en la BD, solo para mostrar en el menú
    private String tituloOferta;
    private String nombreFinca;
    private String ubicacionFinca;
    private double salario;

    
    public SolicitudTrabajo() {
    }

   
    public SolicitudTrabajo(int ofertaId, int personaId, String estado) {
        this.ofertaId = ofertaId;
        this.personaId = personaId;
        this.estado = estado;
    }

    public SolicitudTrabajo(int ofertaId, int personaId, LocalDate now, String pendiente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(int ofertaId) {
        this.ofertaId = ofertaId;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTituloOferta() {
        return tituloOferta;
    }

    public void setTituloOferta(String tituloOferta) {
        this.tituloOferta = tituloOferta;
    }

    public String getNombreFinca() {
        return nombreFinca;
    }

    public void setNombreFinca(String nombreFinca) {
        this.nombreFinca = nombreFinca;
    }

    public String getUbicacionFinca() {
        return ubicacionFinca;
    }

    public void setUbicacionFinca(String ubicacionFinca) {
        this.ubicacionFinca = ubicacionFinca;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
