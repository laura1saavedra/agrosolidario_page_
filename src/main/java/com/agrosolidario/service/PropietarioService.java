package com.agrosolidario.service;

import com.agrosolidario.dao.DocumentoDao;
import com.agrosolidario.dao.DuenoFincaDao;
import com.agrosolidario.dao.FincaDao;
import com.agrosolidario.model.Documento;
import com.agrosolidario.model.DuenoFinca;
import com.agrosolidario.model.Finca;

import java.util.List;
import java.util.Set;

public class PropietarioService {

    private final DuenoFincaDao duenoDao = new DuenoFincaDao();
    private final FincaDao fincaDao = new FincaDao();
    private final DocumentoDao documentoDao = new DocumentoDao();

    private static final Set<String> TIPOS_DOCUMENTO_VALIDOS =
            Set.of("IDENTIDAD", "PROPIEDAD", "FINCA", "CERTIFICADO");

    private static final Set<String> ESTADOS_DOCUMENTO_VALIDOS =
            Set.of("PENDIENTE", "APROBADO", "RECHAZADO");

    // =====================================================
    // 1) Obtener dueño autenticado
    // =====================================================
    public DuenoFinca obtenerDuenoPorUsuario(int usuarioId) {
        ValidationUtil.validarId(usuarioId, "usuarioId");

        DuenoFinca dueno = duenoDao.buscarPorUsuarioId(usuarioId);

        if (dueno == null) {
            throw new IllegalStateException(
                "El usuario no tiene perfil de dueño."
            );
        }
        return dueno;
    }

    // =====================================================
    // 2) Actualizar datos personales del dueño
    // =====================================================
    public boolean actualizarDatosDueno(DuenoFinca dueno) {

        ValidationUtil.validarId(dueno.getIdDueno(), "idDueno");
        ValidationUtil.validarTexto(dueno.getNombre(), "nombre");
        ValidationUtil.validarTexto(dueno.getContacto(), "contacto");
        ValidationUtil.validarTexto(dueno.getUbicacion(), "ubicación");

        return duenoDao.actualizar(dueno);
    }


    public int crearFinca(Finca finca, int usuarioId) {

        ValidationUtil.validarId(usuarioId, "usuarioId");
        ValidationUtil.validarTexto(finca.getNombreFinca(), "nombre finca");
        ValidationUtil.validarTexto(finca.getUbicacion(), "ubicación");
        ValidationUtil.validarTexto(finca.getTipoProduccion(), "tipo producción");

        // Seguridad: validar que el usuario es dueño
        DuenoFinca dueno = obtenerDuenoPorUsuario(usuarioId);

       
        Finca existente = fincaDao.buscarPorDuenoId(dueno.getIdDueno());
        if (existente != null) {
            throw new IllegalStateException(
                "El dueño ya tiene una finca registrada."
            );
        }

        finca.setDuenoId(dueno.getIdDueno());
        return fincaDao.crear(finca);
    }

    // =====================================================
    // 4) Actualizar finca (SEGURIDAD)
    // =====================================================
    public boolean actualizarFinca(Finca finca, int usuarioId) {

        ValidationUtil.validarId(finca.getIdFinca(), "idFinca");
        ValidationUtil.validarTexto(finca.getNombreFinca(), "nombre finca");
        ValidationUtil.validarTexto(finca.getUbicacion(), "ubicación");
        ValidationUtil.validarTexto(finca.getTipoProduccion(), "tipo producción");

        DuenoFinca dueno = obtenerDuenoPorUsuario(usuarioId);

        // Seguridad: la finca debe pertenecer al dueño
        Finca actual = fincaDao.buscarPorDuenoId(dueno.getIdDueno());
        if (actual == null || actual.getIdFinca() != finca.getIdFinca()) {
            throw new SecurityException(
                "No tiene permisos para modificar esta finca."
            );
        }

        finca.setDuenoId(dueno.getIdDueno());
        return fincaDao.actualizar(finca);
    }


    public boolean eliminarFinca(int fincaId, int usuarioId) {

        ValidationUtil.validarId(fincaId, "idFinca");

        DuenoFinca dueno = obtenerDuenoPorUsuario(usuarioId);

        return fincaDao.eliminar(fincaId, dueno.getIdDueno());
    }

    // =====================================================
    // 6) Registrar documento de finca (VALIDACIONES + ENUM)
    // =====================================================
    public int registrarDocumentoFinca(Documento doc, int usuarioId) {

        ValidationUtil.validarTexto(doc.getTipo(), "tipo documento");
        ValidationUtil.validarTexto(doc.getUrl(), "url documento");

        if (!TIPOS_DOCUMENTO_VALIDOS.contains(doc.getTipo())) {
            throw new IllegalArgumentException(
                "Tipo de documento no válido."
            );
        }

        if (doc.getEstado() == null) {
            doc.setEstado("PENDIENTE");
        }

        if (!ESTADOS_DOCUMENTO_VALIDOS.contains(doc.getEstado())) {
            throw new IllegalArgumentException(
                "Estado de documento no válido."
            );
        }

        DuenoFinca dueno = obtenerDuenoPorUsuario(usuarioId);
        Finca finca = fincaDao.buscarPorDuenoId(dueno.getIdDueno());

        if (finca == null) {
            throw new IllegalStateException(
                "No se puede registrar documento sin finca."
            );
        }

        doc.setFincaId(finca.getIdFinca());
        doc.setDuenoId(dueno.getIdDueno());

        return documentoDao.crear(doc);
    }

    // =====================================================
    // 7) Listar documentos de la finca
    // =====================================================
    public List<Documento> listarDocumentos(int usuarioId) {

        DuenoFinca dueno = obtenerDuenoPorUsuario(usuarioId);
        Finca finca = fincaDao.buscarPorDuenoId(dueno.getIdDueno());

        if (finca == null) {
            throw new IllegalStateException(
                "El dueño no tiene finca registrada."
            );
        }

        return documentoDao.listarPorFinca(finca.getIdFinca());
    }

    public Finca obtenerFinca(int idDueno) {
       ValidationUtil.validarId(idDueno, "idDueno");
        
        return fincaDao.buscarPorDuenoId(idDueno);
    }
}
