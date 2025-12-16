package com.agrosolidario.service;

import com.agrosolidario.dao.DuenoFincaDao;
import com.agrosolidario.dao.PersonaDesplazadaDao;
import com.agrosolidario.dao.UsuarioDao;
import com.agrosolidario.model.DuenoFinca;
import com.agrosolidario.model.PersonaDesplazada;
import com.agrosolidario.model.Usuario;

public class AuthService {

    private final UsuarioDao usuarioDao;
    private final DuenoFincaDao duenoFincaDao;
    private final PersonaDesplazadaDao personaDesplazadaDao;

    public AuthService() {
        this.usuarioDao = new UsuarioDao();
        this.duenoFincaDao = new DuenoFincaDao();
        this.personaDesplazadaDao = new PersonaDesplazadaDao();
    }

    /* =========================
       REGISTRO GENERAL
       ========================= */
    public Usuario registrarUsuario(
            String email,
            String password,
            String rol,
            String nombre,
            String telefono,
            String direccionOUbicacion,
            String fechaNacimiento, // solo para persona
            int aniosExperiencia     // solo para persona
    ) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener mínimo 6 caracteres");
        }
        if (!rol.equals("DUENO") && !rol.equals("PERSONA")) {
            throw new IllegalArgumentException("Rol inválido");
        }


        if (usuarioDao.emailExiste(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }


        String passwordHash = PasswordUtil.hashPassword(password);


        Usuario usuario = new Usuario(email, passwordHash, rol, "ACTIVO");
        int usuarioId = usuarioDao.crearUsuario(usuario);
        usuario.setIdUsuario(usuarioId);


        if (rol.equals("DUENO")) {
            DuenoFinca dueno = new DuenoFinca(
                    usuarioId,
                    nombre,
                    telefono,
                    direccionOUbicacion
            );
            duenoFincaDao.crearDueno(dueno);

        } else {
            PersonaDesplazada persona = new PersonaDesplazada(
                    usuarioId,
                    nombre,
                    java.time.LocalDate.parse(fechaNacimiento),
                    direccionOUbicacion,
                    telefono,
                    aniosExperiencia
            );
            personaDesplazadaDao.crearPersona(persona);
        }

        return usuario;
    }

    /* =========================
       LOGIN
       ========================= */
    public Usuario login(String email, String password) {

        // 1. Buscar usuario
        Usuario usuario = usuarioDao.buscarPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos");
        }

        // 2. Verificar estado
        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new IllegalArgumentException("Usuario inactivo");
        }

        // 3. Verificar contraseña
        boolean passwordValida = PasswordUtil.verificarPassword(
                password,
                usuario.getPasswordHash()
        );

        if (!passwordValida) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos");
        }

        return usuario;
    }
}
