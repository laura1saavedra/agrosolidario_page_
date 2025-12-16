<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Solicitud enviada</title>

    <link rel="icon" type="image/svg+xml" href="../favicon.svg">

    <link rel="stylesheet" href="../style.css">
</head>
<body>
    <header>
  <div class="logo">AgroSolidario</div>
  <nav>
    <a href="./pe-propietario.jsp">Mi finca</a>
    <a href="./ofertas.jsp">Mis ofertas</a>
    <a href="./busqueda.jsp">Búsqueda de trabajadores</a>
    <a href="../index.jsp">Cerrar sesión</a>
  </nav>
</header>

<main class="seleccion-persona">
  <h1 class="seleccion-persona__title">Acabas de enviarle la oferta a:</h1>

  <div class="seleccion-persona__card seleccion-persona__card--nombre">
    <p>Julio Gonzalo García López</p>
  </div>

  <div class="seleccion-persona__card seleccion-persona__card--accion">
    <p>¡Ahora te falta seleccionar otras 5 personas!.</p>
    <a href="./busqueda.jsp" class="btn-dark seleccion-persona__btn">Volver</a>
  </div>
</main>

</body>
</html>