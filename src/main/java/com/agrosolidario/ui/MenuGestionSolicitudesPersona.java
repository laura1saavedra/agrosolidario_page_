package com.agrosolidario.ui;

import com.agrosolidario.model.SolicitudTrabajo;
import com.agrosolidario.service.GestionSolicitudesService;

import java.util.List;
import java.util.Scanner;

public class MenuGestionSolicitudesPersona {

    private final GestionSolicitudesService service = new GestionSolicitudesService();
    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu(int personaId) {

        int opcion;

        do {
            System.out.println("\n=== GESTIÓN DE SOLICITUDES ===");
            System.out.println("1. Ver solicitudes pendientes");
            System.out.println("2. Ver historial de solicitudes");
            System.out.println("3. Aceptar solicitud");
            System.out.println("4. Rechazar solicitud");
            System.out.println("0. Volver");

            opcion = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcion) {
                    case 1 -> mostrarSolicitudes(service.listarPendientes(personaId));
                    case 2 -> mostrarSolicitudes(service.listarHistorial(personaId));
                    case 3 -> procesarSolicitud(personaId, true);
                    case 4 -> procesarSolicitud(personaId, false);
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private void mostrarSolicitudes(List<SolicitudTrabajo> lista) {

        if (lista.isEmpty()) {
            System.out.println("No hay solicitudes.");
            return;
        }

        for (SolicitudTrabajo s : lista) {
            System.out.println("""
                ---------------------------------
                ID Solicitud: %d
                Oferta: %s
                Finca: %s
                Ubicación: %s
                Salario: %.2f
                Estado: %s
                Fecha envío: %s
                ---------------------------------
                """.formatted(
                    s.getIdSolicitud(),
                    s.getTituloOferta(),
                    s.getNombreFinca(),
                    s.getUbicacionFinca(),
                    s.getSalario(),
                    s.getEstado(),
                    s.getFechaEnvio()
                )
            );
        }
    }

    private void procesarSolicitud(int personaId, boolean aceptar) {

        System.out.print("Ingrese ID de la solicitud: ");
        int idSolicitud = Integer.parseInt(scanner.nextLine());

        if (aceptar) {
            service.aceptarSolicitud(idSolicitud, personaId);
            System.out.println("Solicitud ACEPTADA correctamente.");
        } else {
            service.rechazarSolicitud(idSolicitud, personaId);
            System.out.println("Solicitud RECHAZADA correctamente.");
        }
    }
}
