<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio sesión </title>

    <link rel="icon" type="image/svg+xml" href="../favicon.svg">

    <link rel="stylesheet" href="../style.css">
</head>

<body>
    <header>
        <div class="logo">AgroSolidario</div>

        <nav>
            <a href="../index.jsp">Inicio</a>
            <a href="./inicio-sesion.jsp">Inicio de sesión</a>
            <a href="./registro.jsp">Regístrate</a>
        </nav>
    </header>

    <section class="seccion-formulario-inicio-sesion">

        <div class="contenedor-formulario">
            <h2>Inicio de sesión</h2>

            <form>

                <label for="correo-inicio">Correo</label>
                <input type="email" id="correo-inicio" name="correo">

                <label for="contrasena-inicio">Contraseña</label>
                <input type="password" id="contrasena-inicio" name="contrasena">

                <a id="iniciar" href="elige-perfil.jsp" >Iniciar sesión</a>

            </form>

            <p class="texto-crear-cuenta">
                ¿No tienes una cuenta?
                <a href="registro.jsp">Crear cuenta</a>
            </p>

        </div>

    </section>
</body>

</html>

<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div style="color:red;"><%= error %></div>
<% } %>
