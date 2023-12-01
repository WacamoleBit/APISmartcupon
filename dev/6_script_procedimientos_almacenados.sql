USE smartcupon_db;

-- REGISTRAR CLIENTE CON DIRECCION

DELIMITER //
CREATE PROCEDURE registrarCliente (
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

	INSERT INTO direccion (calle, numero, tipoDireccion)
	VALUES (calle, numero, tipoDireccion);
    
    SET filasDireccion = ROW_COUNT();

	SET direccionID = LAST_INSERT_ID();
	
	INSERT INTO cliente 
		(nombre, apellidoPaterno, apellidoMaterno, telefono, email, direccion, fechaNacimiento, password) 
	VALUES 
		(nombre, apellidoPaterno, apellidoMaterno, telefono, email, direccionID, STR_TO_DATE(fechaNacimiento, '%Y-%m-%d'), password);
        
	SET filasCliente = ROW_COUNT();
    
    SET filasAfectadas = filasDireccion + filasCliente;
        
	COMMIT;
END //

-- BUSCAR POR FILTRO

DELIMITER //
CREATE PROCEDURE buscarPorFiltro (
	IN cadenaBusqueda VARCHAR(255),
    IN porNombre BOOL,
    IN porUsername BOOL,
    IN porRol BOOL)
BEGIN
	SELECT 
		idUsuario, u.nombre as nombre, apellidoPaterno, apellidoMaterno, curp, u.email, username, password, r.nombre as rol, empresa
    FROM 
		usuario u
		INNER JOIN rol r ON u.rol = r.idRol
        -- INNER JOIN empresa e ON u.empresa = e.idEmpresa
    WHERE
		(porNombre AND u.nombre LIKE CONCAT('%', cadenaBusqueda, '%')) OR
		(porUsername AND username LIKE CONCAT('%', cadenaBusqueda, '%')) OR
		(porRol AND r.nombre LIKE CONCAT('%', cadenaBusqueda, '%'));
END //

