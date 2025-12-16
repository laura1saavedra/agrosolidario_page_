package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.DuenoFinca;

import java.sql.*;

public class DuenoFincaDao {

    public void crearDueno(DuenoFinca dueno) {

        String sql = """
                INSERT INTO dueno_finca (usuario_id, nombre, contacto, ubicacion, foto_perfil_url)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dueno.getUsuarioId());
            ps.setString(2, dueno.getNombre());
            ps.setString(3, dueno.getContacto());
            ps.setString(4, dueno.getUbicacion());
            ps.setString(5, dueno.getFotoPerfilUrl());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creando dueño de finca", e);
        }
    }

    // BUSCAR dueño por usuario
    public DuenoFinca buscarPorUsuarioId(int usuarioId) {

        String sql = "SELECT * FROM dueno_finca WHERE usuario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                DuenoFinca dueno = new DuenoFinca();
                dueno.setIdDueno(rs.getInt("id_dueno"));
                dueno.setUsuarioId(rs.getInt("usuario_id"));
                dueno.setNombre(rs.getString("nombre"));
                dueno.setContacto(rs.getString("contacto"));
                dueno.setUbicacion(rs.getString("ubicacion"));
                dueno.setFotoPerfilUrl(rs.getString("foto_perfil_url"));
                return dueno;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando dueño por usuario", e);
        }
    }

  
    public boolean actualizar(DuenoFinca dueno) {

        String sql = """
                UPDATE dueno_finca
                SET nombre = ?, contacto = ?, ubicacion = ?, foto_perfil_url = ?
                WHERE id_dueno = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dueno.getNombre());
            ps.setString(2, dueno.getContacto());
            ps.setString(3, dueno.getUbicacion());
            ps.setString(4, dueno.getFotoPerfilUrl());
            ps.setInt(5, dueno.getIdDueno());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando dueño", e);
        }
    }
}

