-- SCRIPT promocion

USE smartcupon_db;

DROP PROCEDURE IF EXISTS registrarPromocion;
DROP PROCEDURE IF EXISTS modificarPromocion;
DROP PROCEDURE IF EXISTS eliminarPromocion;
DROP PROCEDURE IF EXISTS buscarPromocionPorFiltro;
DROP TRIGGER IF EXISTS delete_promocion_trigger;

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
    IN _maximoCupones INT,
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
				tipoPromocion, porcentajeDescuento, categoria, cuponesDisponibles, maximoCupones, codigoPromocion,
				estatus, empresa
			)
			VALUES (
				_nombre, _descripcion, _imagen, _fechaInicio, _fechaTermino, _restricciones,
				_tipoPromocion, _porcentajeDescuento, _categoria, _maximoCupones, _maximoCupones, _codigoPromocion,
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

CREATE PROCEDURE modificarPromocion (
	IN _idPromocion INT,
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
    IN _estatus INT,
    IN _empresa INT,

    INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
)
BEGIN
	SET _filasAfectadas = 0;
	SET _error = '';
    
	IF EXISTS (SELECT * FROM promocion where idPromocion = _idPromocion) THEN
		SET @tipoPromocion = 0;
        SET @categoria = 0;
        SET @estatus = 0;
        SET @empresa = 0;
        
        SET @tipoPromocionActual = 0;
        SET @categoriaActual = 0;
        SET @estatusActual = 0;
        SET @empresaActual = 0;
        
        SELECT p.tipopromocion INTO @tipoPromocionActual FROM promocion p where idPromocion = _idPromocion;
        SELECT p.categoria INTO @categoriaActual FROM promocion p where idPromocion = _idPromocion;
        SELECT p.estatus INTO @estatusActual FROM promocion p where idPromocion = _idPromocion;
        SELECT p.empresa INTO @empresaActual FROM promocion p where idPromocion = _idPromocion;
        
        SELECT COUNT(*) INTO @tipoPromocion FROM tipopromocion WHERE idTipoPromocion = IFNULL(_tipoPromocion, @tipoPromocionActual);
        IF @tipoPromocion = 0 THEN
			SET _error = CONCAT(_error, 'El tipo de promocion no existe en la base de datos');
        END IF;
        
        SELECT COUNT(*) INTO @categoria FROM categoriaPromocion WHERE idCategoria = IFNULL(_categoria, @categoriaActual);
        IF @categoria = 0 THEN
			SET _error = CONCAT(_error, 'La categoria no existe en la base de datos');
        END IF;
        
        SELECT COUNT(*) INTO @estatus FROM estatus WHERE idEstatus = IFNULL(_estatus, @estatusActual);
        IF @estatus = 0 THEN
			SET _error = CONCAT(_error, 'El estado especificado no existe en la base de datos');
        END IF;
        
        SELECT COUNT(*) INTO @empresa FROM empresa WHERE idEmpresa = IFNULL(_empresa, @estatusActual);
        IF @empresa = 0 THEN
			SET _error = CONCAT(_error, 'La empresa no existe en la base de datos');
        END IF;
        
        IF @tipoPromocion > 0 AND @categoria > 0 AND @estatus > 0 AND @empresa > 0 THEN
			UPDATE promocion SET
				nombre = IFNULL(_nombre, nombre),
				descripcion = IFNULL(_descripcion, descripcion),
				imagen = IFNULL(_imagen, imagen),
				fechaInicio = IFNULL(STR_TO_DATE(_fechaInicio, '%Y-%m-%d'), fechaInicio),
				fechaTermino = IFNULL(STR_TO_DATE(_fechaTermino, '%Y-%m-%d'), fechaTermino),
				restricciones = IFNULL(_restricciones, restricciones),
				tipoPromocion = IFNULL(_tipoPromocion, tipoPromocion),
				porcentajeDescuento = IFNULL(_porcentajeDescuento, porcentajeDescuento),
				categoria = IFNULL(_categoria, categoria),
				cuponesDisponibles = IFNULL(_cuponesDisponibles, cuponesDisponibles),
				codigoPromocion = IFNULL(_codigoPromocion, codigoPromocion),
				estatus = IFNULL(_estatus, estatus),
				empresa = IFNULL(_empresa, empresa)
			WHERE idPromocion = _idPromocion;
            
            SET _filasAfectadas = ROW_COUNT();
        END IF;
    ELSE
		SET _error = 'La promocion no existe en la base de datos';
    END IF;
END//

-- BUSCAR PROMOCION POR FILTRO

CREATE PROCEDURE buscarPromocionPorFiltro (
    IN _fecha DATE,
    IN _porFechaInicio BOOL,
    IN _nombre VARCHAR(255)
)
BEGIN
    SELECT idPromocion, nombre, descripcion, to_base64(imagen) as imagenBase64, 
			fechaInicio, fechaTermino, restricciones, tipoPromocion, porcentajeDescuento, 
			categoria, cuponesDisponibles, maximoCupones, codigoPromocion, estatus, 
			empresa 
	FROM promocion
	WHERE (_porFechaInicio = true AND fechaInicio = _fecha)
	OR (_porFechaInicio = false AND fechaTermino = _fecha)
	OR (nombre LIKE CONCAT('%', _nombre, '%'));
END //

-- DELETE

CREATE TRIGGER delete_promocion_trigger
AFTER DELETE ON promocion FOR EACH ROW
BEGIN
	DELETE FROM promocionsucursal WHERE idPromocion = OLD.idPromocion;
END; //


CREATE PROCEDURE eliminarPromocion (
	IN _idPromocion INT,
    
    INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
)
BEGIN
	SET _filasAfectadas = 0;
    SET _error = '';
    
	IF NOT EXISTS (SELECT * FROM promocion WHERE idPromocion = _idPromocion) THEN
		SET _error = CONCAT(_error, "No existe la promoción proporcionada");
	ELSE
		DELETE FROM promocion WHERE idPromocion = _idPromocion;
            
		SET _filasAfectadas = ROW_COUNT();
    END IF;
END //

DELIMITER ;