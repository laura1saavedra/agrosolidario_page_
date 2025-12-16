package com.agrosolidario.ui;

import com.agrosolidario.model.OfertaTrabajo;
import com.agrosolidario.model.PersonaDesplazada;
import com.agrosolidario.service.PostulacionService;

import java.util.List;
import java.util.Scanner;

public class MenuPersona {

    private final PersonaDesplazada persona;
    private final MenuGestionSolicitudesPersona gestionSolicitudesMenu = new MenuGestionSolicitudesPersona();
    private final PostulacionService postulacionService = new PostulacionService();
    private final Scanner scanner = new Scanner(System.in);

    public MenuPersona(PersonaDesplazada persona) {
        this.persona = persona;
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PERSONA DESPLAZADA ===");
            System.out.println("1. Gestionar mis solicitudes");
            System.out.println("2. Listar ofertas activas");
            System.out.println("3. Solicitar empleo en una oferta");
            System.out.println("0. Cerrar sesión");

            opcion = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcion) {
                    case 1 -> gestionSolicitudesMenu.mostrarMenu(persona.getIdPersona());
                    case 2 -> listarOfertas();
                    case 3 -> solicitarEmpleo();
                    case 0 -> System.out.println("Hasta pronto, " + persona.getNombre());
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private void listarOfertas() {
        List<OfertaTrabajo> ofertas = postulacionService.listarOfertasActivas();

        if (ofertas.isEmpty()) {
            System.out.println("No hay ofertas activas.");
            return;
        }

        System.out.println("\n--- OFERTAS DISPONIBLES ---");
        for (OfertaTrabajo o : ofertas) {
            System.out.println("""
                ------------------------------
                ID: %d
                Título: %s
                Salario: %.2f
                Vacantes: %d
                Fecha inicio: %s
                Fecha límite: %s
                Estado: %s
                ------------------------------
                """.formatted(
                    o.getIdOferta(),
                    o.getTitulo(),
                    o.getSalario(),
                    o.getVacantes(),
                    o.getFechaInicio(),
                    o.getFechaLimite(),
                    o.getEstado()
                )
            );
        }
    }

    private void solicitarEmpleo() {
        System.out.print("Ingrese el ID de la oferta a la que desea aplicar: ");
        int idOferta = Integer.parseInt(scanner.nextLine());

        postulacionService.solicitarEmpleo(idOferta, persona.getIdPersona());
        System.out.println(" Solicitud enviada correctamente.");
    }
}
