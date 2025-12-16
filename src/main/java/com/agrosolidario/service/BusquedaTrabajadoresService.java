package com.agrosolidario.service;

import com.agrosolidario.dao.FincaDao;
import com.agrosolidario.dao.OfertaTrabajoDao;
import com.agrosolidario.dao.PersonaDesplazadaDao;
import com.agrosolidario.dao.PersonaHabilidadDao;
import com.agrosolidario.dao.SolicitudTrabajoDao;
import com.agrosolidario.model.Finca;
import com.agrosolidario.model.OfertaTrabajo;
import com.agrosolidario.model.PersonaDesplazada;
import com.agrosolidario.model.SolicitudTrabajo;

import java.time.LocalDate;
import java.util.List;

public class BusquedaTrabajadoresService {

    private final PersonaDesplazadaDao personaDao;
    private final PersonaHabilidadDao personaHabilidadDao;
    private final SolicitudTrabajoDao solicitudDao;
    private final OfertaTrabajoDao ofertaDao;
    private final FincaDao fincaDao;

    public BusquedaTrabajadoresService() {
        this.personaDao = new PersonaDesplazadaDao();
        this.personaHabilidadDao = new PersonaHabilidadDao();
        this.solicitudDao = new SolicitudTrabajoDao();
        this.ofertaDao = new OfertaTrabajoDao();
        this.fincaDao = new FincaDao();
    }

    // ==========================
    // 1) BÚSQUEDA DE TRABAJADORES
    // ==========================

    public List<PersonaDesplazada> buscarTrabajadores(String ubicacion, int minExperiencia) {
        if (minExperiencia < 0) {
            throw new IllegalArgumentException("La experiencia mínima no puede ser negativa.");
        }
        return personaDao.buscar(ubicacion, minExperiencia);
    }

    // ==========================
    // 2) PERFIL DEL TRABAJADOR
    // ==========================

    public PersonaDesplazada verPerfilTrabajador(int personaId) {
        if (personaId <= 0) {
            throw new IllegalArgumentException("ID de persona inválido.");
        }

        PersonaDesplazada persona = personaDao.buscarPorId(personaId);
        if (persona == null) {
            throw new IllegalArgumentException("La persona seleccionada no existe.");
        }
        return persona;
    }

  
    public List<String> verHabilidadesTrabajador(int personaId) {
        if (personaId <= 0) {
            throw new IllegalArgumentException("ID de persona inválido.");
        }
        return personaHabilidadDao.listarHabilidadesPorPersona(personaId);
    }

    // ==========================
    // 3) ENVÍO DE SOLICITUD
    // ==========================

    /**
     * Envía una solicitud SOLO SI:
     * - la oferta existe
     * - la oferta es del dueño autenticado (por finca)
     * - la oferta está ACTIVA
     * - la persona existe
     * - no hay solicitud duplicada
     * @param ofertaId
     * @param personaId
     * @param duenoId
     */
    public void enviarSolicitud(int ofertaId, int personaId, int duenoId) {

        // Validaciones básicas de entrada
        if (duenoId <= 0) throw new IllegalArgumentException("Dueño inválido.");
        if (ofertaId <= 0) throw new IllegalArgumentException("Oferta inválida.");
        if (personaId <= 0) throw new IllegalArgumentException("Persona inválida.");

        // 1) Validar persona existe
        PersonaDesplazada persona = personaDao.buscarPorId(personaId);
        if (persona == null) {
            throw new IllegalArgumentException("No se puede enviar solicitud: la persona no existe.");
        }

        // 2) Validar oferta existe
        OfertaTrabajo oferta = ofertaDao.buscarPorId(ofertaId);
        if (oferta == null) {
            throw new IllegalArgumentException("No se puede enviar solicitud: la oferta no existe.");
        }

        // 3) Validar oferta ACTIVA
        if (!"ACTIVA".equalsIgnoreCase(oferta.getEstado())) {
            throw new IllegalStateException("No se puede enviar solicitud: la oferta no está ACTIVA.");
        }

        // 4) Validar que la oferta pertenezca al dueño autenticado
        //    (comparando finca de la oferta vs finca del dueño)
        Finca fincaDelDueno = fincaDao.buscarPorDuenoId(duenoId);
        if (fincaDelDueno == null) {
            throw new IllegalStateException("No se puede enviar solicitud: el dueño no tiene finca registrada.");
        }

        if (oferta.getFincaId() != fincaDelDueno.getIdFinca()) {
            throw new SecurityException("No tiene permisos: la oferta no pertenece a su finca.");
        }

        // 5) Validar que no exista solicitud duplicada
        if (solicitudDao.existeSolicitud(ofertaId, personaId)) {
            throw new IllegalArgumentException("Ya has enviado una solicitud para esta oferta.");
        }

        // 6) Crear solicitud
        SolicitudTrabajo solicitud = new SolicitudTrabajo(
                ofertaId,
                personaId,
                LocalDate.now(),
                "PENDIENTE"
        );

        solicitudDao.crearSolicitud(solicitud);
    }
}
