
    CREATE USER 'adminGeneral'@'localhost' IDENTIFIED BY 'smartcupon';
    GRANT ALL PRIVILEGES ON smartcupon_db.* TO 'adminGeneral'@'localhost';
    FLUSH PRIVILEGES;

    CREATE USER 'adminSucursal'@'localhost' IDENTIFIED BY 'smartcupon';
    GRANT SELECT, INSERT, UPDATE, DELETE ON smartcupon_db.promocion TO 'adminSucursal'@'localhost';
    GRANT SELECT, INSERT, UPDATE, DELETE ON smartcupon_db.cupon TO 'adminSucursal'@'localhost';
    GRANT SELECT, INSERT, UPDATE, DELETE ON smartcupon_db.sucursal TO 'adminSucursal'@'localhost';
    GRANT SELECT, INSERT, UPDATE, DELETE ON smartcupon_db.empresa TO 'adminSucursal'@'localhost';
    FLUSH PRIVILEGES;