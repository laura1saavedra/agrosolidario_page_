package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.SolicitudTrabajo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudTrabajoDao {

  
    public boolean existeSolicitud(int ofertaId, int personaId) {
        String sql = "SELECT COUNT(*) FROM solicitud_trabajo WHERE oferta_id = ? AND persona_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ofertaId);
            ps.setInt(2, personaId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error verificando solicitud existente", e);
        }
    }

    
    public int crearSolicitud(int ofertaId, int personaId) {
        String sql = """
            INSERT INTO solicitud_trabajo (oferta_id, persona_id, fecha_envio, estado)
            VALUES (?, ?, NOW(), 'PENDIENTE')
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ofertaId);
            ps.setInt(2, personaId);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            
            throw new RuntimeException("Error creando solicitud", e);
        }

        return -1;
    }

    
    public int crearSolicitud(SolicitudTrabajo solicitud) {
        String sql = """
            INSERT INTO solicitud_trabajo (oferta_id, persona_id, fecha_envio, estado)
            VALUES (?, ?, NOW(), ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, solicitud.getOfertaId());
            ps.setInt(2, solicitud.getPersonaId());
            ps.setString(3, solicitud.getEstado() == null ? "PENDIENTE" : solicitud.getEstado());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creando solicitud desde objeto", e);
        }

        return -1;
    }

    
    public List<SolicitudTrabajo> listarPorOferta(int ofertaId) {
        List<SolicitudTrabajo> lista = new ArrayList<>();

        String sql = """
            SELECT id_solicitud, oferta_id, persona_id, fecha_envio, fecha_respuesta, estado
            FROM solicitud_trabajo
            WHERE oferta_id = ?
            ORDER BY fecha_envio DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ofertaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SolicitudTrabajo s = new SolicitudTrabajo();
                    s.setIdSolicitud(rs.getInt("id_solicitud"));
                    s.setOfertaId(rs.getInt("oferta_id"));
                    s.setPersonaId(rs.getInt("persona_id"));
                    s.setFechaEnvio(rs.getTimestamp("fecha_envio").toLocalDateTime());

                    Timestamp tsResp = rs.getTimestamp("fecha_respuesta");
                    s.setFechaRespuesta(tsResp != null ? tsResp.toLocalDateTime() : null);

                    s.setEstado(rs.getString("estado"));
                    lista.add(s);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando solicitudes por oferta", e);
        }

        return lista;
    }

    
    public SolicitudTrabajo buscarPorId(int idSolicitud) {
        String sql = """
            SELECT id_solicitud, oferta_id, persona_id, fecha_envio, fecha_respuesta, estado
            FROM solicitud_trabajo
            WHERE id_solicitud = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SolicitudTrabajo s = new SolicitudTrabajo();
                    s.setIdSolicitud(rs.getInt("id_solicitud"));
                    s.setOfertaId(rs.getInt("oferta_id"));
                    s.setPersonaId(rs.getInt("persona_id"));
                    s.setFechaEnvio(rs.getTimestamp("fecha_envio").toLocalDateTime());

                    Timestamp tsResp = rs.getTimestamp("fecha_respuesta");
                    s.setFechaRespuesta(tsResp != null ? tsResp.toLocalDateTime() : null);

                    s.setEstado(rs.getString("estado"));
                    return s;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando solicitud por id", e);
        }

        return null;
    }

    
    public List<SolicitudTrabajo> listarPorPersonaYEstado(int personaId, String estado) {

        List<SolicitudTrabajo> lista = new ArrayList<>();

        String sql = """
            SELECT s.id_solicitud, s.oferta_id, s.persona_id, s.fecha_envio, s.fecha_respuesta, s.estado,
                   o.titulo, o.salario,
                   f.nombre_finca, f.ubicacion
            FROM solicitud_trabajo s
            JOIN oferta_trabajo o ON s.oferta_id = o.id_oferta
            JOIN finca f ON o.finca_id = f.id_finca
            WHERE s.persona_id = ? AND s.estado = ?
            ORDER BY s.fecha_envio DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);
            ps.setString(2, estado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SolicitudTrabajo s = new SolicitudTrabajo();
                    s.setIdSolicitud(rs.getInt("id_solicitud"));
                    s.setOfertaId(rs.getInt("oferta_id"));
                    s.setPersonaId(rs.getInt("persona_id"));
                    s.setFechaEnvio(rs.getTimestamp("fecha_envio").toLocalDateTime());

                    Timestamp tsResp = rs.getTimestamp("fecha_respuesta");
                    s.setFechaRespuesta(tsResp != null ? tsResp.toLocalDateTime() : null);

                    s.setEstado(rs.getString("estado"));

                    // Campos auxiliares para el menú (JOIN)
                    s.setTituloOferta(rs.getString("titulo"));
                    s.setNombreFinca(rs.getString("nombre_finca"));
                    s.setUbicacionFinca(rs.getString("ubicacion"));
                    s.setSalario(rs.getDouble("salario"));

                    lista.add(s);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando solicitudes por persona y estado", e);
        }

        return lista;
    }

    
    public List<SolicitudTrabajo> listarHistorial(int personaId) {

        List<SolicitudTrabajo> lista = new ArrayList<>();

        String sql = """
            SELECT s.id_solicitud, s.oferta_id, s.persona_id, s.fecha_envio, s.fecha_respuesta, s.estado,
                   o.titulo, o.salario,
                   f.nombre_finca, f.ubicacion
            FROM solicitud_trabajo s
            JOIN oferta_trabajo o ON s.oferta_id = o.id_oferta
            JOIN finca f ON o.finca_id = f.id_finca
            WHERE s.persona_id = ? AND s.estado IN ('ACEPTADA','RECHAZADA')
            ORDER BY s.fecha_respuesta DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SolicitudTrabajo s = new SolicitudTrabajo();
                    s.setIdSolicitud(rs.getInt("id_solicitud"));
                    s.setOfertaId(rs.getInt("oferta_id"));
                    s.setPersonaId(rs.getInt("persona_id"));
                    s.setFechaEnvio(rs.getTimestamp("fecha_envio").toLocalDateTime());

                    Timestamp tsResp = rs.getTimestamp("fecha_respuesta");
                    s.setFechaRespuesta(tsResp != null ? tsResp.toLocalDateTime() : null);

                    s.setEstado(rs.getString("estado"));

                    s.setTituloOferta(rs.getString("titulo"));
                    s.setNombreFinca(rs.getString("nombre_finca"));
                    s.setUbicacionFinca(rs.getString("ubicacion"));
                    s.setSalario(rs.getDouble("salario"));

                    lista.add(s);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando historial de solicitudes", e);
        }

        return lista;
    }

   
    public void actualizarEstado(int idSolicitud, String nuevoEstado) {
        String sql = """
            UPDATE solicitud_trabajo
            SET estado = ?, fecha_respuesta = NOW()
            WHERE id_solicitud = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idSolicitud);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando estado de solicitud", e);
        }
    }
}

