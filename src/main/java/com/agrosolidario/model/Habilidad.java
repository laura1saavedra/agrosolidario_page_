package com.agrosolidario.model;

public class Habilidad {

    private int idHabilidad;
    private String nombre;

    public Habilidad(int idHabilidad, String nombre) {
        this.idHabilidad = idHabilidad;
        this.nombre = nombre;
    }

    public int getIdHabilidad() { return idHabilidad; }
    public void setIdHabilidad(int idHabilidad) { this.idHabilidad = idHabilidad; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
