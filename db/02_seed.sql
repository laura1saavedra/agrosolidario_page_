/* =========================================================
   AgroSolidario - Datos de prueba (02_seed.sql)
   REQUIERE ejecutar antes: db/01_schema.sql
   BD: agro_solidario
   ========================================================= */

USE agro_solidario;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE oferta_habilidad_requerida;
TRUNCATE TABLE persona_habilidad;
TRUNCATE TABLE solicitud_trabajo;
TRUNCATE TABLE mensaje;
TRUNCATE TABLE documento;
TRUNCATE TABLE oferta_trabajo;
TRUNCATE TABLE finca;
TRUNCATE TABLE persona_desplazada;
TRUNCATE TABLE dueno_finca;
TRUNCATE TABLE habilidad;
TRUNCATE TABLE usuario;

SET FOREIGN_KEY_CHECKS = 1;

-- 1) USUARIOS
INSERT INTO usuario (nombre, email, password_hash, rol)
VALUES
  ('Carlos Dueño', 'dueno@agro.com', 'HASH_PRUEBA_123', 'DUENO'),
  ('Ana Persona', 'persona@agro.com', 'HASH_PRUEBA_123', 'PERSONA');

-- 2) DUEÑO
INSERT INTO dueno_finca (usuario_id, nombre, contacto, ubicacion, foto_perfil_url)
VALUES
  (1, 'Carlos Dueño', '3001112233', 'Boyacá - Tunja', 'https://img.example.com/carlos.jpg');

-- 3) PERSONA DESPLAZADA
INSERT INTO persona_desplazada (
  usuario_id, nombre, fecha_nacimiento, contacto, ubicacion,
  descripcion_experiencia, anios_experiencia, foto_perfil_url
)
VALUES
  (2, 'Ana Persona', '1998-05-10', '3012223344', 'Boyacá - Tunja',
   'Experiencia en cosecha y clasificación de papa.', 3,
   'https://img.example.com/ana.jpg');

-- 4) HABILIDADES
INSERT INTO habilidad (nombre)
VALUES
  ('Cosecha'),
  ('Siembra'),
  ('Manejo de herramientas'),
  ('Clasificación de productos');

-- 5) HABILIDADES DE LA PERSONA
INSERT INTO persona_habilidad (persona_id, habilidad_id, nivel)
VALUES
  (1, 1, 'INTERMEDIO'),
  (1, 3, 'BASICO'),
  (1, 4, 'AVANZADO');

-- 6) FINCA
INSERT INTO finca (dueno_id, nombre_finca, tamano_ha, ubicacion, tipo_produccion, descripcion)
VALUES
  (1, 'Finca El Roble', 12.50, 'Boyacá - Tunja', 'Papa',
   'Finca dedicada al cultivo de papa y hortalizas.');

-- 7) OFERTA
INSERT INTO oferta_trabajo (
  finca_id, titulo, descripcion, requisitos, salario,
  fecha_inicio, fecha_fin, fecha_limite, vacantes, estado
)
VALUES
  (1, 'Jornalero para cosecha de papa',
   'Apoyo en cosecha y clasificación de papa.',
   'Experiencia básica en labores agrícolas.',
   65000.00,
   '2025-01-10', '2025-02-10', '2025-01-05',
   3, 'ACTIVA');

-- 8) HABILIDADES REQUERIDAS EN LA OFERTA
INSERT INTO oferta_habilidad_requerida (oferta_id, habilidad_id, nivel_min)
VALUES
  (1, 1, 'BASICO'),
  (1, 3, 'BASICO');

-- 9) SOLICITUD
INSERT INTO solicitud_trabajo (oferta_id, persona_id, estado)
VALUES
  (1, 1, 'PENDIENTE');

-- 10) DOCUMENTOS
INSERT INTO documento (tipo, url, estado, persona_id)
VALUES
  ('IDENTIDAD', 'https://docs.example.com/ana_cc.pdf', 'APROBADO', 1);

INSERT INTO documento (tipo, url, estado, dueno_id)
VALUES
  ('PROPIEDAD', 'https://docs.example.com/propiedad_carlos.pdf', 'PENDIENTE', 1);

INSERT INTO documento (tipo, url, estado, finca_id)
VALUES
  ('FINCA', 'https://docs.example.com/finca_el_roble.pdf', 'PENDIENTE', 1);

-- 11) MENSAJES
INSERT INTO mensaje (emisor_usuario_id, receptor_usuario_id, contenido, leido)
VALUES
  (1, 2, 'Hola Ana, hemos revisado tu solicitud. ¿Puedes iniciar pronto?', 0),
  (2, 1, 'Sí señor, tengo disponibilidad inmediata.', 0);

