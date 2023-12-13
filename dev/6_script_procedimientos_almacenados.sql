USE smartcupon_db;

-- REGISTRAR CLIENTE CON DIRECCION

CREATE PROCEDURE REGISTRARCLIENTE(IN NOMBRE VARCHAR
(50), IN APELLIDOPATERNO VARCHAR(50), IN APELLIDOMATERNO 
VARCHAR(50), IN TELEFONO VARCHAR(50), IN EMAIL VARCHAR
(50), IN FECHANACIMIENTO VARCHAR(10), IN PASSWORD 
VARCHAR(20), IN CALLE VARCHAR(100), IN NUMERO INT, 
IN TIPODIRECCION INT, INOUT FILASAFECTADAS INT) BEGIN 
DECLARE 
	DECLARE DECLARE direccionID INT;
	DECLARE filasDireccion INT;
	DECLARE filasCliente INT;
	START TRANSACTION;
	INSERT INTO
	    direccion (calle, numero, tipoDireccion)
	VALUES (calle, numero, tipoDireccion);
	SET filasDireccion = ROW_COUNT();
	SET direccionID = LAST_INSERT_ID();
	INSERT INTO
	    cliente (
	        nombre,
	        apellidoPaterno,
	        apellidoMaterno,
	        telefono,
	        email,
	        direccion,
	        fechaNacimiento,
	        password
	    )
	VALUES (
	        nombre,
	        apellidoPaterno,
	        apellidoMaterno,
	        telefono,
	        email,
	        direccionID,
	        STR_TO_DATE(fechaNacimiento, '%Y-%m-%d'),
	        password
	    );
	SET filasCliente = ROW_COUNT();
	SET filasAfectadas = filasDireccion + filasCliente;
	COMMIT;
	END;


-- BUSCAR POR FILTRO

DELIMITER / /

CREATE PROCEDURE BUSCARPORFILTRO(IN CADENABUSQUEDA 
VARCHAR(255), IN PORNOMBRE BOOL, IN PORUSERNAME BOOL
, IN PORROL BOOL) BEGIN SELECT 
	SELECT
	SELECT
	    idUsuario,
	    u.nombre as nombre,
	    apellidoPaterno,
	    apellidoMaterno,
	    curp,
	    u.email,
	    username,
	    password,
	    r.nombre as rol,
	    empresa
	FROM usuario u
	    INNER JOIN rol r ON u.rol = r.idRol -- INNER JOIN empresa e ON u.empresa = e.idEmpresa
	WHERE (
	        porNombre
	        AND u.nombre LIKE CONCAT('%', cadenaBusqueda, '%')
	    )
	    OR (
	        porUsername
	        AND username LIKE CONCAT('%', cadenaBusqueda, '%')
	    )
	    OR (
	        porRol
	        AND r.nombre LIKE CONCAT('%', cadenaBusqueda, '%')
	    );
	END;


CREATE PROCEDURE REGISTRARCLIENTE(IN NOMBRE VARCHAR
(50), IN APELLIDOPATERNO VARCHAR(50), IN APELLIDOMATERNO 
VARCHAR(50), IN TELEFONO VARCHAR(50), IN EMAIL VARCHAR
(50), IN FECHANACIMIENTO VARCHAR(10), IN PASSWORD 
VARCHAR(20), IN CALLE VARCHAR(100), IN NUMERO INT, 
IN TIPODIRECCION INT, INOUT FILASAFECTADAS INT) BEGIN 
DECLARE 
	DECLARE DECLARE direccionID INT;
	DECLARE filasDireccion INT;
	DECLARE filasCliente INT;
	START TRANSACTION;
	INSERT INTO
	    direccion (calle, numero, tipoDireccion)
	VALUES (calle, numero, tipoDireccion);
	SET filasDireccion = ROW_COUNT();
	SET direccionID = LAST_INSERT_ID();
	INSERT INTO
	    cliente (
	        nombre,
	        apellidoPaterno,
	        apellidoMaterno,
	        telefono,
	        email,
	        direccion,
	        fechaNacimiento,
	        password
	    )
	VALUES (
	        nombre,
	        apellidoPaterno,
	        apellidoMaterno,
	        telefono,
	        email,
	        direccionID,
	        STR_TO_DATE(fechaNacimiento, '%Y-%m-%d'),
	        password
	    );
	SET filasCliente = ROW_COUNT();
	SET filasAfectadas = filasDireccion + filasCliente;
	COMMIT;
	END;


DROP PROCEDURE registrarEmpresa;

CREATE PROCEDURE REGISTRAREMPRESA(IN NOMBRE VARCHAR
(50), IN NOMBRECOMERCIAL VARCHAR(50), IN LOGO LONGBLOB
, IN NOMBREPERSONA VARCHAR(50), IN APELLIDOPATERNO 
VARCHAR(50), IN APELLIDOMATERNO VARCHAR(50), IN TIPOPERSONA 
INT, IN EMAIL VARCHAR(50), IN CALLE VARCHAR(100), 
IN NUMERO INT, IN CODIGOPOSTAL VARCHAR(5), IN CIUDAD 
INT, IN TIPODIRECCION INT, IN TELEFONO VARCHAR(10)
, IN PAGINAWEB VARCHAR(100), IN RFC VARCHAR(12), IN 
ESTATUS INT, INOUT FILASAFECTADAS INT) BEGIN DECLARE 
	DECLARE direccionID INT;
	DECLARE personaID INT;
	DECLARE filasDireccion INT;
	DECLARE filasEmpresa INT;
	DECLARE filasPersona INT;
	START TRANSACTION;
	INSERT INTO
	    persona(
	        nombre,
	        apellidoPaterno,
	        apellidoMaterno,
	        tipoPersona
	    )
	VALUES (
	        nombrePersona,
	        apellidoPaterno,
	        apellidoMaterno,
	        tipoPersona
	    );
	SET filasPersona = ROW_COUNT();
	SET personaID = LAST_INSERT_ID();
	INSERT INTO
	    direccion (
	        calle,
	        numero,
	        codigoPostal,
	        ciudad,
	        tipoDireccion
	    )
	VALUES (
	        calle,
	        numero,
	        codigoPostal,
	        ciudad,
	        tipoDireccion
	    );
	SET filasDireccion = ROW_COUNT();
	SET direccionID = LAST_INSERT_ID();
	INSERT INTO
	    empresa (
	        nombre,
	        nombreComercial,
	        logo,
	        representante,
	        email,
	        direccion,
	        telefono,
	        paginaWeb,
	        rfc,
	        estatus
	    )
	VALUES (
	        nombre,
	        nombreComercial,
	        logo,
	        personaID,
	        email,
	        direccionID,
	        telefono,
	        paginaWeb,
	        rfc,
	        estatus
	    );
	SET filasEmpresa = ROW_COUNT();
	SET filasAfectadas = filasDireccion + filasPersona + filasEmpresa;
	COMMIT;
	END;


CREATE PROCEDURE REGISTRARSUCURSAL(IN NOMBRE VARCHAR
(50), IN CALLE VARCHAR(100), IN NUMERO INT, IN CODIGOPOSTAL 
VARCHAR(5), IN COLONIA VARCHAR(100), IN CIUDAD INT
, IN TIPODIRECCION INT, IN TELEFONO VARCHAR(10), IN 
LATITUD DOUBLE, IN LONGITUD DOUBLE, IN NOMBREPERSONA 
VARCHAR(50), IN APELLIDOPATERNO VARCHAR(50), IN APELLIDOMATERNO 
VARCHAR(50), IN TIPOPERSONA INT, INOUT FILASAFECTADAS 
INT) BEGIN DECLARE 
	DECLARE direccionID INT;
	DECLARE personaID INT;
	DECLARE filasDireccion INT;
	DECLARE filasSucursal INT;
	DECLARE filasPersona INT;
	START TRANSACTION;
	INSERT INTO
	    direccion(
	        calle,
	        numero,
	        codigoPostal,
	        colonia,
	        ciudad,
	        tipoDireccion
	    )
	VALUES (
	        calle,
	        numero,
	        codigoPostal,
	        colonia,
	        ciudad,
	        tipoDireccion
	    );
	SET filasDireccion = ROW_COUNT();
	SET direccionID = LAST_INSERT_ID();
	INSERT INTO
	    persona(
	        nombre,
	        apellidoPaterno,
	        apellidoMaterno,
	        tipoPersona
	    )
	VALUES (
	        nombrePersona,
	        apellidoPaterno,
	        apellidoMaterno,
	        tipoPersona
	    );
	SET filasPersona = ROW_COUNT();
	SET personaID = LAST_INSERT_ID();
	INSERT INTO
	    sucursal(
	        nombre,
	        direccion,
	        telefono,
	        latitud,
	        longitud,
	        encargado
	    )
	VALUES (
	        nombre,
	        direccionID,
	        telefono,
	        latitud,
	        longitud,
	        personaID
	    );
	SET filasSucursal = ROW_COUNT();
	SET
	    filasAfectadas = filasSucursal + filasPersona + filasDireccion;
	COMMIT;
	END;
