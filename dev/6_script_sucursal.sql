-- SCRIPT Sucursal

USE smartcupon_db;

DROP PROCEDURE IF EXISTS registrarSucursal;
DROP PROCEDURE IF EXISTS modificarSucursal;
DROP PROCEDURE IF EXISTS eliminarSucursal;
DROP TRIGGER IF EXISTS delete_sucursal_trigger;

DELIMITER //

-- INSERT

CREATE PROCEDURE registrarSucursal (
    IN _nombreEncargado VARCHAR(50), 
    IN _apellidoPaterno VARCHAR(50), 
    IN _apellidoMaterno VARCHAR(50), 
    IN _calle VARCHAR(100), 
    IN _numero INT, 
    IN _codigoPostal VARCHAR(5), 
    IN _colonia VARCHAR(100), 
    IN _ciudad INT, 
    IN _empresa INT,
    IN _nombre VARCHAR (50), 
    IN _telefono VARCHAR(10), 
    IN _latitud DOUBLE, 
    IN _longitud DOUBLE, 
    INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
) 
BEGIN 
	SET @direccionID = 0;
	SET @personaID = 0;
	SET @filasDireccion = 0;
	SET @filasSucursal = 0;
	SET @filasPersona = 0;
    SET _filasAfectadas = 0;
    SET _error = "";
    
    SET @direccion = 0;
    SET @encargado = 0;
    SET @sucursal = 0;
    
    BEGIN
		 -- Verificar existencia del representante
        SELECT COUNT(*) INTO @encargado FROM persona 
        WHERE nombre = _nombreEncargado AND apellidoPaterno = _apellidoPaterno AND apellidoMaterno = _apellidoMaterno AND tipoPersona = 1;
		
        IF @representante > 0 THEN
            ROLLBACK;
            SET _error = CONCAT(_error, 'El representante ya existe en la base de datos.\n');
        ELSE
            -- Insertar en la tabla persona
            INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, tipoPersona)
            VALUES (_nombreEncargado, _apellidoPaterno, _apellidoMaterno, 1);
                
            SET @filasPersona = ROW_COUNT();
            SET @personaID = LAST_INSERT_ID();
        END IF;
    END;
    
	BEGIN
		IF NOT EXISTS (SELECT * FROM ciudad WHERE idCiudad = _ciudad) THEN
            ROLLBACK;
            SET _error = CONCAT(_error, 'La ciudad especificada no está registrada en la base de datos.\n');
		ELSE
			SELECT COUNT(*) INTO @direccion FROM direccion 
			WHERE calle = _calle AND numero = _numero AND codigoPostal = _codigoPostal AND colonia = _colonia AND ciudad = _ciudad AND tipoDireccion = 1;
			
			IF @direccion > 0 THEN
				ROLLBACK;
				SET _error = CONCAT(_error, 'La dirección ya existe en la base de datos.\n');
			ELSE 
				INSERT INTO direccion(calle, numero, codigoPostal, colonia, ciudad, tipoDireccion)
				VALUES (_calle, _numero, _codigoPostal, _colonia, _ciudad, 2);
				
				SET @filasDireccion = ROW_COUNT();
				SET @direccionID = LAST_INSERT_ID();
			END IF;
        END IF;
    END;
    
    BEGIN
		SELECT COUNT(*) INTO @sucursal FROM sucursal 
        WHERE empresa = _empresa AND nombre =_nombre AND telefono = _telefono AND latitud = _latitud AND longitud = _longitud;
        
        IF @sucursal > 0 THEN
			ROLLBACK;
            SET _error = CONCAT(_error, 'La sucursal ya existe en la base de datos.\n');
        ELSE
			INSERT INTO sucursal(nombre, direccion, telefono, latitud, longitud, encargado, empresa)
			VALUES (_nombre, @direccionID, _telefono, _latitud, _longitud, @personaID, _empresa);
			
			SET @filasSucursal = ROW_COUNT();
			SET _filasAfectadas = @filasSucursal + @filasPersona + @filasDireccion;
        END IF;
    END;
END //

-- UPDATE

CREATE PROCEDURE modificarSucursal(
    IN _idSucursal INT,
    IN _nombre VARCHAR(50), 
    IN _telefono VARCHAR(10), 
    IN _latitud DOUBLE, 
    IN _longitud DOUBLE, 
	IN _empresa INT,
	IN _idPersona INT,
	IN _nombreEncargado VARCHAR(50),
	IN _apellidoPaterno VARCHAR(50),
	IN _apellidoMaterno VARCHAR(50),
	IN _idDireccion INT,
	IN _calle VARCHAR(100),
	IN _numero INT,
	IN _codigoPostal VARCHAR(5),
	IN _colonia VARCHAR(100),
	IN _ciudad INT,
    
	INOUT _filasAfectadas INT,
	INOUT _error VARCHAR(255)
) 
BEGIN 

    SET @filasSucursal = 0;
	SET @filasDireccion = 0;
	SET @filasPersona = 0;
    SET _filasAfectadas = 0;
	SET _error = "";
    
	SET @sucursal = 0;
	SET @encargado = 0;
	SET @direccion = 0;

	START TRANSACTION;

	BEGIN
        
        IF EXISTS (SELECT * FROM sucursal WHERE idSucursal = _idSucursal) THEN
			SET @direccion = 0;
			SET @encargado = 0;
			
			SET @direccionActual = 0;
			SET @encargadoActual = 0;
            
            SELECT s.direccion INTO @direccionActual FROM sucursal s where idSucursal = _idSucursal;
			SELECT s.encargado INTO @encargadoActual FROM sucursal s where idSucursal = _idSucursal;
            
            SELECT COUNT(*) INTO @direccion FROM direccion WHERE idDireccion = IFNULL(_idDireccion, @direccionActual);
			IF @direccion = 0 THEN
				SET _error = CONCAT(_error, 'La dirección no existe en la base de datos');
			END IF;
			
			SELECT COUNT(*) INTO @encargado FROM persona WHERE idPersona = IFNULL(_idPersona, @encargadoActual);
			IF @encargado = 0 THEN
				SET _error = CONCAT(_error, 'El encargado no existe en la base de datos');
			END IF;
            
            IF @direccion > 0 AND @encargado > 0 THEN
				UPDATE persona SET 
					nombre=IFNULL(_nombreEncargado, nombre),
					apellidoPaterno=IFNULL(_apellidoPaterno, apellidoPaterno),
					apellidoMaterno=IFNULL(_apellidoMaterno, apellidoMaterno)
				WHERE idPersona=_idPersona;

				SET @filasPersona = ROW_COUNT();
                
                UPDATE direccion  SET 
					calle=IFNULL(_calle, calle),
					numero=IFNULL(_numero, numero),
					codigoPostal=IFNULL(_codigoPostal, codigoPostal),
					colonia = IFNULL(_colonia, colonia),
					ciudad = IFNULL(_ciudad, ciudad)
				WHERE idDireccion = _idDireccion;

				SET @filasDireccion = ROW_COUNT();
				
                UPDATE sucursal SET 
					nombre=IFNULL(_nombre, nombre),
					telefono=IFNULL(_telefono, telefono),
					latitud=IFNULL(_latitud, latitud),
					longitud=IFNULL(_longitud, longitud),
					empresa=IFNULL(_empresa, empresa)
				WHERE idSucursal = _idSucursal;

				SET @filasSucursal = ROW_COUNT();
			END IF;
            
        ELSE 
			SET _error = CONCAT(_error, 'La sucursal no existe en la base de datos');
        END IF;
        
        SET _filasAfectadas = @filasPersona + @filasSucursal + @filasDireccion;
	END;

	COMMIT;
END//

-- DELETE

CREATE TRIGGER delete_sucursal_trigger
AFTER DELETE ON sucursal FOR EACH ROW
BEGIN
    DELETE FROM direccion WHERE idDireccion = OLD.direccion;
    DELETE FROM persona WHERE idPersona = OLD.encargado;
END; //

CREATE PROCEDURE eliminarSucursal (
	IN _idSucursal INT,
    
    INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
)
BEGIN
	SET _filasAfectadas = 0;
    SET _error = '';
    
	IF NOT EXISTS (SELECT * FROM sucursal WHERE idSucursal = _idSucursal) THEN
		SET _error = CONCAT(_error, "No existe la sucursal proporcionada");
	ELSE
		SET @sucursal = 0;
        
		IF EXISTS (SELECT * FROM promocionsucursal WHERE idSucursal = _idSucursal) THEN
			SET _error = CONCAT(_error, "La sucursal tiene promociones asociadas.");
        ELSE
			DELETE FROM sucursal WHERE idSucursal = _idSucursal;
            
            SET _filasAfectadas = ROW_COUNT();
        END IF;
    END IF;
END //

DELIMITER ;