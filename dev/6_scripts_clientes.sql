-- SCRIPTS Clientes

USE smartcupon_db;

DROP PROCEDURE IF EXISTS registrarCliente;
DROP PROCEDURE IF EXISTS modificarCliente;
DROP PROCEDURE IF EXISTS buscarPorFiltro;

DELIMITER //

-- INSERT

CREATE PROCEDURE registrarCliente (
	IN _nombre VARCHAR(50), 
	IN _apellidoPaterno VARCHAR(50), 
	IN _apellidoMaterno VARCHAR(50), 
	IN _telefono VARCHAR(50), 
	IN _email VARCHAR(50),
	IN _fechaNacimiento VARCHAR(10), 
	IN _password VARCHAR(20), 
	IN _calle VARCHAR(100), 
	IN _numero INT,
	INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
) 
BEGIN 
	SET @direccionID = 0;
	SET @filasDireccion = 0;
	SET @filasCliente = 0;
    SET _filasAfectadas = 0;
    SET _error = '';
    
	START TRANSACTION;
    
		IF EXISTS (SELECT * FROM cliente WHERE email = _email) THEN
			ROLLBACK;
			SET _error = CONCAT(_error, 'El correo electronico ya ha sido tomado.\n');
		ELSE 
			INSERT INTO direccion (calle, numero, tipoDireccion)
			VALUES (_calle, _numero, 4);
			
			SET @filasDireccion = ROW_COUNT();
			
			IF @filasDireccion > 0 THEN
				SET @direccionID = LAST_INSERT_ID();
			
				INSERT INTO cliente (nombre, apellidoPaterno, apellidoMaterno ,telefono, email, direccion, fechaNacimiento, password) 
				VALUES (_nombre, _apellidoPaterno, _apellidoMaterno, _telefono, _email, @direccionID, STR_TO_DATE(_fechaNacimiento, '%Y-%m-%d'), _password);

				SET @filasCliente = ROW_COUNT();
				SET _filasAfectadas = @filasDireccion + @filasCliente;
			ELSE
				ROLLBACK;
				SET _error = CONCAT(_error, 'Error al registrar la dirección.\n');
			END IF;
        END IF;
	
	COMMIT;
END //

-- UPDATE
CREATE PROCEDURE modificarCliente(
	IN _idCliente INT, 
	IN _nombre VARCHAR(50),
	IN _apellidoPaterno VARCHAR(50),
	IN _apellidoMaterno VARCHAR(50),
	IN _telefono VARCHAR(10),
	IN _fechaNacimiento DATE,
	IN _password VARCHAR(20),
	IN _idDireccion INT,
	IN _calle VARCHAR(100),
	IN _numero INT,

	INOUT _filasAfectadas INT,
	INOUT _error VARCHAR(255)
)
BEGIN
	SET @filasCliente = 0;
	SET @filasDireccion = 0;
	SET _error = '';
	SET _filasAfectadas = 0;

	START TRANSACTION;
		BEGIN
			IF EXISTS (SELECT * FROM cliente WHERE idCliente = _idCliente) THEN
		
				IF NOT EXISTS (SELECT * FROM cliente WHERE nombre=_nombre AND apellidoPaterno=_apellidoPaterno AND apellidoMaterno = _apellidoMaterno AND telefono=_telefono) THEN
					UPDATE cliente SET 
					nombre=IFNULL(_nombre, nombre),
					apellidoPaterno=IFNULL(_apellidoPaterno, apellidoPaterno),
					apellidoMaterno=IFNULL(_apellidoMaterno, apellidoMaterno),
					telefono=IFNULL(_telefono, telefono),
					fechaNacimiento=IFNULL(_fechaNacimiento, fechaNacimiento),
					password=IF(_password IS NOT NULL AND LENGTH(_password) > 8, _password, password)
					WHERE idCliente=_idCliente;

					SET @filasCliente = ROW_COUNT();
				
				ELSE
					ROLLBACK;
					SET _error = CONCAT(_error, 'El cliente ya existe en la base de datos. \n');
				END IF;
			ELSE
				ROLLBACK;
				SET _error = CONCAT(_error, 'El cliente ingresado no existe a en la base de datos');
			END IF;
		END;

		BEGIN
			IF EXISTS (SELECT * FROM direccion WHERE idDireccion = _idDireccion) THEN 

				IF NOT EXISTS (SELECT * FROM direccion WHERE calle=_calle AND numero=_numero) THEN 
					UPDATE direccion SET 
					calle=IFNULL(_calle, calle),
					numero=IFNULL(_numero, numero)
					WHERE idDireccion=_idDireccion;
				
					SET @filasDireccion = ROW_COUNT();

				ELSE
					ROLLBACK;
					SET _error = CONCAT(_error, 'La direccion ya existe en la base de datos. \n');
				END IF;
			ELSE
				ROLLBACK;
				SET _error = CONCAT(_error, 'La dirección ingresada no existe en la base de datos. \n');
			END IF;
		END;

	SET _filasAfectadas = @filasDireccion + @filasCliente;

	COMMIT;
END;
-- ELIMINAR

-- BUSCAR POR FILTRO

CREATE PROCEDURE buscarPorFiltro(
	IN cadenaBusqueda VARCHAR(255), 
	IN porNombre BOOL, 
	IN porUsername BOOL, 
	IN porRol BOOL
) 
BEGIN 
	SELECT idUsuario, u.nombre as nombre, apellidoPaterno, apellidoMaterno, curp, u.email, username, password, u.rol as rol, r.nombre as nombreRol, empresa, e.nombre as nombreEmpresa
	FROM usuario u
	INNER JOIN rol r ON u.rol = r.idRol 
    LEFT JOIN empresa e ON u.empresa = e.idEmpresa
	WHERE (porNombre AND u.nombre LIKE CONCAT('%', cadenaBusqueda, '%'))
	OR (porUsername AND username LIKE CONCAT('%', cadenaBusqueda, '%'))
	OR (porRol AND r.nombre LIKE CONCAT('%', cadenaBusqueda, '%'));
END //

DELIMITER ;