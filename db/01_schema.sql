/* =========================================================
   AgroSolidario - Esquema MySQL (01_schema.sql)
   Motor: InnoDB | Charset: utf8mb4
   ========================================================= */

CREATE DATABASE IF NOT EXISTS agro_solidario
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE agro_solidario;

SET FOREIGN_KEY_CHECKS = 0;

-- Borrar tablas en orden seguro (hijas -> padres)
DROP TABLE IF EXISTS oferta_habilidad_requerida;
DROP TABLE IF EXISTS persona_habilidad;
DROP TABLE IF EXISTS solicitud_trabajo;
DROP TABLE IF EXISTS mensaje;
DROP TABLE IF EXISTS documento;
DROP TABLE IF EXISTS oferta_trabajo;
DROP TABLE IF EXISTS finca;
DROP TABLE IF EXISTS persona_desplazada;
DROP TABLE IF EXISTS dueno_finca;
DROP TABLE IF EXISTS habilidad;
DROP TABLE IF EXISTS usuario;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================================================
-- TABLA: usuario
-- =========================================================
CREATE TABLE usuario (
  id_usuario INT(11) NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(80) NOT NULL,
  email VARCHAR(120) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  rol ENUM('DUENO','PERSONA') NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usuario),
  UNIQUE KEY uk_usuario_email (email)
) ENGINE=InnoDB;

-- =========================================================
-- TABLA: habilidad
-- =========================================================
CREATE TABLE habilidad (
  id_habilidad INT(11) NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(60) NOT NULL,
  PRIMARY KEY (id_habilidad),
  UNIQUE KEY uk_habilidad_nombre (nombre)
) ENGINE=InnoDB;

-- =========================================================
-- TABLA: dueno_finca
-- =========================================================
CREATE TABLE dueno_finca (
  id_dueno INT(11) NOT NULL AUTO_INCREMENT,
  usuario_id INT(11) NOT NULL,
  nombre VARCHAR(80) NOT NULL,
  contacto VARCHAR(30) NOT NULL,
  ubicacion VARCHAR(120) NOT NULL,
  foto_perfil_url VARCHAR(255) NULL,
  PRIMARY KEY (id_dueno),
  UNIQUE KEY uk_dueno_usuario (usuario_id),
  CONSTRAINT fk_dueno_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- TABLA: persona_desplazada
-- =========================================================
CREATE TABLE persona_desplazada (
  id_persona INT(11) NOT NULL AUTO_INCREMENT,
  usuario_id INT(11) NOT NULL,
  nombre VARCHAR(80) NOT NULL,
  fecha_nacimiento DATE NOT NULL,
  contacto VARCHAR(30) NOT NULL,
  ubicacion VARCHAR(120) NOT NULL,
  descripcion_experiencia TEXT NULL,
  anios_experiencia TINYINT(3) NULL,
  foto_perfil_url VARCHAR(255) NULL,
  PRIMARY KEY (id_persona),
  UNIQUE KEY uk_persona_usuario (usuario_id),
  CONSTRAINT fk_persona_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- TABLA PUENTE: persona_habilidad
-- =========================================================
CREATE TABLE persona_habilidad (
  persona_id INT(11) NOT NULL,
  habilidad_id INT(11) NOT NULL,
  nivel ENUM('BASICO','INTERMEDIO','AVANZADO') NOT NULL,
  PRIMARY KEY (persona_id, habilidad_id),
  KEY idx_ph_habilidad (habilidad_id),
  CONSTRAINT fk_ph_persona
    FOREIGN KEY (persona_id) REFERENCES persona_desplazada(id_persona)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_ph_habilidad
    FOREIGN KEY (habilidad_id) REFERENCES habilidad(id_habilidad)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- TABLA: finca
-- =========================================================
CREATE TABLE finca (
  id_finca INT(11) NOT NULL AUTO_INCREMENT,
  dueno_id INT(11) NOT NULL,
  nombre_finca VARCHAR(80) NOT NULL,
  tamano_ha DECIMAL(10,2) NULL,
  ubicacion VARCHAR(120) NOT NULL,
  tipo_produccion VARCHAR(60) NOT NULL,
  descripcion TEXT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_finca),
  KEY idx_finca_dueno (dueno_id),
  CONSTRAINT fk_finca_dueno
    FOREIGN KEY (dueno_id) REFERENCES dueno_finca(id_dueno)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- TABLA: oferta_trabajo
-- =========================================================
CREATE TABLE oferta_trabajo (
  id_oferta INT(11) NOT NULL AUTO_INCREMENT,
  finca_id INT(11) NOT NULL,
  titulo VARCHAR(120) NOT NULL,
  descripcion TEXT NOT NULL,
  requisitos TEXT NULL,
  salario DECIMAL(12,2) NULL,
  fecha_inicio DATE NULL,
  fecha_fin DATE NULL,
  fecha_publicacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_limite DATE NULL,
  vacantes SMALLINT(5) NOT NULL DEFAULT 1,
  estado ENUM('ACTIVA','FINALIZADA','CANCELADA') NOT NULL DEFAULT 'ACTIVA',
  PRIMARY KEY (id_oferta),
  KEY idx_oferta_finca (finca_id),
  KEY idx_oferta_estado (estado),
  CONSTRAINT fk_oferta_finca
    FOREIGN KEY (finca_id) REFERENCES finca(id_finca)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- TABLA PUENTE: oferta_habilidad_requerida
-- =========================================================
CREATE TABLE oferta_habilidad_requerida (
  oferta_id INT(11) NOT NULL,
  habilidad_id INT(11) NOT NULL,
  nivel_min ENUM('BASICO','INTERMEDIO','AVANZADO') NOT NULL,
  PRIMARY KEY (oferta_id, habilidad_id),
  KEY idx_ohr_habilidad (habilidad_id),
  CONSTRAINT fk_ohr_oferta_trabajo
    FOREIGN KEY (oferta_id) REFERENCES oferta_trabajo(id_oferta)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_ohr_habilidad_ref
    FOREIGN KEY (habilidad_id) REFERENCES habilidad(id_habilidad)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- TABLA: solicitud_trabajo
-- =========================================================
CREATE TABLE solicitud_trabajo (
  id_solicitud INT(11) NOT NULL AUTO_INCREMENT,
  oferta_id INT(11) NOT NULL,
  persona_id INT(11) NOT NULL,
  fecha_envio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_respuesta DATETIME NULL,
  estado ENUM('PENDIENTE','ACEPTADA','RECHAZADA') NOT NULL DEFAULT 'PENDIENTE',
  PRIMARY KEY (id_solicitud),
  UNIQUE KEY uk_solicitud_oferta_persona (oferta_id, persona_id),
  KEY idx_solicitud_oferta (oferta_id),
  KEY idx_solicitud_persona (persona_id),
  KEY idx_solicitud_estado (estado),
  CONSTRAINT fk_solicitud_oferta
    FOREIGN KEY (oferta_id) REFERENCES oferta_trabajo(id_oferta)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_solicitud_persona
    FOREIGN KEY (persona_id) REFERENCES persona_desplazada(id_persona)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- TABLA: mensaje
-- =========================================================
CREATE TABLE mensaje (
  id_mensaje INT(11) NOT NULL AUTO_INCREMENT,
  emisor_usuario_id INT(11) NOT NULL,
  receptor_usuario_id INT(11) NOT NULL,
  contenido TEXT NOT NULL,
  fecha_envio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  leido TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id_mensaje),
  KEY idx_mensaje_emisor (emisor_usuario_id),
  KEY idx_mensaje_receptor (receptor_usuario_id),
  KEY idx_mensaje_leido (leido),
  CONSTRAINT fk_mensaje_emisor
    FOREIGN KEY (emisor_usuario_id) REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_mensaje_receptor
    FOREIGN KEY (receptor_usuario_id) REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================================================
-- TABLA: documento
-- =========================================================
CREATE TABLE documento (
  id_documento INT(11) NOT NULL AUTO_INCREMENT,
  tipo ENUM('IDENTIDAD','PROPIEDAD','FINCA','CERTIFICADO') NOT NULL,
  url VARCHAR(255) NOT NULL,
  fecha_subida DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado ENUM('PENDIENTE','APROBADO','RECHAZADO') NOT NULL DEFAULT 'PENDIENTE',
  persona_id INT(11) NULL,
  finca_id INT(11) NULL,
  dueno_id INT(11) NULL,
  PRIMARY KEY (id_documento),
  KEY idx_documento_persona (persona_id),
  KEY idx_documento_finca (finca_id),
  KEY idx_documento_dueno (dueno_id),
  KEY idx_documento_tipo (tipo),
  CONSTRAINT fk_documento_persona
    FOREIGN KEY (persona_id) REFERENCES persona_desplazada(id_persona)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_documento_finca
    FOREIGN KEY (finca_id) REFERENCES finca(id_finca)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_documento_dueno
    FOREIGN KEY (dueno_id) REFERENCES dueno_finca(id_dueno)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;
