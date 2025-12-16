package com.agrosolidario.model;

import java.time.LocalDate;

public class OfertaTrabajo {

    private int idOferta;
    private int fincaId;
    private String titulo;
    private String descripcion;
    private String requisitos;
    private double salario;
    private LocalDate fechaPublicacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaLimite;
    private int vacantes;
    private String estado;

    public OfertaTrabajo() {}

    public OfertaTrabajo(int fincaId, String titulo, String descripcion, String requisitos,
                         double salario, LocalDate fechaInicio, LocalDate fechaFin,
                         LocalDate fechaLimite, int vacantes, String estado) {
        this.fincaId = fincaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.salario = salario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaLimite = fechaLimite;
        this.vacantes = vacantes;
        this.estado = estado;
    }

  
    public int getIdOferta() { return idOferta; }
    public void setIdOferta(int idOferta) { this.idOferta = idOferta; }

    public int getFincaId() { return fincaId; }
    public void setFincaId(int fincaId) { this.fincaId = fincaId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getRequisitos() { return requisitos; }
    public void setRequisitos(String requisitos) { this.requisitos = requisitos; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }

    public int getVacantes() { return vacantes; }
    public void setVacantes(int vacantes) { this.vacantes = vacantes; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
