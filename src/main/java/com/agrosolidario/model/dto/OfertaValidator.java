package com.agrosolidario.model.dto;

import com.agrosolidario.model.OfertaTrabajo;

import java.time.LocalDate;

public class OfertaValidator {

    public static void validarCamposObligatorios(OfertaTrabajo oferta) {

        if (oferta.getTitulo() == null || oferta.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la oferta es obligatorio.");
        }

        if (oferta.getDescripcion() == null || oferta.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la oferta es obligatoria.");
        }

        if (oferta.getSalario() <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor a cero.");
        }

        if (oferta.getVacantes() <= 0) {
            throw new IllegalArgumentException("Debe indicar al menos una vacante.");
        }
    }

    public static void validarFechas(OfertaTrabajo oferta) {

        LocalDate hoy = LocalDate.now();

        if (oferta.getFechaInicio() == null ||
            oferta.getFechaFin() == null ||
            oferta.getFechaLimite() == null) {

            throw new IllegalArgumentException("Todas las fechas son obligatorias.");
        }

        if (oferta.getFechaInicio().isBefore(hoy)) {
            throw new IllegalArgumentException(
                "La fecha de inicio no puede ser anterior a la fecha actual."
            );
        }

        if (oferta.getFechaFin().isBefore(oferta.getFechaInicio())) {
            throw new IllegalArgumentException(
                "La fecha de fin no puede ser anterior a la fecha de inicio."
            );
        }

        if (oferta.getFechaLimite().isAfter(oferta.getFechaInicio())) {
            throw new IllegalArgumentException(
                "La fecha límite debe ser anterior o igual a la fecha de inicio."
            );
        }
    }
}
