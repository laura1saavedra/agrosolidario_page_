package com.agrosolidario.dao;

import com.agrosolidario.config.DatabaseConnection;
import com.agrosolidario.model.Usuario;

import java.sql.*;
import java.time.LocalDateTime;

public class UsuarioDao {

    public boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM usuario WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Error verificando email", e);
        }
    }

    // Crear usuario y retornar ID generado
    public int crearUsuario(Usuario usuario) {
        String sql = """
                INSERT INTO usuario (email, password_hash, rol, estado, fecha_registro)
                VALUES (?, ?, ?, ?, NOW())
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getPasswordHash());
            ps.setString(3, usuario.getRol());
            ps.setString(4, usuario.getEstado());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("No se pudo obtener el ID del usuario");

        } catch (SQLException e) {
            throw new RuntimeException("Error creando usuario", e);
        }
    }

    // Buscar usuario por email
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando usuario por email", e);
        }
    }

    // Buscar usuario por ID
    public Usuario buscarPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando usuario por ID", e);
        }
    }

    // Mapper privado
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setEmail(rs.getString("email"));
        usuario.setPasswordHash(rs.getString("password_hash"));
        usuario.setRol(rs.getString("rol"));
        usuario.setEstado(rs.getString("estado"));

        Timestamp fecha = rs.getTimestamp("fecha_registro");
        if (fecha != null) {
            usuario.setFechaRegistro(fecha.toLocalDateTime());
        }
        return usuario;
    }
}
