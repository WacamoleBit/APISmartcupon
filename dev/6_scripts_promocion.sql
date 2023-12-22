-- SCRIPT promocion

USE smartcupon_db;

DROP PROCEDURE IF EXISTS registrarPromocion;

DELIMITER //

-- INSERT

CREATE PROCEDURE registrarPromocion(
    IN _nombre VARCHAR(50),
    IN _descripcion VARCHAR(255),
    IN _imagen LONGBLOB,
    IN _fechaInicio DATE,
    IN _fechaTermino DATE,
    IN _restricciones VARCHAR(255),
    IN _tipoPromocion INT,
    IN _porcentajeDescuento INT,
    IN _categoria INT,
    IN _cuponesDisponibles INT,
    IN _codigoPromocion VARCHAR(8),
    IN _empresa INT,

    INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
)
BEGIN
    DECLARE _existEmpresa INT;
    DECLARE _existTipoPromocion INT;
    DECLARE _existCategoria INT;
    DECLARE _existEstatus INT;

    -- Inicializar variables
    SET _filasAfectadas = 0;
    SET _error = "";

    -- Verificar existencia de la empresa
    SELECT COUNT(*) INTO _existEmpresa FROM empresa WHERE idEmpresa = _empresa;
    IF _existEmpresa = 0 THEN
		ROLLBACK;
        SET _error = "La empresa especificada no existe en la base de datos.";
    END IF;

    -- Verificar existencia del tipo de promoción
    SELECT COUNT(*) INTO _existTipoPromocion FROM tipoPromocion WHERE idTipoPromocion = _tipoPromocion;
    IF _existTipoPromocion = 0 THEN
		ROLLBACK;
        SET _error = "El tipo de promoción especificado no existe en la base de datos.";
    END IF;

    -- Verificar existencia de la categoría
    SELECT COUNT(*) INTO _existCategoria FROM categoriaPromocion WHERE idCategoria = _categoria;
    IF _existCategoria = 0 THEN
		ROLLBACK;
        SET _error = "La categoría especificada no existe en la base de datos.";
    END IF;
    
	-- Iniciar transacción
    START TRANSACTION;
    
    IF _existEmpresa > 0 AND _existTipoPromocion > 0 AND _existCategoria > 0 THEN 
		-- Insertar en la tabla promocion
		BEGIN
			INSERT INTO promocion (
				nombre, descripcion, imagen, fechaInicio, fechaTermino, restricciones,
				tipoPromocion, porcentajeDescuento, categoria, cuponesDisponibles, codigoPromocion,
				estatus, empresa
			)
			VALUES (
				_nombre, _descripcion, _imagen, _fechaInicio, _fechaTermino, _restricciones,
				_tipoPromocion, _porcentajeDescuento, _categoria, _cuponesDisponibles, _codigoPromocion,
				1, _empresa
			);

			SET _filasAfectadas = ROW_COUNT();

			IF _filasAfectadas = 1 THEN
				COMMIT;
			ELSE
				ROLLBACK;
				SET _error = "Error al insertar la promoción en la base de datos.";
			END IF;
		END;
	END IF;
    
END //

-- UPDATE

-- DELETE

DELIMITER ;