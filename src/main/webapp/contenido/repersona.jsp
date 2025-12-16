<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AgroSolidario - Registro</title>

    <link rel="icon" type="image/svg+xml" href="../favicon.svg">

    <link rel="stylesheet" href="../style.css"> 
    <script src="${pageContext.request.contextPath}/app.js" defer></script>

</head>
<body>

    <div class="header">
        <span>AgroSolidario</span>
        <a href="../index.jsp" class="exit-btn">Salir</a>
    </div>

    <div class="container">
        <h2>Registro persona en situación de desplazamiento</h2>
        <p>Por favor complete el siguiente formulario con sus datos personales.</p>

        <div class="form-box">
            <div class="label-title">Datos personales:</div>

            <div class="row">
                <div>
                    <label>Número de documento:</label>
                    <input type="text">
                </div>

                <div>
                    <label>Ubicación actual:</label>
                    <input type="text" placeholder="Colombia, Bogotá D.C">
                </div>
            </div>

            <div class="row">
                <div>
                    <label>Número de celular:</label>
                    <input type="text">
                </div>

                <div>
                    <label>Edad:</label>
                    <input type="number">
                </div>
            </div>
        </div>
    </div>

    <div class="separator"></div>

     <div class="container">

        <div class="form-box">
            <div class="label-title">Experiencia laboral:</div>

            <label>Descripción de experiencia laboral:</label>
            <textarea rows="4"></textarea>

            <label>Habilidades que domina:</label>
            <input type="text">

            <label>Años de experiencia:</label>
            <input type="number">
        </div>
    </div>

    <div class="separator"></div>
   
    <div class="container">

        <div class="form-box">
            <div class="label-title">Documentos de verificación:</div>

            <div class="row">
                <div style="text-align:center;">
                    <p>Adjunte su documento de <br />identificación:</p>
                     <input id="doc-id" class="file-input" type="file" accept=".pdf,.jpg,.jpeg,.png" />

                     <label for="doc-id" class="upload-btn">Subir archivo</label>
                </div>

                <div style="text-align:center;">
                    <p>Adjuntar documento que certifique <br />desplazamiento:</p>
                     <input id="doc-id" class="file-input" type="file" accept=".pdf,.jpg,.jpeg,.png" />

                     <label for="doc-id" class="upload-btn">Subir archivo</label>
                </div>
            </div>
        </div>
        <div class="btn-wrapper">
            <a class="btn" href="./solicitudes.jsp">Continuar</a>
        </div>
    </div>

</body>
</html>