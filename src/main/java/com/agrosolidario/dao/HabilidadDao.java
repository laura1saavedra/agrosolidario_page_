package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.Habilidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabilidadDao {

    public List<Habilidad> listarTodas() {
        List<Habilidad> lista = new ArrayList<>();
        String sql = "SELECT id_habilidad, nombre FROM habilidad";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Habilidad(
                        rs.getInt("id_habilidad"),
                        rs.getString("nombre")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando habilidades", e);
        }
        return lista;
    }
}
