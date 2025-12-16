package com.agrosolidario.model;

public class OfertaHabilidadRequerida {

    private int id;
    private int ofertaId;
    private int habilidadId;
    private String nivelMinimo;

    public OfertaHabilidadRequerida() {}

    public OfertaHabilidadRequerida(int ofertaId, int habilidadId, String nivelMinimo) {
        this.ofertaId = ofertaId;
        this.habilidadId = habilidadId;
        this.nivelMinimo = nivelMinimo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOfertaId() { return ofertaId; }
    public void setOfertaId(int ofertaId) { this.ofertaId = ofertaId; }

    public int getHabilidadId() { return habilidadId; }
    public void setHabilidadId(int habilidadId) { this.habilidadId = habilidadId; }

    public String getNivelMinimo() { return nivelMinimo; }
    public void setNivelMinimo(String nivelMinimo) { this.nivelMinimo = nivelMinimo; }
}
