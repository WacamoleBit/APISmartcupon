-- SCRIPTS Usuarios

USE smartcupon_db;

DROP PROCEDURE IF EXISTS registrarUsuario;
DROP PROCEDURE IF EXISTS editarUsuario;
DROP PROCEDURE IF EXISTS buscarPorFiltro;

DELIMITER //

-- REGISTRAR

CREATE PROCEDURE registrarUsuario (
	IN _nombre VARCHAR(50), 
	IN _apellidoPaterno VARCHAR(50), 
	IN _apellidoMaterno VARCHAR(50), 
	IN _curp VARCHAR(18), 
	IN _email VARCHAR(50),
	IN _username VARCHAR(20), 
	IN _password VARCHAR(20), 
	IN _rol INT, 
	IN _empresa INT,
	INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
) 
BEGIN 
    SET _filasAfectadas = 0;
    SET _error = '';
    SET @email = 0;
	SET @curp = 0;
    SET @username = 0;
    
	START TRANSACTION;
    
        SELECT COUNT(*) INTO @email FROM usuario WHERE email = _email;
		IF (@email > 0) THEN
			ROLLBACK;
			SET _error = CONCAT(_error, 'El usuario ya ha sido tomado.\n');
		END IF;
        
        SELECT COUNT(*) INTO @curp FROM usuario WHERE curp = _curp;
		IF (@curp > 0) THEN
			ROLLBACK;
			SET _error = CONCAT(_error, 'La CURPP ya ha sido tomada.\n');
		END IF;
        
        SELECT COUNT(*) INTO @username FROM usuario WHERE username = _username;
		IF (@username > 0) THEN
			ROLLBACK;
			SET _error = CONCAT(_error, 'El usuario ya ha sido tomado.\n');
		END IF;
        
		IF (@email = 0 AND @curp = 0 AND @username = 0) THEN
			INSERT INTO usuario (nombre, apellidoPaterno, apellidoMaterno, curp, email, username, password, rol, empresa)
			VALUES (_nombre, _apellidoPaterno, _apellidoMaterno, _curp, _email, _username, _password, _rol, _empresa);
            
            SET _filasAfectadas = ROW_COUNT();
        END IF;
	
	COMMIT;
END //

-- EDITAR USUARIO

CREATE PROCEDURE editarUsuario (
	IN _idUsuario INT, 
	IN _nombre VARCHAR(50), 
	IN _apellidoPaterno VARCHAR(50), 
	IN _apellidoMaterno VARCHAR(50), 
	IN _curp VARCHAR(18), 
	IN _email VARCHAR(50),
	IN _username VARCHAR(20), 
	IN _password VARCHAR(20), 
	IN _rol INT, 
	IN _empresa INT,
	INOUT _filasAfectadas INT,
    INOUT _error VARCHAR(255)
) 
BEGIN 
    SET _filasAfectadas = 0;
    SET _error = '';
    SET @email = 0;
	SET @curp = 0;
    
	START TRANSACTION;
    
        SELECT COUNT(*) INTO @email FROM usuario WHERE email = _email AND idUsuario != _idUsuario;
		IF (@email > 0) THEN
			ROLLBACK;
			SET _error = CONCAT(_error, 'El email ya ha sido tomado.\n');
		END IF;
        
        SELECT COUNT(*) INTO @curp FROM usuario WHERE curp = _curp AND idUsuario != _idUsuario;
		IF (@curp > 0) THEN
			ROLLBACK;
			SET _error = CONCAT(_error, 'La CURPP ya ha sido tomada.\n');
		END IF;
        
        SELECT COUNT(*) INTO @username FROM usuario WHERE username = _username AND idUsuario != _idUsuario;
		IF (@username > 0) THEN
			ROLLBACK;
			SET _error = CONCAT(_error, 'El usuario ya ha sido tomado.\n');
		END IF;
        
        
		IF (@email = 0 AND @curp = 0 AND @username = 0) THEN
			UPDATE usuario 
				SET nombre = _nombre, 
				apellidoPaterno = _apellidoPaterno, 
				apellidoMaterno = _apellidoMaterno, 
                curp = _curp, 
                email = _email, 
				username = _username,
                password = _password, 
                rol = _rol, 
                empresa = _empresa
			WHERE idUsuario = _idUsuario;
            
            SET _filasAfectadas = ROW_COUNT();
        END IF;
	
	COMMIT;
END //

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