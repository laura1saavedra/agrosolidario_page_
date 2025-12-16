package com.agrosolidario.ui;

import com.agrosolidario.model.Documento;
import com.agrosolidario.model.DuenoFinca;
import com.agrosolidario.model.Finca;
import com.agrosolidario.model.OfertaTrabajo;
import com.agrosolidario.service.PropietarioService;
import com.agrosolidario.service.OfertaService;

import java.util.List;
import java.util.Scanner;

public class MenuPropietario {

    private final PropietarioService service = new PropietarioService();
    private final OfertaService ofertaService = new OfertaService();
    private final Scanner sc = new Scanner(System.in);

   
    private int usuarioId = 1;

    public MenuPropietario() {
        this(1);
    }

    public MenuPropietario(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public void iniciar() {

        int opcion;
        do {
            System.out.println("\n===== MENÚ PROPIETARIO - MI FINCA =====");
            System.out.println("1. Ver datos personales");
            System.out.println("2. Actualizar datos personales");
            System.out.println("3. Crear finca");
            System.out.println("4. Ver mi finca");
            System.out.println("5. Actualizar finca");
            System.out.println("6. Eliminar finca");
            System.out.println("7. Registrar documento de la finca");
            System.out.println("8. Listar documentos de la finca");
            System.out.println("9. Gestionar ofertas laborales");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            try {
                switch (opcion) {
                    case 1 -> verDatosPersonales();
                    case 2 -> actualizarDatosPersonales();
                    case 3 -> crearFinca();
                    case 4 -> verFinca();
                    case 5 -> actualizarFinca();
                    case 6 -> eliminarFinca();
                    case 7 -> registrarDocumento();
                    case 8 -> listarDocumentos();
                    case 9 -> gestionarOfertas();
                    case 0 -> System.out.println("Saliendo del módulo Mi finca...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    // =========================
    // OPCIÓN 1
    // =========================
    private void verDatosPersonales() {
        DuenoFinca d = service.obtenerDuenoPorUsuario(usuarioId);
        System.out.println("\n--- DATOS PERSONALES ---");
        System.out.println("Nombre: " + d.getNombre());
        System.out.println("Contacto: " + d.getContacto());
        System.out.println("Ubicación: " + d.getUbicacion());
        System.out.println("Foto URL: " + d.getFotoPerfilUrl());
    }

    // =========================
    // OPCIÓN 2
    // =========================
    private void actualizarDatosPersonales() {
        DuenoFinca d = service.obtenerDuenoPorUsuario(usuarioId);

        System.out.print("Nuevo nombre: ");
        d.setNombre(sc.nextLine());

        System.out.print("Nuevo contacto: ");
        d.setContacto(sc.nextLine());

        System.out.print("Nueva ubicación: ");
        d.setUbicacion(sc.nextLine());

        System.out.print("Nueva foto URL: ");
        d.setFotoPerfilUrl(sc.nextLine());

        service.actualizarDatosDueno(d);
        System.out.println("Datos personales actualizados.");
    }

    // =========================
    // OPCIÓN 3
    // =========================
    private void crearFinca() {
        Finca f = new Finca();

        System.out.print("Nombre de la finca: ");
        f.setNombreFinca(sc.nextLine());

        System.out.print("Tamaño (ha): ");
        f.setTamanoHa(Double.valueOf(sc.nextLine()));

        System.out.print("Ubicación: ");
        f.setUbicacion(sc.nextLine());

        System.out.print("Tipo de producción: ");
        f.setTipoProduccion(sc.nextLine());

        System.out.print("Descripción: ");
        f.setDescripcion(sc.nextLine());

        int id = service.crearFinca(f, usuarioId);
        System.out.println("Finca creada con ID: " + id);
    }

    // =========================
    // OPCIÓN 4
    // =========================
    private void verFinca() {
        Finca f = service.obtenerFinca(service.obtenerDuenoPorUsuario(usuarioId).getIdDueno());

        if (f == null) {
            System.out.println("No tiene finca registrada.");
            return;
        }

        System.out.println("\n--- MI FINCA ---");
        System.out.println("Nombre: " + f.getNombreFinca());
        System.out.println("Tamaño (ha): " + f.getTamanoHa());
        System.out.println("Ubicación: " + f.getUbicacion());
        System.out.println("Tipo producción: " + f.getTipoProduccion());
        System.out.println("Descripción: " + f.getDescripcion());
    }

    // =========================
    // OPCIÓN 5
    // =========================
    private void actualizarFinca() {
        Finca f = service.obtenerFinca(service.obtenerDuenoPorUsuario(usuarioId).getIdDueno());

        if (f == null) {
            System.out.println(" No existe finca para actualizar.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        f.setNombreFinca(sc.nextLine());

        System.out.print("Nuevo tamaño (ha): ");
        f.setTamanoHa(Double.valueOf(sc.nextLine()));

        System.out.print("Nueva ubicación: ");
        f.setUbicacion(sc.nextLine());

        System.out.print("Nuevo tipo producción: ");
        f.setTipoProduccion(sc.nextLine());

        System.out.print("Nueva descripción: ");
        f.setDescripcion(sc.nextLine());

        service.actualizarFinca(f, usuarioId);
        System.out.println("Finca actualizada.");
    }

    // =========================
    // OPCIÓN 6
    // =========================
    private void eliminarFinca() {
        Finca f = service.obtenerFinca(service.obtenerDuenoPorUsuario(usuarioId).getIdDueno());

        if (f == null) {
            System.out.println("No existe finca para eliminar.");
            return;
        }

        service.eliminarFinca(f.getIdFinca(), usuarioId);
        System.out.println(" Finca eliminada.");
    }

    // =========================
    // OPCIÓN 7
    // =========================
    private void registrarDocumento() {
        Documento d = new Documento();

        System.out.print("Tipo documento (IDENTIDAD/PROPIEDAD/FINCA/CERTIFICADO): ");
        d.setTipo(sc.nextLine());

        System.out.print("URL del documento: ");
        d.setUrl(sc.nextLine());

        int id = service.registrarDocumentoFinca(d, usuarioId);
        System.out.println(" Documento registrado con ID: " + id);
    }

    // =========================
    // OPCIÓN 8
    // =========================
    private void listarDocumentos() {
        List<Documento> docs = service.listarDocumentos(usuarioId);

        System.out.println("\n--- DOCUMENTOS DE LA FINCA ---");
        for (Documento d : docs) {
            System.out.println(
                "ID: " + d.getIdDocumento() +
                " | Tipo: " + d.getTipo() +
                " | Estado: " + d.getEstado() +
                " | URL: " + d.getUrl()
            );
        }
    }

    private void gestionarOfertas() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE OFERTAS ---");
            System.out.println("1. Publicar nueva oferta");
            System.out.println("2. Listar mis ofertas");
            System.out.println("3. Actualizar oferta");
            System.out.println("4. Finalizar oferta");
            System.out.println("5. Eliminar oferta");
            System.out.println("0. Volver");
            opcion = Integer.parseInt(sc.nextLine());

            try {
                switch (opcion) {
                    case 1 -> publicarOferta();
                    case 2 -> listarOfertas();
                    case 3 -> actualizarOferta();
                    case 4 -> finalizarOferta();
                    case 5 -> eliminarOferta();
                    case 0 -> System.out.println("Regresando al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private OfertaTrabajo leerOfertaDesdeConsola(boolean exigirId) {
        OfertaTrabajo oferta = new OfertaTrabajo();

        if (exigirId) {
            System.out.print("ID de la oferta: ");
            oferta.setIdOferta(Integer.parseInt(sc.nextLine()));
        }

        System.out.print("Título: ");
        oferta.setTitulo(sc.nextLine());

        System.out.print("Descripción: ");
        oferta.setDescripcion(sc.nextLine());

        System.out.print("Requisitos: ");
        oferta.setRequisitos(sc.nextLine());

        System.out.print("Salario: ");
        oferta.setSalario(Double.parseDouble(sc.nextLine()));

        System.out.print("Vacantes: ");
        oferta.setVacantes(Integer.parseInt(sc.nextLine()));

        System.out.print("Fecha de inicio (YYYY-MM-DD): ");
        oferta.setFechaInicio(java.time.LocalDate.parse(sc.nextLine()));

        System.out.print("Fecha de fin (YYYY-MM-DD): ");
        oferta.setFechaFin(java.time.LocalDate.parse(sc.nextLine()));

        System.out.print("Fecha límite de aplicación (YYYY-MM-DD): ");
        oferta.setFechaLimite(java.time.LocalDate.parse(sc.nextLine()));

        return oferta;
    }

    private void publicarOferta() {
        OfertaTrabajo oferta = leerOfertaDesdeConsola(false);
        int id = ofertaService.publicarOferta(oferta, usuarioId);
        System.out.println(" Oferta publicada con ID: " + id);
    }

    private void listarOfertas() {
        List<OfertaTrabajo> ofertas = ofertaService.listarMisOfertas(usuarioId);

        if (ofertas.isEmpty()) {
            System.out.println("No hay ofertas registradas.");
            return;
        }

        for (OfertaTrabajo o : ofertas) {
            System.out.println("""
                ------------------------
                ID: %d
                Título: %s
                Salario: %.2f
                Vacantes: %d
                Estado: %s
                Fecha inicio: %s
                Fecha fin: %s
                ------------------------
                """.formatted(
                    o.getIdOferta(),
                    o.getTitulo(),
                    o.getSalario(),
                    o.getVacantes(),
                    o.getEstado(),
                    o.getFechaInicio(),
                    o.getFechaFin()
                )
            );
        }
    }

    private void actualizarOferta() {
        OfertaTrabajo oferta = leerOfertaDesdeConsola(true);
        ofertaService.actualizarOferta(oferta, usuarioId);
        System.out.println(" Oferta actualizada.");
    }

    private void finalizarOferta() {
        System.out.print("ID de la oferta a finalizar: ");
        int id = Integer.parseInt(sc.nextLine());
        ofertaService.finalizarOferta(id, usuarioId);
        System.out.println("Oferta finalizada.");
    }

    private void eliminarOferta() {
        System.out.print("ID de la oferta a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        ofertaService.eliminarOferta(id, usuarioId);
        System.out.println("Oferta eliminada.");
    }
}
