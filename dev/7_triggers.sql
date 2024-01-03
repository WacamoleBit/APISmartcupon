DROP TRIGGER actualizarEstatusPromocion;
CREATE TRIGGER actualizarEstatusPromocion BEFORE UPDATE ON promocion
FOR EACH ROW 
BEGIN
     IF NEW.cuponesDisponibles <= 0 AND NEW.estatus <> 2 THEN
        SET NEW.estatus = 2;
    END IF;
END //

DELIMITER ;


DROP TRIGGER actualizarEstatusFecha;

DELIMITER //

CREATE TRIGGER actualizarEstatusFecha BEFORE UPDATE ON promocion
FOR EACH ROW
BEGIN
    IF NEW.fechaTermino < NOW() AND NEW.estatus <> 2 THEN
        SET NEW.estatus = 2;
    END IF;
END //

DELIMITER ;
