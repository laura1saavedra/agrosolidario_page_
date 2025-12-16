package com.agrosolidario.ui;

import com.agrosolidario.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConexion {

    public static void main(String[] args) {

        String sqlCountUsuarios = "SELECT COUNT(*) AS total FROM usuario";
        String sqlHoraServidor = "SELECT NOW() AS hora";

        try (Connection con = DatabaseConnection.getConnection()) {

            // 1) Confirmar datos del motor y esquema
            System.out.println("Conexión exitosa a MySQL");
            System.out.println("Motor BD: " + con.getMetaData().getDatabaseProductName());
            System.out.println("Esquema actual: " + con.getCatalog());

            // 2) Probar consulta real: contar usuarios
            try (PreparedStatement ps = con.prepareStatement(sqlCountUsuarios);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    int total = rs.getInt("total");
                    System.out.println("Usuarios registrados: " + total);
                }
            }

            // 3) Probar consulta real: hora del servidor
            try (PreparedStatement ps2 = con.prepareStatement(sqlHoraServidor);
                 ResultSet rs2 = ps2.executeQuery()) {

                if (rs2.next()) {
                    System.out.println("Hora del servidor: " + rs2.getString("hora"));
                }
            }

            System.out.println("Prueba JDBC completada correctamente.");

        } catch (SQLException e) {
            System.out.println(" Error en la conexión o consulta JDBC:");
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
