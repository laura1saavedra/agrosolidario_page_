package com.agrosolidario.model;

public class DuenoFinca {

    private int idDueno;
    private int usuarioId;
    private String nombre;
    private String contacto;        
    private String ubicacion;       
    private String fotoPerfilUrl;   

    public DuenoFinca() {
    }

    public DuenoFinca(int usuarioId, String nombre, String contacto, String ubicacion) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.contacto = contacto;
        this.ubicacion = ubicacion;
    }

    public int getIdDueno() {
        return idDueno;
    }

    public void setIdDueno(int idDueno) {
        this.idDueno = idDueno;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
}
