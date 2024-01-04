-- SCRIPTS Empresa

USE smartcupon_db;

DROP PROCEDURE IF EXISTS registrarEmpresa;
DROP PROCEDURE IF EXISTS modificarEmpresa;
DROP PROCEDURE IF EXISTS eliminarEmpresa;
DROP TRIGGER IF EXISTS delete_empresa_trigger;

DELIMITER //

-- INSERT

CREATE PROCEDURE registrarEmpresa (
    IN _nombre VARCHAR(50),
    IN _nombreComercial VARCHAR(50),
    IN _logo LONGBLOB,
    IN _email VARCHAR(50),
    IN _telefono VARCHAR(10),
    IN _paginaWeb VARCHAR(100),
    IN _rfc VARCHAR(13),
    IN _nombreRepresentante VARCHAR(50),
    IN _apellidoPaterno VARCHAR(50),
    IN _apellidoMaterno VARCHAR(50),
    IN _calle VARCHAR(100),
    IN _numero INT,
    IN _codigoPostal VARCHAR(5),
    IN _ciudad INT,
    
    INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
)
BEGIN
    SET @direccionID = 0;
    SET @personaID = 0;
    SET @filasDireccion = 0;
    SET @filasEmpresa = 0;
    SET @filasPersona = 0;
    SET _filasAfectadas = 0;
    SET _error = "";

    -- Inicializar variable de conteo
    SET @representante = 0;
    SET @direccion = 0;
    SET @empresa = 0;

    START TRANSACTION; 

    BEGIN
        -- Verificar existencia del representante
        SELECT COUNT(*) INTO @representante FROM persona 
        WHERE nombre = _nombreRepresentante AND apellidoPaterno = _apellidoPaterno AND apellidoMaterno = _apellidoMaterno;
		
        IF @representante > 0 THEN
            ROLLBACK;
            SET _error = CONCAT(_error, 'El representante ya existe en la base de datos.\n');
        ELSE
            -- Insertar en la tabla persona
            INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, tipoPersona)
            VALUES (_nombreRepresentante, _apellidoPaterno, _apellidoMaterno, 2);
                
            SET @filasPersona = ROW_COUNT();
            SET @personaID = LAST_INSERT_ID();
        END IF;
    END;

    BEGIN 
		-- Verificar existencia de la ciudad
        IF NOT EXISTS (SELECT * FROM ciudad WHERE idCiudad = _ciudad) THEN
            ROLLBACK;
            SET _error = CONCAT(_error, 'La ciudad especificada no está registrada en la base de datos.\n');
		ELSE
			-- Verificar existencia de la direccion
			SELECT COUNT(*) INTO @direccion FROM direccion 
			WHERE calle = _calle AND numero = _numero AND codigoPostal = _codigoPostal AND ciudad = _ciudad AND tipoDireccion = 2;
			
			IF @direccion > 0 THEN
				ROLLBACK;
				SET _error = CONCAT(_error, 'La dirección ya existe en la base de datos.\n');
			 ELSE
				-- Insertar en la tabla dirección
				INSERT INTO direccion (calle, numero, codigoPostal, ciudad, tipoDireccion)
				VALUES (_calle, _numero, _codigoPostal, _ciudad, 2);
			
				SET @filasDireccion = ROW_COUNT();
				SET @direccionID = LAST_INSERT_ID();
			END IF;
        END IF;
    END;

    BEGIN
		 -- Verificar existencia de la empresa
        SELECT COUNT(*) INTO @empresa FROM empresa 
        WHERE nombre = _nombre AND nombreComercial = _nombreComercial AND email = _email AND telefono = _telefono AND rfc = _rfc;

        IF @empresa > 0 THEN
            ROLLBACK;
            SET _error = CONCAT(_error, 'La empresa ya existe en la base de datos.\n');
		ELSE
			-- Verificar que las inserciones anteriores fueron exitosas
			IF (@personaID < 1) OR (@direccionID < 1) THEN
				ROLLBACK;
				SET _ERROR = CONCAT(_error, 'Error al registrar los datos de la dirección o el representante.\n');
			ELSE
				-- Insertar en la tabla empresa
				INSERT INTO empresa (nombre, nombreComercial, logo, representante, email, direccion, telefono, paginaWeb, rfc, estatus)
				VALUES (_nombre, _nombreComercial, _logo, @personaID, _email, @direccionID, _telefono, _paginaWeb, _rfc, 1);
					
				SET @filasEmpresa = ROW_COUNT();
				SET _filasAfectadas = @filasDireccion + @filasPersona + @filasEmpresa;
			END IF;
        END IF;
    END;

    COMMIT;
END //

-- UPDATE

CREATE PROCEDURE modificarEmpresa(
	IN _idEmpresa INT,
	IN _nombre VARCHAR(50),
	IN _nombreComercial VARCHAR(100),
	IN _logo LONGBLOB,
	IN _email VARCHAR(50),
	IN _telefono VARCHAR(10),
	IN _paginaWeb VARCHAR(50),
	IN _estatus INT,
	IN _idDireccion INT,
	IN _calle VARCHAR(50),
	IN _numero INT,
	IN _codigoPostal VARCHAR(5),
	IN _ciudad INT,
	IN _idPersona INT,
	IN _nombreEncargado VARCHAR (50),
	IN _apellidoPaterno VARCHAR (50),
	IN _apellidoMaterno VARCHAR (50),

	INOUT _filasAfectadas INT,
	INOUT _error VARCHAR(255)
)
BEGIN

	SET @filasEncargado = 0;
	SET @filasDireccion = 0;
	SET @filasEmpresa = 0;
	SET _filasAfectadas = 0;
	SET _error = '';

	START TRANSACTION;
		BEGIN

			IF EXISTS (SELECT * FROM persona WHERE idPersona = _idPersona) THEN 

				IF NOT EXISTS (SELECT * FROM persona WHERE nombre= _nombreEncargado AND apellidoPaterno= _apellidoPaterno AND apellidoMaterno= _apellidoMaterno AND idPersona!=idPersona) THEN 
					UPDATE persona SET 
					nombre=IFNULL(_nombreEncargado, nombre),
					apellidoPaterno=IFNULL(_apellidoPaterno, apellidoPaterno),
					apellidoMaterno = IFNULL(_apellidoMaterno, apellidoMaterno)
					WHERE idPersona= _idPersona;

					SET @filasPersona = ROW_COUNT();

				ELSE
        			ROLLBACK;
        			SET _error=CONCAT (_error, 'El encargado ya existe en la base de datos. \n ');
				END IF;

			ELSE
    			ROLLBACK;
    			SET _error = CONCAT(_error, 'La empresa no existe en la base de datos \n.');
			END IF;
		END;

		BEGIN 

			IF EXISTS (SELECT * FROM direccion WHERE idDireccion = _idDireccion) THEN 
				IF NOT EXISTS (SELECT * FROM direccion WHERE calle=_calle AND numero = _numero AND idDireccion != _idDireccion) THEN 
					UPDATE direccion SET 
					calle= IFNULL(_calle, calle),
					numero=IFNULL(_numero, numero),
					codigoPostal= IFNULL(_codigoPostal, codigoPostal),
					ciudad=IFNULL(_ciudad, ciudad)
					WHERE idDireccion = _idDireccion;

					SET @filasDireccion = ROW_COUNT();

				ELSE 
      				ROLLBACK;
      				SET _error = CONCAT(_error, 'La dirección ya existe en la base de datos. \n');
				END IF;
			ELSE 
				ROLLBACK;
				SET _error = CONCAT(_error, 'La dirección ingresada no existe en la base de datos \n');
			END IF;
		END;
	

		BEGIN 

			IF EXISTS (SELECT * FROM empresa WHERE idEmpresa = _idEmpresa) THEN 
 				IF NOT EXISTS (SELECT * FROM empresa WHERE nombre=_nombre AND nombreComercial = _nombreComercial AND email=_email AND telefono=_telefono AND paginaWeb=_paginaWeb AND idEmpresa!=_idEmpresa) THEN 
					UPDATE empresa SET 
					nombre=IFNULL(_nombre, nombre),
					nombreComercial=IFNULL(_nombreComercial, nombreComercial),
					logo=IFNULL(_logo, logo),
					email=IFNULL(_email, email),
					telefono=IFNULL(_telefono, telefono),
					paginaWeb=IFNULL(_paginaWeb, paginaWeb),
					estatus=IFNULL(_estatus, estatus)
					WHERE idEmpresa= _idEmpresa;

					SET @filasEmpresa = ROW_COUNT();
				ELSE
					ROLLBACK;
					SET _error= CONCAT(_error, 'La empresa ya existe en la base de datos. \n');
				END IF;
			ELSE
				ROLLBACK;
				SET _error= CONCAT(_error, 'La empresa no existe en la base de datos. \n');
			END IF;
		END;
	SET _filasAfectadas = @filasEmpresa + @filasPersona + @filasDireccion;

	COMMIT;
END//

-- ELIMINAR

CREATE TRIGGER delete_empresa_trigger
AFTER DELETE ON empresa FOR EACH ROW
BEGIN
    DELETE FROM direccion WHERE idDireccion = OLD.direccion;
    DELETE FROM persona WHERE idPersona = OLD.representante;
END; //

CREATE PROCEDURE eliminarEmpresa (
	IN _idEmpresa INT,
    
    INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
)
BEGIN
    SET _filasAfectadas = 0;
    SET _error = '';
    
    IF NOT EXISTS (SELECT * FROM empresa WHERE idEmpresa = _idEmpresa) THEN
		SET _error = CONCAT(_error, "No existe la empresa proporcionada");
	ELSE
		IF EXISTS(SELECT * FROM promocion WHERE empresa = _idEmpresa) THEN
			SET _error = CONCAT(_error, "La empresa tiene promociones asociadas");
		ELSE
			IF EXISTS (SELECT * FROM sucursal WHERE empresa = _idEmpresa) THEN
				SET _error = "La empresa tiene sucursales asociadas.";
			ELSE
				DELETE FROM empresa WHERE idEmpresa = _idEmpresa;
            
            		SET _filasAfectadas = ROW_COUNT();
        	END IF;
	END IF;
    END IF;
END //

DELIMITER ;