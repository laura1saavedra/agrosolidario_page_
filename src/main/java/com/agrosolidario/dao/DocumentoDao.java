package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.Documento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDao {

    private static final String SQL_INSERT
            = "INSERT INTO documento (tipo, url, estado, finca_id, dueno_id) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_LIST_BY_FINCA
            = "SELECT * FROM documento WHERE finca_id = ?";

    public int crear(Documento doc) {
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, doc.getTipo());
            ps.setString(2, doc.getUrl());
            ps.setString(3, doc.getEstado());
            ps.setInt(4, doc.getFincaId());
            ps.setInt(5, doc.getDuenoId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error creando documento", e);
        }
    }

    public List<Documento> listarPorFinca(int fincaId) {
        List<Documento> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_LIST_BY_FINCA)) {

            ps.setInt(1, fincaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Documento d = new Documento();
                d.setIdDocumento(rs.getInt("id_documento"));
                d.setTipo(rs.getString("tipo"));
                d.setUrl(rs.getString("url"));
                d.setEstado(rs.getString("estado"));
                d.setFechaSubida(rs.getTimestamp("fecha_subida").toLocalDateTime());
                d.setFincaId(rs.getInt("finca_id"));
                d.setDuenoId(rs.getInt("dueno_id"));
                lista.add(d);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error listando documentos", e);
        }
    }

    public List<Documento> listarPorPersona(int personaId) {
        List<Documento> lista = new ArrayList<>();
        String sql = "SELECT * FROM documento WHERE persona_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Documento d = new Documento();
                d.setIdDocumento(rs.getInt("id_documento"));
                d.setTipo(rs.getString("tipo"));
                d.setUrl(rs.getString("url"));
                d.setEstado(rs.getString("estado"));
                d.setFechaSubida(rs.getTimestamp("fecha_subida").toLocalDateTime());
                d.setPersonaId(rs.getInt("persona_id"));
                lista.add(d);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando documentos de persona", e);
        }
        return lista;
    }

}
