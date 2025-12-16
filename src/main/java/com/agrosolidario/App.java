package com.agrosolidario;

import com.agrosolidario.dao.PersonaDesplazadaDao;
import com.agrosolidario.model.PersonaDesplazada;
import com.agrosolidario.model.Usuario;
import com.agrosolidario.service.AuthService;
import com.agrosolidario.ui.MenuGestionSolicitudesPersona;
import com.agrosolidario.ui.MenuPropietario;
import com.agrosolidario.ui.TestConexion;
import java.util.Scanner;

public class App {

    private final AuthService authService = new AuthService();
    private final PersonaDesplazadaDao personaDesplazadaDao = new PersonaDesplazadaDao();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new App().iniciar();
    }

    private void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> iniciarSesion();
                case 2 -> TestConexion.ejecutarPrueba();
                case 0 -> System.out.println("Hasta pronto.");
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n==== AgroSolidario - Consola ====");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Probar conexión a la base de datos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void iniciarSesion() {
        System.out.print("Correo: ");
        String email = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            Usuario usuario = authService.login(email, password);

            if ("DUENO".equals(usuario.getRol())) {
                new MenuPropietario(usuario.getIdUsuario()).iniciar();
            } else {
                PersonaDesplazada persona = personaDesplazadaDao.buscarPorUsuarioId(usuario.getIdUsuario());
                if (persona == null) {
                    System.out.println("No se encontró un perfil de persona desplazada para el usuario.");
                    return;
                }
                new MenuGestionSolicitudesPersona().mostrarMenu(persona.getIdPersona());
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número válido.");
            return -1;
        }
    }
}
