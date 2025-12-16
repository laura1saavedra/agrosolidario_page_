package com.agrosolidario.model.dto;

import com.agrosolidario.model.PersonaDesplazada;
import java.util.ArrayList;
import java.util.List;

public class PersonaPerfilDto {

    private PersonaDesplazada persona;
    private List<String> habilidades; // formato: "Nombre - Nivel"

    public PersonaPerfilDto() {
        this.habilidades = new ArrayList<>();
    }

    public PersonaPerfilDto(PersonaDesplazada persona, List<String> habilidades) {
        this.persona = persona;
        this.habilidades = habilidades;
    }

    public PersonaDesplazada getPersona() { return persona; }
    public void setPersona(PersonaDesplazada persona) { this.persona = persona; }

    public List<String> getHabilidades() { return habilidades; }
    public void setHabilidades(List<String> habilidades) { this.habilidades = habilidades; }
}
