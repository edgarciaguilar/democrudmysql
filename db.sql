/* Create database cursosonline0322 */
CREATE DATABASE cursosonline0322;

/* Change active database */
USE cursosonline0322;

/* Create table curso */
CREATE TABLE curso (
    idcurso int AUTO_INCREMENT PRIMARY KEY,
    titulo varchar(100) NOT NULL,
    descripcion text,
    ruta_imagen varchar(250),
    precio float NOT NULL,
    fecha_inicio date NOT NULL,
    fecha_creacion datetime NOT NULL,
    fecha_act datetime
);

/* Create table usuario */
CREATE TABLE usuario (
    idusuario int AUTO_INCREMENT PRIMARY KEY,
    nombres varchar(50) NOT NULL,
    apellidos varchar(50) NOT NULL,
    nom_completo varchar(100) NOT NULL,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(250) NOT NULL,
    rol enum('ADMIN','ESTUDIANTE') NOT NULL,
    fecha_creacion datetime NOT NULL,
    fecha_act datetime
);

/* Create table inscripcion */
CREATE TABLE inscripcion (
    idinscripcion int AUTO_INCREMENT PRIMARY KEY,
    idusuario int,
    idcurso int,
    fecha_inscripcion datetime,
    FOREIGN KEY (idusuario) REFERENCES usuario(idusuario),
    FOREIGN KEY (idcurso) REFERENCES curso(idcurso)
);
