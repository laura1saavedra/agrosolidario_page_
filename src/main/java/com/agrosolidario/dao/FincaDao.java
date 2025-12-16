package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.Finca;

import java.sql.*;

public class FincaDao {

    private static final String SQL_FIND_BY_DUENO =
            "SELECT * FROM finca WHERE dueno_id = ?";

    private static final String SQL_INSERT =
            "INSERT INTO finca (dueno_id, nombre_finca, tamano_ha, ubicacion, tipo_produccion, descripcion) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE finca SET nombre_finca=?, tamano_ha=?, ubicacion=?, tipo_produccion=?, descripcion=? " +
            "WHERE id_finca=? AND dueno_id=?";

    private static final String SQL_DELETE =
            "DELETE FROM finca WHERE id_finca=? AND dueno_id=?";

    public Finca buscarPorDuenoId(int duenoId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_DUENO)) {

            ps.setInt(1, duenoId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Finca f = new Finca();
                f.setIdFinca(rs.getInt("id_finca"));
                f.setDuenoId(rs.getInt("dueno_id"));
                f.setNombreFinca(rs.getString("nombre_finca"));
                f.setTamanoHa(rs.getDouble("tamano_ha"));
                f.setUbicacion(rs.getString("ubicacion"));
                f.setTipoProduccion(rs.getString("tipo_produccion"));
                f.setDescripcion(rs.getString("descripcion"));
                f.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return f;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error consultando finca", e);
        }
    }

    public int crear(Finca finca) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, finca.getDuenoId());
            ps.setString(2, finca.getNombreFinca());
            ps.setDouble(3, finca.getTamanoHa());
            ps.setString(4, finca.getUbicacion());
            ps.setString(5, finca.getTipoProduccion());
            ps.setString(6, finca.getDescripcion());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error creando finca", e);
        }
    }

    public boolean actualizar(Finca finca) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, finca.getNombreFinca());
            ps.setDouble(2, finca.getTamanoHa());
            ps.setString(3, finca.getUbicacion());
            ps.setString(4, finca.getTipoProduccion());
            ps.setString(5, finca.getDescripcion());
            ps.setInt(6, finca.getIdFinca());
            ps.setInt(7, finca.getDuenoId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando finca", e);
        }
    }

    public boolean eliminar(int idFinca, int duenoId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, idFinca);
            ps.setInt(2, duenoId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando finca", e);
        }
    }
}
