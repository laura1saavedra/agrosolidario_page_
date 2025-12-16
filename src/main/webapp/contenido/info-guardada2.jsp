<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title> Información guardada propietario</title>

    <link rel="icon" type="image/svg+xml" href="../favicon.svg">

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Epilogue:wght@400;500;600&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="../style.css" />
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

  <main class="perfil-exito">
    <!-- Barra blanca superior -->
    <div class="perfil-exito__top">
      <h1>Perfil:</h1>
    </div>

    <!-- Tarjeta gris de confirmación -->
    <section class="perfil-exito__card" role="status" aria-live="polite">
      <p>¡Tu información fue guardada con éxito!</p>

      <div class="perfil-exito__icon" aria-hidden="true">
        <!-- ícono check (SVG) -->
        <svg viewBox="0 0 120 120" width="140" height="140">
          <circle cx="60" cy="60" r="46" fill="none" stroke="black" stroke-width="10"/>
          <path d="M38 62 L54 78 L82 48"
                fill="none" stroke="black" stroke-width="12"
                stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
    </section>

    <!-- Botón salir -->
    <a class="perfil-exito__btn" href="./busqueda.jsp">Salir</a>
  </main>

</body>
</html>
