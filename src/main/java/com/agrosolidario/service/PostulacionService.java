package com.agrosolidario.service;

import com.agrosolidario.dao.OfertaTrabajoDao;
import com.agrosolidario.dao.PersonaDesplazadaDao;
import com.agrosolidario.dao.SolicitudTrabajoDao;
import com.agrosolidario.model.OfertaTrabajo;
import com.agrosolidario.model.PersonaDesplazada;

import java.time.LocalDate;
import java.util.List;

public class PostulacionService {

    private final OfertaTrabajoDao ofertaDao;
    private final SolicitudTrabajoDao solicitudDao;
    private final PersonaDesplazadaDao personaDao;

    public PostulacionService() {
        this.ofertaDao = new OfertaTrabajoDao();
        this.solicitudDao = new SolicitudTrabajoDao();
        this.personaDao = new PersonaDesplazadaDao();
    }

    public List<OfertaTrabajo> listarOfertasActivas() {
        return ofertaDao.listarActivas();
    }

    public void solicitarEmpleo(int ofertaId, int personaId) {
        if (ofertaId <= 0 || personaId <= 0) {
            throw new IllegalArgumentException("Oferta o persona inválida");
        }

        PersonaDesplazada persona = personaDao.buscarPorId(personaId);
        if (persona == null) {
            throw new IllegalArgumentException("La persona no existe");
        }

        OfertaTrabajo oferta = ofertaDao.buscarPorId(ofertaId);
        if (oferta == null) {
            throw new IllegalArgumentException("La oferta no existe");
        }

        if (!"ACTIVA".equalsIgnoreCase(oferta.getEstado())) {
            throw new IllegalStateException("La oferta no está activa");
        }

        if (oferta.getFechaLimite() != null && oferta.getFechaLimite().isBefore(LocalDate.now())) {
            throw new IllegalStateException("La oferta ya superó su fecha límite");
        }

        if (solicitudDao.existeSolicitud(ofertaId, personaId)) {
            throw new IllegalArgumentException("Ya enviaste una solicitud para esta oferta");
        }

        solicitudDao.crearSolicitud(ofertaId, personaId);
    }
}
