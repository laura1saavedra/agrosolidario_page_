package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.PersonaDesplazada;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersonaDesplazadaDao {

    
    public void crearPersona(PersonaDesplazada persona) {
        String sql = """
            INSERT INTO persona_desplazada
            (usuario_id, nombre, fecha_nacimiento, ubicacion, telefono, anios_experiencia)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, persona.getUsuarioId());
            ps.setString(2, persona.getNombre());
            ps.setDate(3, Date.valueOf(persona.getFechaNacimiento()));
            ps.setString(4, persona.getUbicacion());
            ps.setString(5, persona.getTelefono());
            ps.setInt(6, persona.getAniosExperiencia());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creando persona desplazada", e);
        }
    }

   
    public boolean actualizarPerfil(PersonaDesplazada persona) {
        String sql = """
            UPDATE persona_desplazada
            SET nombre = ?, fecha_nacimiento = ?, ubicacion = ?, telefono = ?, anios_experiencia = ?
            WHERE id_persona = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, persona.getNombre());
            ps.setDate(2, Date.valueOf(persona.getFechaNacimiento()));
            ps.setString(3, persona.getUbicacion());
            ps.setString(4, persona.getTelefono());
            ps.setInt(5, persona.getAniosExperiencia());
            ps.setInt(6, persona.getIdPersona());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando perfil", e);
        }
    }

    
    public PersonaDesplazada buscarPorId(int idPersona) {
        String sql = """
            SELECT id_persona, usuario_id, nombre, fecha_nacimiento, ubicacion,
                   telefono, anios_experiencia, fecha_registro
            FROM persona_desplazada
            WHERE id_persona = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPersona);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPersona(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando persona por ID", e);
        }
        return null;
    }

        public PersonaDesplazada buscarPorUsuarioId(int usuarioId) {
        String sql = """
            SELECT id_persona, usuario_id, nombre, fecha_nacimiento, ubicacion,
                   telefono, anios_experiencia, fecha_registro
            FROM persona_desplazada
            WHERE usuario_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPersona(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando persona por usuario", e);
        }
        return null;
    }

    private PersonaDesplazada mapearPersona(ResultSet rs) throws SQLException {
        int idPersona = rs.getInt("id_persona");
        int usuarioId = rs.getInt("usuario_id");
        String nombre = rs.getString("nombre");

        LocalDate fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
        String ubicacion = rs.getString("ubicacion");
        String telefono = rs.getString("telefono");
        int aniosExp = rs.getInt("anios_experiencia");

        LocalDateTime fechaRegistro = rs.getTimestamp("fecha_registro").toLocalDateTime();

        return new PersonaDesplazada(
                idPersona, usuarioId, nombre, fechaNacimiento,
                ubicacion, telefono, aniosExp, fechaRegistro
        );
    }

    public List<PersonaDesplazada> buscar(String ubicacion, int minExperiencia) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
