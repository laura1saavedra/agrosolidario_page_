package com.agrosolidario.model;

import java.time.LocalDateTime;

public class Documento {

    private int idDocumento;
    private String tipo;      // IDENTIDAD, PROPIEDAD, FINCA, CERTIFICADO
    private String url;
    private String estado;    
    private LocalDateTime fechaSubida;

    private Integer personaId;
    private Integer fincaId;
    private Integer duenoId;

    public Documento() {}

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public Integer getFincaId() {
        return fincaId;
    }

    public void setFincaId(Integer fincaId) {
        this.fincaId = fincaId;
    }

    public Integer getDuenoId() {
        return duenoId;
    }

    public void setDuenoId(Integer duenoId) {
        this.duenoId = duenoId;
    }
}
