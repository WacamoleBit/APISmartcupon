USE smartcupon_db;

-- REGISTRAR CLIENTE CON DIRECCION

CREATE PROCEDURE registrarCliente(
	IN nombre VARCHAR(50), 
	IN apellidoPaterno VARCHAR(50), 
	IN apellidoMaterno VARCHAR(50), 
	IN telefono VARCHAR(50), 
	IN email VARCHAR(50), 
	IN fechaNacimiento VARCHAR(10), 
	IN password VARCHAR(20), 
	IN calle VARCHAR(100), 
	IN numero INT, 
	IN tipoDireccion INT, 
	INOUT filasAfectadas INT
) 
BEGIN 
	DECLARE direccionID INT;
	DECLARE filasDireccion INT;
	DECLARE filasCliente INT;
	START TRANSACTION;

	SELECT COUNT(*) INTO tipoDireccionExistente FROM tipodireccion WHERE tipoDireccion = idTipoDireccion;
	IF tipoDireccionExistente = 0 THEN
		SET filasAfectadas = -1;
		SIGNAL SQLSTATE 'Error SQL'
	END IF;
		
		INSERT INTO direccion (calle, numero, tipoDireccion)
		VALUES (calle, numero, tipoDireccion);
	
	SET filasDireccion = ROW_COUNT();
	
	IF filasDireccion > 0 THEN
	
		SET direccionID = LAST_INSERT_ID();
	
		INSERT INTO cliente (nombre,apellidoPaterno,apellidoMaterno,telefono,email,direccion,fechaNacimiento,password) 
		VALUES (nombre,apellidoPaterno,apellidoMaterno,telefono,email,direccionID,STR_TO_DATE(fechaNacimiento, '%Y-%m-%d'),password);

		SET filasCliente = ROW_COUNT();
		SET filasAfectadas = filasDireccion + filasCliente;
	ELSE
		SET filasAfectadas = -1;
	END IF;
	COMMIT;
END;


-- BUSCAR POR FILTRO

DELIMITER / /

CREATE PROCEDURE buscarPorFiltro(
	IN cadenaBusqueda VARCHAR(255), 
	IN porNombre BOOL, 
	IN porUsername BOOL, 
	IN porRol BOOL
) 
BEGIN 
	SELECT idUsuario,u.nombre as nombre,apellidoPaterno,apellidoMaterno,curp,u.email,username,password,r.nombre as rol,empresa
	FROM usuario u
	INNER JOIN rol r ON u.rol = r.idRol -- INNER JOIN empresa e ON u.empresa = e.idEmpresa
	WHERE (porNombre AND u.nombre LIKE CONCAT('%', cadenaBusqueda, '%'))
	OR (porUsername AND username LIKE CONCAT('%', cadenaBusqueda, '%'))
	OR (porRol AND r.nombre LIKE CONCAT('%', cadenaBusqueda, '%'));
END;


DROP PROCEDURE registrarEmpresa;

CREATE PROCEDURE registrarEmpresa(
	IN nombre VARCHAR(50), 
	IN nombreComercial VARCHAR(50), 
	IN logo LONGBLOB, 
	IN nombrePersona VARCHAR(50), 
	IN apellidoPaterno VARCHAR(50), 
	IN apellidoMaterno VARCHAR(50), 
	IN tipoPersona INT, 
	IN email VARCHAR(50), 
	IN calle VARCHAR(100), 
	IN numero INT, 
	IN codigoPostal VARCHAR(5), 
	IN ciudad INT, 
	IN tipoDireccion INT, 
	IN telefono VARCHAR(10), 
	IN paginaWeb VARCHAR(100), 
	IN rfc VARCHAR(12), 
	IN estatus INT, 
	INOUT filasAfectadas INT
) 
BEGIN 
	DECLARE direccionID INT;
	DECLARE personaID INT;
	DECLARE filasDireccion INT;
	DECLARE filasEmpresa INT;
	DECLARE filasPersona INT;
	DECLARE tipoPersonaExistente INT;
	DECLARE tipoDireccionExistente INT;
	
	START TRANSACTION;

	SELECT COUNT(*) INTO tipoPersonaExistente FROM tipopersona WHERE idTipoPersona = tipoPersona;
	
	IF tipoPersonaExistente = 0 THEN
		SET filasAfectadas = -1;
		SIGNAL SQLSTATE 'Error SQL';
	
	END IF;

	SELECT COUNT(*) INTO tipoDireccionExistente FROM tipodireccion WHERE idTipoDireccion = tipoDireccion;

	IF tipoDireccionExistente = 0 THEN
        SET filasAfectadas = -1;
        SIGNAL SQLSTATE 'Error SQL';
    END IF;

	INSERT INTO persona(nombre,apellidoPaterno,apellidoMaterno,tipoPersona)
	VALUES (nombrePersona,apellidoPaterno,apellidoMaterno,tipoPersona);
	
	SET filasPersona = ROW_COUNT();
	
	IF filasPersona > 0 THEN
        -- Obtener la ID de la persona recién creada
        SET personaID = LAST_INSERT_ID();
        
        -- Insertar la dirección
        INSERT INTO direccion(calle, numero, codigoPostal, ciudad, tipoDireccion)
        VALUES (calle, numero, codigoPostal, ciudad, tipoDireccion);

        SET filasDireccion = ROW_COUNT();
        SET direccionID = LAST_INSERT_ID();

        -- Verificar si se creó la dirección correctamente
        IF filasDireccion > 0 THEN
            -- Insertar la empresa
            INSERT INTO empresa(nombre, nombreComercial, logo, representante, email, direccion, telefono, paginaWeb, rfc, estatus)
            VALUES (nombre, nombreComercial, logo, personaID, email, direccionID, telefono, paginaWeb, rfc, estatus);

            SET filasEmpresa = ROW_COUNT();
            SET filasAfectadas = filasDireccion + filasPersona + filasEmpresa;
        ELSE
            SET filasAfectadas = -1;
            SIGNAL SQLSTATE '45000';
        END IF;
    ELSE
        SET filasAfectadas = -1;
        SIGNAL SQLSTATE '45000';
	END IF;
	COMMIT;
END;

CREATE PROCEDURE registrarSucursal(
	IN nombre VARCHAR(50), 
	IN telefono VARCHAR(10), 
	IN latitud DOUBLE, 
	IN longitud DOUBLE, 
	IN empresa INT,
	IN calle VARCHAR(100), 
	IN numero INT, 
	IN codigoPostal VARCHAR(5), 
	IN colonia VARCHAR(100), 
	IN ciudad INT, 
	IN nombrePersona VARCHAR(50), 
	IN apellidoPaterno VARCHAR(50), 
	IN apellidoMaterno VARCHAR(50),  
	INOUT filasAfectadas INT
) 
BEGIN 
	DECLARE direccionID INT;
	DECLARE personaID INT;
	DECLARE filasDireccion INT;
	DECLARE filasSucursal INT;
	DECLARE filasPersona INT;
	DECLARE empresaExistente INT;

	START TRANSACTION;



		INSERT INTO persona(nombre,apellidoPaterno,apellidoMaterno,tipoPersona)
		VALUES (nombrePersona,apellidoPaterno,apellidoMaterno,1);

		SET filasPersona = ROW_COUNT();
		SET personaID = LAST_INSERT_ID();

		IF filasPersona > 0 THEN

			INSERT INTO direccion(calle,numero,codigoPostal,colonia,ciudad,tipoDireccion)
			VALUES (calle,numero,codigoPostal,colonia,ciudad,2);

			SET filasDireccion = ROW_COUNT();
			SET direccionID = LAST_INSERT_ID();
			
			IF filasDireccion > 0 THEN
				
				SELECT COUNT(*) INTO empresaExistente FROM empresa WHERE empresa = empresa;

				IF empresaExistente > 0 THEN
					
					INSERT INTO sucursal(nombre,direccion,telefono,latitud,longitud,encargado, empresa)
					VALUES (nombre,direccionID,telefono,latitud,longitud,personaID,empresa);
	
					SET filasSucursal = ROW_COUNT();	
					SET filasAfectadas = filasSucursal + filasPersona + filasDireccion;
				ELSE
					ROLLBACK;
				END IF;
			ELSE
				SET filasAfectadas = -1;
				SIGNAL SQLSTATE '45000';
			END IF;
		ELSE
			SET filasAfectadas = -1;
			SIGNAL SQLSTATE '45000';
		END IF;
		
		COMMIT;
END;