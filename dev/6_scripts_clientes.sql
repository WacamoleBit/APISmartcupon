-- SCRIPTS Clientes

DELIMITER //

-- INSERT

CREATE PROCEDURE registrarCliente (
	IN _nombre VARCHAR(50), 
	IN _apellidoPaterno VARCHAR(50), 
	IN _apellidoMaterno VARCHAR(50), 
	IN _telefono VARCHAR(50), 
	IN _fechaNacimiento VARCHAR(10), 
    IN _email VARCHAR(50), 
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
				VALUES (_nombre, _apellidoPaterno, _apellidoMaterno, _telefono, _email, @direccionID, STR_TO_DATE(_fechaNacimiento, '%Y-%m-%d'), password);

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

-- ELIMINAR

DELIMITER ;