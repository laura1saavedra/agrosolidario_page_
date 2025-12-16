package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaHabilidadDao {

    // ✅ READ (lo que ya tenías)
    public List<String> listarHabilidadesPorPersona(int personaId) {
        List<String> lista = new ArrayList<>();

        String sql = """
            SELECT h.nombre, ph.nivel
            FROM persona_habilidad ph
            JOIN habilidad h ON h.id_habilidad = ph.habilidad_id
            WHERE ph.persona_id = ?
            ORDER BY h.nombre
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String nivel = rs.getString("nivel");
                    lista.add(nombre + " - " + nivel);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando habilidades de la persona", e);
        }

        return lista;
    }

    // ✅ (Nuevo) READ técnico para validar si ya existe una relación persona-habilidad
    public boolean existeRelacion(int personaId, int habilidadId) {
        String sql = """
            SELECT 1
            FROM persona_habilidad
            WHERE persona_id = ? AND habilidad_id = ?
            LIMIT 1
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);
            ps.setInt(2, habilidadId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error validando relación persona-habilidad", e);
        }
    }

    // ✅ CREATE: agregar habilidad a la persona
    public void agregarHabilidad(int personaId, int habilidadId, String nivel) {
        String sql = """
            INSERT INTO persona_habilidad (persona_id, habilidad_id, nivel)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);
            ps.setInt(2, habilidadId);
            ps.setString(3, nivel);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error agregando habilidad a la persona", e);
        }
    }

    // ✅ UPDATE: cambiar nivel de una habilidad (muy recomendado)
    public boolean actualizarNivel(int personaId, int habilidadId, String nuevoNivel) {
        String sql = """
            UPDATE persona_habilidad
            SET nivel = ?
            WHERE persona_id = ? AND habilidad_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoNivel);
            ps.setInt(2, personaId);
            ps.setInt(3, habilidadId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando nivel de habilidad", e);
        }
    }

    // ✅ DELETE: eliminar habilidad asociada a la persona
    public boolean eliminarHabilidad(int personaId, int habilidadId) {
        String sql = """
            DELETE FROM persona_habilidad
            WHERE persona_id = ? AND habilidad_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);
            ps.setInt(2, habilidadId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando habilidad de la persona", e);
        }
    }
}

