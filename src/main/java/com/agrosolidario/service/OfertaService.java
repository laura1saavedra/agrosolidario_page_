package com.agrosolidario.service;

import com.agrosolidario.dao.FincaDao;
import com.agrosolidario.dao.OfertaTrabajoDao;
import com.agrosolidario.model.Finca;
import com.agrosolidario.model.OfertaTrabajo;
import com.agrosolidario.model.dto.OfertaValidator;

import java.time.LocalDate;
import java.util.List;

public class OfertaService {

    private final OfertaTrabajoDao ofertaDao;
    private final FincaDao fincaDao;

    public OfertaService() {
        this.ofertaDao = new OfertaTrabajoDao();
        this.fincaDao = new FincaDao();
    }

    /**
     * @param oferta
     * @param duenoId
     * @return 
     */
    public int publicarOferta(OfertaTrabajo oferta, int duenoId) {

        // 1. Validar que el dueño tenga finca
        Finca finca = fincaDao.buscarPorDuenoId(duenoId);
        if (finca == null) {
            throw new IllegalStateException(
                "No puede publicar ofertas porque no tiene una finca registrada."
            );
        }

        validarOferta(oferta);

        // 3. Forzar datos controlados por el sistema
        oferta.setFincaId(finca.getIdFinca());
        oferta.setEstado("ACTIVA");

        // 4. Guardar oferta
        return ofertaDao.crear(oferta);
    }

    /**
     * @param duenoId
     * @return 
     */
    public List<OfertaTrabajo> listarMisOfertas(int duenoId) {

        Finca finca = fincaDao.buscarPorDuenoId(duenoId);
        if (finca == null) {
            throw new IllegalStateException("El propietario no tiene finca registrada.");
        }

        return ofertaDao.listarPorFincaId(finca.getIdFinca());
    }

    /**
     * @param idOferta
     * @param duenoId
     * @return 
     */
    public OfertaTrabajo obtenerOferta(int idOferta, int duenoId) {

        OfertaTrabajo oferta = ofertaDao.buscarPorId(idOferta);
        if (oferta == null) {
            throw new IllegalArgumentException("La oferta no existe.");
        }

        validarPropiedadOferta(oferta, duenoId);
        return oferta;
    }

    /**
     * @param oferta
     * @param duenoId
     */
    public void actualizarOferta(OfertaTrabajo oferta, int duenoId) {

        // 1. Validar que exista
        OfertaTrabajo actual = ofertaDao.buscarPorId(oferta.getIdOferta());
        if (actual == null) {
            throw new IllegalArgumentException("La oferta no existe.");
        }

        // 2. Validar propiedad
        validarPropiedadOferta(actual, duenoId);

        // 3. Validar estado
        if (!"ACTIVA".equals(actual.getEstado())) {
            throw new IllegalStateException(
                "Solo se pueden modificar ofertas en estado ACTIVA."
            );
        }

        // 4. Validar campos
        validarOferta(oferta);

        // 5. Mantener campos no editables
        oferta.setFincaId(actual.getFincaId());
        oferta.setEstado(actual.getEstado());

        ofertaDao.actualizar(oferta);
    }

    /**
     * Finalizar una oferta (eliminación lógica)
     * @param idOferta
     * @param duenoId
     */
    public void finalizarOferta(int idOferta, int duenoId) {

        OfertaTrabajo oferta = ofertaDao.buscarPorId(idOferta);
        if (oferta == null) {
            throw new IllegalArgumentException("La oferta no existe.");
        }

        validarPropiedadOferta(oferta, duenoId);

        oferta.setEstado("FINALIZADA");
        ofertaDao.actualizar(oferta);
    }

    /**
     * Eliminar una oferta (eliminación física)
     * @param idOferta
     * @param duenoId
     */
    public void eliminarOferta(int idOferta, int duenoId) {

        OfertaTrabajo oferta = ofertaDao.buscarPorId(idOferta);
        if (oferta == null) {
            throw new IllegalArgumentException("La oferta no existe.");
        }

        validarPropiedadOferta(oferta, duenoId);

        ofertaDao.eliminar(idOferta);
    }


    private void validarOferta(OfertaTrabajo oferta) {

        if (oferta.getTitulo() == null || oferta.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }

        if (oferta.getDescripcion() == null || oferta.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }

        if (oferta.getSalario() <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor a cero.");
        }

        if (oferta.getVacantes() <= 0) {
            throw new IllegalArgumentException("Las vacantes deben ser mayores a cero.");
        }

        LocalDate hoy = LocalDate.now();

        if (oferta.getFechaInicio().isBefore(hoy)) {
            throw new IllegalArgumentException(
                "La fecha de inicio no puede ser anterior a hoy."
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

    private void validarPropiedadOferta(OfertaTrabajo oferta, int duenoId) {

        Finca finca = fincaDao.buscarPorDuenoId(duenoId);
        if (finca == null || oferta.getFincaId() != finca.getIdFinca()) {
            throw new SecurityException(
                "No tiene permisos para gestionar esta oferta."
            );
        }
    }
}
