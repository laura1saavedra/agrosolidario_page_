package com.agrosolidario.service;

import com.agrosolidario.dao.SolicitudTrabajoDao;
import com.agrosolidario.model.SolicitudTrabajo;
import java.util.List;

public class GestionSolicitudesService {

    private final SolicitudTrabajoDao solicitudDao;

    public GestionSolicitudesService() {
        this.solicitudDao = new SolicitudTrabajoDao();
    }

    public List<SolicitudTrabajo> listarPendientes(int personaId) {
        return solicitudDao.listarPorPersonaYEstado(personaId, "PENDIENTE");
    }

    public List<SolicitudTrabajo> listarHistorial(int personaId) {
        return solicitudDao.listarHistorial(personaId);
    }

    public void aceptarSolicitud(int solicitudId, int personaId) {

        SolicitudTrabajo s = validarSolicitud(solicitudId, personaId);

        if (!"PENDIENTE".equals(s.getEstado())) {
            throw new IllegalStateException("La solicitud ya fue procesada.");
        }

        solicitudDao.actualizarEstado(solicitudId, "ACEPTADA");
    }

    public void rechazarSolicitud(int solicitudId, int personaId) {

        SolicitudTrabajo s = validarSolicitud(solicitudId, personaId);

        if (!"PENDIENTE".equals(s.getEstado())) {
            throw new IllegalStateException("La solicitud ya fue procesada.");
        }

        solicitudDao.actualizarEstado(solicitudId, "RECHAZADA");
    }

    private SolicitudTrabajo validarSolicitud(int solicitudId, int personaId) {

        SolicitudTrabajo s = solicitudDao.buscarPorId(solicitudId);

        if (s == null) {
            throw new IllegalArgumentException("La solicitud no existe.");
        }

        if (s.getPersonaId() != personaId) {
            throw new SecurityException("No puede gestionar solicitudes ajenas.");
        }

        return s;
    }
}
