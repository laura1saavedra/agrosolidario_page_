package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.OfertaTrabajo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfertaTrabajoDao {

    public int crear(OfertaTrabajo oferta) {
        String sql = """
            INSERT INTO oferta_trabajo
            (finca_id, titulo, descripcion, requisitos, salario,
             fecha_publicacion, fecha_inicio, fecha_fin, fecha_limite,
             vacantes, estado)
            VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, oferta.getFincaId());
            ps.setString(2, oferta.getTitulo());
            ps.setString(3, oferta.getDescripcion());
            ps.setString(4, oferta.getRequisitos());
            ps.setDouble(5, oferta.getSalario());
            ps.setDate(6, Date.valueOf(oferta.getFechaInicio()));
            ps.setDate(7, Date.valueOf(oferta.getFechaFin()));
            ps.setDate(8, Date.valueOf(oferta.getFechaLimite()));
            ps.setInt(9, oferta.getVacantes());
            ps.setString(10, oferta.getEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear oferta", e);
        }
        return -1;
    }


    public OfertaTrabajo buscarPorId(int idOferta) {
        String sql = "SELECT * FROM oferta_trabajo WHERE id_oferta = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOferta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearOferta(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar oferta", e);
        }
        return null;
    }


    public List<OfertaTrabajo> listarPorFincaId(int fincaId) {
        List<OfertaTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM oferta_trabajo WHERE finca_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, fincaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearOferta(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando ofertas", e);
        }
        return lista;
    }
    
    public List<OfertaTrabajo> listarActivas() {
        List<OfertaTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM oferta_trabajo WHERE estado = 'ACTIVA' ORDER BY fecha_publicacion DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearOferta(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando ofertas activas", e);
        }
        return lista;
    }

    public boolean actualizar(OfertaTrabajo oferta) {
        String sql = """
            UPDATE oferta_trabajo
            SET titulo=?, descripcion=?, requisitos=?, salario=?,
                fecha_inicio=?, fecha_fin=?, fecha_limite=?,
                vacantes=?, estado=?
            WHERE id_oferta=?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, oferta.getTitulo());
            ps.setString(2, oferta.getDescripcion());
            ps.setString(3, oferta.getRequisitos());
            ps.setDouble(4, oferta.getSalario());
            ps.setDate(5, Date.valueOf(oferta.getFechaInicio()));
            ps.setDate(6, Date.valueOf(oferta.getFechaFin()));
            ps.setDate(7, Date.valueOf(oferta.getFechaLimite()));
            ps.setInt(8, oferta.getVacantes());
            ps.setString(9, oferta.getEstado());
            ps.setInt(10, oferta.getIdOferta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando oferta", e);
        }
    }


    public boolean eliminar(int idOferta) {
        String sql = "DELETE FROM oferta_trabajo WHERE id_oferta = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOferta);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando oferta", e);
        }
    }

    // Mapper
    private OfertaTrabajo mapearOferta(ResultSet rs) throws SQLException {
        OfertaTrabajo o = new OfertaTrabajo();
        o.setIdOferta(rs.getInt("id_oferta"));
        o.setFincaId(rs.getInt("finca_id"));
        o.setTitulo(rs.getString("titulo"));
        o.setDescripcion(rs.getString("descripcion"));
        o.setRequisitos(rs.getString("requisitos"));
        o.setSalario(rs.getDouble("salario"));
        o.setFechaPublicacion(rs.getDate("fecha_publicacion").toLocalDate());
        o.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
        o.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
        o.setFechaLimite(rs.getDate("fecha_limite").toLocalDate());
        o.setVacantes(rs.getInt("vacantes"));
        o.setEstado(rs.getString("estado"));
        return o;
    }
}
