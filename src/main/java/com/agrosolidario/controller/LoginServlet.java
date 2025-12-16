package com.agrosolidario.controller;

import com.agrosolidario.model.Usuario;
import com.agrosolidario.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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

        try {
            Usuario usuario = authService.login(email, password);

            // Guardar sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            // Redirección por rol
            if ("DUENO".equals(usuario.getRol())) {
                response.sendRedirect("dueno/dashboard.jsp");
            } else {
                response.sendRedirect("persona/dashboard.jsp");
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/inicio-sesion.jsp").forward(request, response);
        }
    }
}

