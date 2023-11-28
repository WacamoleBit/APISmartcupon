USE smartcupon_db;

DELIMITER //

CREATE PROCEDURE registrarUsuario(
	IN nombre VARCHAR(50),
    IN apellidoPaterno VARCHAR(50),
    IN apellidoMaterno VARCHAR(50),
    IN curp VARCHAR(18),
    IN telefono VARCHAR(50), 
    IN username VARCHAR(20),
    IN password VARCHAR(20), 
    IN rol INT,
    IN empresa INT,
    )
    
    #INSERT INTO usuario (nombre, apellidoPaterno, apellidoMaterno, curp, email, username, password, rol, empresa)
        #VALUES (#{nombre}, #{apellidoPaterno}, #{apellidoMaterno}, #{curp}, #{email}, #{username}, #{password}, #{rol}, #{empresa})

END //

-- BUSCAR POR FILTRO

DELIMITER //

CREATE PROCEDURE buscarPorFiltro (
	IN cadenaBusqueda VARCHAR(255),
    IN porNombre BOOL,
    IN porUsername BOOL,
    IN porRol BOOL)
BEGIN
	SELECT idUsuario, u.nombre as nombre, apellidoPaterno, apellidoMaterno, curp, email, username, password, r.nombre as rol, empresa 
    FROM usuario u
    INNER JOIN rol r ON u.rol = r.idRol
    WHERE
		(porNombre AND u.nombre LIKE CONCAT('%', cadenaBusqueda, '%')) OR
		(porUsername AND username LIKE CONCAT('%', cadenaBusqueda, '%')) OR
		(porRol AND r.nombre LIKE CONCAT('%', cadenaBusqueda, '%'));
END //

