package com.agrosolidario.model;

public class PersonaHabilidad {

    private int id;
    private int personaId;
    private int habilidadId;
    private String nivel;

    public PersonaHabilidad(int personaId, int habilidadId, String nivel) {
        this.personaId = personaId;
        this.habilidadId = habilidadId;
        this.nivel = nivel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPersonaId() { return personaId; }
    public void setPersonaId(int personaId) { this.personaId = personaId; }

    public int getHabilidadId() { return habilidadId; }
    public void setHabilidadId(int habilidadId) { this.habilidadId = habilidadId; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
}
