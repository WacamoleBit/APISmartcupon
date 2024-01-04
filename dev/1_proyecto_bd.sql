-- Crear base de datos

DROP DATABASE IF EXISTS smartcupon_db;

CREATE DATABASE smartcupon_db;

-- Crear tablas

USE smartcupon_db;

CREATE TABLE
    estado (
        idEstado INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(100)
    );

CREATE TABLE
    ciudad (
        idCiudad INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(100),
        estado INT NOT NULL,
        FOREIGN KEY (estado) REFERENCES estado(idEstado)
    );

CREATE TABLE
    estatus ( -- puede ser usado por empresa, promocion, cupon
        idEstatus INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(8) NOT NULL -- valido o invalido
    );

CREATE TABLE
    tipoPromocion (
        idTipoPromocion INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(14) NOT NULL -- descuento o costo rebajado
    );

CREATE TABLE
    categoriaPromocion (
        idCategoria INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(20) NOT NULL -- cine, comida, farmacia, etc...
    );

CREATE TABLE
    rol (
        idRol INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(23) NOT NULL -- admin comercial, general
    );

CREATE TABLE
    tipoPersona (
        idTipoPersona INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(13) NOT NULL -- encargado, representante
    );

CREATE TABLE
    tipoDireccion(
        idTipoDireccion INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(8) NOT NULL -- empresa, sucursal, usuario
    );

CREATE TABLE
    persona (
        idPersona INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(50) NOT NULL,
        apellidoPaterno VARCHAR(50) NOT NULL,
        apellidoMaterno VARCHAR(50) NOT NULL,
        tipoPersona INT NOT NULL,
        FOREIGN KEY (tipoPersona) REFERENCES tipoPersona(idTipoPersona)
    );

CREATE TABLE
    direccion (
        idDireccion INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        calle VARCHAR(100) NOT NULL,
        numero INT NOT NULL,
        colonia VARCHAR(100),
        codigoPostal VARCHAR(5),
        ciudad INT,
        tipoDireccion INT NOT NULL, -- a quién le pertenece
        FOREIGN KEY (ciudad) REFERENCES ciudad(idCiudad),
        FOREIGN KEY (tipoDireccion) REFERENCES tipoDireccion(idTipoDireccion)
    );

CREATE TABLE
    empresa (
        idEmpresa INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(50) NOT NULL,
        nombreComercial VARCHAR(100) NOT NULL,
        logo LONGBLOB NULL,
        representante INT NOT NULL,
        email VARCHAR(50) NOT NULL, -- Es de la empresa
        direccion INT NOT NULL,
        telefono VARCHAR(10) NOT NULL,
        paginaWeb VARCHAR(100) NULL, -- No es NOT NULL, porque dice (si tiene)
        rfc VARCHAR(13) NOT NULL,
        estatus INT NOT NULL,
        FOREIGN KEY (direccion) REFERENCES direccion(idDireccion),
        FOREIGN KEY (estatus) REFERENCES estatus(idEstatus),
        FOREIGN KEY (representante) REFERENCES persona(idPersona)
    );

CREATE TABLE
    sucursal (
        idSucursal INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(50) NOT NULL,
        direccion INT NOT NULL, -- Al final debe de ser NOT NULL 
        telefono VARCHAR(10) NOT NULL,
        latitud DOUBLE NOT NULL,
        longitud DOUBLE NOT NULL,
        empresa INT NOT NULL,
        encargado INT NOT NULL, -- Se obtendra de un combo box
        FOREIGN KEY (direccion) REFERENCES direccion(idDireccion),
        FOREIGN KEY (encargado) REFERENCES persona(idPersona),
        FOREIGN KEY (empresa) REFERENCES empresa(idEmpresa)
    );

CREATE TABLE
    promocion (
        idPromocion INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR (50) NOT NULL,
        descripcion VARCHAR(255) NOT NULL,
        imagen LONGBLOB NULL,
        fechaInicio DATE NOT NULL,
        fechaTermino DATE NOT NULL,
        restricciones VARCHAR(255) NULL, -- PENDIENTE
        tipoPromocion INT NOT NULL,
        porcentajeDescuento INT NOT NULL,
        categoria INT NOT NULL,
        cuponesDisponibles INT NOT NULL,
        maximoCupones INT NOT NULL,
        codigoPromocion VARCHAR(8) UNIQUE NOT NULL,
        estatus INT NOT NULL,
        empresa INT NOT NULL,
        FOREIGN KEY (tipoPromocion) REFERENCES tipoPromocion(idTipoPromocion),
        FOREIGN KEY (categoria) REFERENCES categoriaPromocion(idCategoria),
        FOREIGN KEY (estatus) REFERENCES estatus(idEstatus),
        FOREIGN KEY (empresa) REFERENCES empresa(idEmpresa)
    );

CREATE TABLE
    usuario (
        idUsuario INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(50) NOT NULL,
        apellidoPaterno VARCHAR(50) NOT NULL,
        apellidoMaterno VARCHAR(50) NOT NULL,
        curp VARCHAR(18) NOT NULL UNIQUE,
        email VARCHAR(50) NOT NULL,
        username VARCHAR(20) NOT NULL UNIQUE,
        password VARCHAR(20) NOT NULL,
        rol INT NOT NULL,
        empresa INT NULL,
        FOREIGN KEY (rol) REFERENCES rol(idRol),
        FOREIGN KEY (empresa) REFERENCES empresa(idEmpresa)
    );

CREATE TABLE
    cliente(
        idCliente INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(50) NOT NULL,
        apellidoPaterno VARCHAR(50) NOT NULL,
        apellidoMaterno VARCHAR(50) NOT NULL,
        telefono VARCHAR(10) NOT NULL,
        email VARCHAR(50) NOT NULL UNIQUE,
        direccion INT NULL, -- PENDIENTE ver si debe tener dirreción a fuerza, o puede no tener
        fechaNacimiento DATE NOT NULL,
        password VARCHAR(20) NOT NULL,
        FOREIGN KEY (direccion) REFERENCES direccion(idDireccion)
    );

CREATE TABLE
    promocionSucursal (
        idSucursal INT NOT NULL,
        idPromocion INT NOT NULL,
        PRIMARY KEY (idSucursal, idPromocion),
        FOREIGN KEY (idSucursal) REFERENCES sucursal(idSucursal),
        FOREIGN KEY (idPromocion) REFERENCES promocion(idPromocion)
    );
