# Proyecto de Gestión de Trabajadores y Ofertas - AgroSolidario

## Descripción

Este proyecto es una aplicación de gestión de ofertas de trabajo para personas desplazadas en el contexto agrícola. Los usuarios se dividen en **Propietarios** y **Personas Desplazadas**. Los propietarios pueden crear ofertas de trabajo, mientras que las personas desplazadas pueden buscar ofertas, enviar solicitudes, y gestionar su perfil y habilidades.

## Objetivo

El objetivo de este sistema es permitir la interacción entre propietarios de fincas y personas desplazadas para facilitar la contratación de mano de obra agrícola. El sistema tiene los siguientes módulos principales:

- **Registro e inicio de sesión**
- **Gestión de ofertas**: Publicación, modificación, eliminación y visualización de ofertas de trabajo.
- **Gestión de solicitudes**: Enviar y gestionar solicitudes de trabajo.
- **Gestión de perfil**: Permite a las personas desplazadas gestionar su información personal y habilidades.

## Funcionalidades

### 1. **Registro e inicio de sesión**
- Los usuarios pueden registrarse como **Propietarios** o **Personas Desplazadas**.
- Pueden acceder al sistema mediante un login utilizando su **correo electrónico** y **contraseña**.
- El sistema valida los roles y redirige a la interfaz correspondiente según el tipo de usuario.

### 2. **Gestión de ofertas**
- **Propietarios** pueden publicar nuevas ofertas de trabajo.
- Pueden consultar, modificar, o eliminar sus ofertas activas.
- Las ofertas tienen un estado que puede ser **ACTIVA**, **FINALIZADA** o **CANCELADA**.
- Las ofertas pueden estar asociadas con habilidades requeridas.

### 3. **Gestión de solicitudes**
- Las **Personas Desplazadas** pueden enviar solicitudes a ofertas activas.
- Los **Propietarios** pueden aceptar o rechazar solicitudes de trabajo.
- Las solicitudes tienen los estados **PENDIENTE**, **ACEPTADA** o **RECHAZADA**.

### 4. **Gestión de perfil**
- Los usuarios pueden gestionar su perfil personal (nombre, ubicación, experiencia, etc.).
- Pueden agregar y eliminar habilidades de su perfil.
- Pueden gestionar documentos asociados a su perfil (identificación, certificados, etc.).
- Pueden actualizar su foto de perfil.


---

## Estructura del Proyecto

```plaintext
src/
 ├── com/
     ├── agrosolidario/
         ├── dao/
         │    ├── PersonaDesplazadaDao.java
         │    ├── OfertaTrabajoDao.java
         │    └── MensajeDao.java
         ├── model/
         │    ├── PersonaDesplazada.java
         │    ├── OfertaTrabajo.java
         │    ├── Mensaje.java
         │    └── Habilidad.java
         ├── service/
         │    ├── GestionSolicitudesService.java
         │    └── MensajeriaService.java
         ├── ui/ (o controller/)
         │    └── MenuPropietario.java
         ├── config/
         │    └── DatabaseConnection.java


## Tecnologías Utilizadas:

- Java 8+ para la implementación de la lógica.

- JDBC para la conexión y manipulación de bases de datos.

- MySQL como sistema de gestión de bases de datos.

- PreparedStatements para consultas seguras.

- Modelo MVC para estructurar la aplicación. 


## Instrucciones de uso

1. **Base de datos:**

- Crea una base de datos en MySQL llamada agrosolidario.

- Ejecuta los scripts de creación de tablas en db/01_schema.sql.

- Puedes cargar datos de prueba usando db/02_seed.sql.

2. **Configuración de la base de datos:**

- En el archivo src/main/resources/db.properties, configura la URL de la base de datos, el usuario y la contraseña.

3. **Ejecutar el proyecto:**

- Abre tu IDE favorito (NetBeans, IntelliJ, Eclipse).

- Importa el proyecto y configura las dependencias (si usas Maven o Gradle).

- Ejecuta el archivo app.java o la clase MenuPropietario.java (según si usas interfaz de consola o web).

4. Interfaz:

- El proyecto actualmente funciona mediante consola, y muestra menús para interactuar con el sistema.

- Los propietarios pueden publicar ofertas, consultar solicitudes y gestionar su perfil.

- Las personas desplazadas pueden enviar solicitudes a ofertas, gestionar su perfil y habilidades.


## Pruebas

###Prueba 1: Registro de usuario

- Registra un propietario y una persona desplazada con el sistema.

- Verifica que ambos usuarios pueden iniciar sesión.

###Prueba 2: Publicar y gestionar ofertas

- Un propietario crea una oferta.

- Verifica que la oferta sea visible en su lista de ofertas y que pueda ser modificada o eliminada.

###Prueba 3: Solicitar empleo

- Una persona desplazada envía una solicitud a la oferta publicada por el propietario.

- El propietario acepta o rechaza la solicitud.

- Verifica que el estado de la solicitud cambie correctamente.


## Ejemplo de uso

### Registro de propietario
- Ingrese su email: propietario@test.com
- Ingrese su contraseña: 123456
- Ingrese su nombre: Juan Pérez
...
Oferta publicada con éxito.

###Solicitar empleo
- Ingrese su email: desplazado@test.com
- Ingrese su contraseña: 123456
- ¿Desea aplicar a la oferta 'Trabajador agrícola'? (Sí/No)
-  Oferta de trabajo solicitada exitosamente.

---

Desarrollador

---

Laura Saavedra 