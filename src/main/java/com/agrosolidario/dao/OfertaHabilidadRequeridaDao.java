package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.OfertaHabilidadRequerida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfertaHabilidadRequeridaDao {

    public void agregarHabilidad(OfertaHabilidadRequerida rel) {
        String sql = """
            INSERT INTO oferta_habilidad_requerida
            (oferta_id, habilidad_id, nivel_min)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rel.getOfertaId());
            ps.setInt(2, rel.getHabilidadId());
            ps.setString(3, rel.getNivelMinimo());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error agregando habilidad a oferta", e);
        }
    }

    public List<OfertaHabilidadRequerida> listarPorOferta(int ofertaId) {
        List<OfertaHabilidadRequerida> lista = new ArrayList<>();
        String sql = "SELECT * FROM oferta_habilidad_requerida WHERE oferta_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ofertaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OfertaHabilidadRequerida rel = new OfertaHabilidadRequerida();
                rel.setId(rs.getInt("id"));
                rel.setOfertaId(rs.getInt("oferta_id"));
                rel.setHabilidadId(rs.getInt("habilidad_id"));
                rel.setNivelMinimo(rs.getString("nivel_min"));
                lista.add(rel);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando habilidades de oferta", e);
        }
        return lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM oferta_habilidad_requerida WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando habilidad de oferta", e);
        }
    }
}



