package com.agrosolidario.controller;

import com.agrosolidario.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        authService = new AuthService();
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rol = request.getParameter("rol"); // DUENO o PERSONA
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        String aniosExpStr = request.getParameter("aniosExperiencia");

        try {
            int aniosExperiencia = 0;
            if (aniosExpStr != null && !aniosExpStr.isBlank()) {
                aniosExperiencia = Integer.parseInt(aniosExpStr);
            }

            authService.registrarUsuario(
                    email,
                    password,
                    rol,
                    nombre,
                    telefono,
                    direccion,
                    fechaNacimiento,
                    aniosExperiencia
            );

            // Registro exitoso → login
            response.sendRedirect("login.jsp?registro=ok");

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
        }
    }
}
