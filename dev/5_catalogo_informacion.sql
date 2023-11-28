USE smartcupon_db;

INSERT INTO estatus (nombre)
VALUES ("Activo"),
	   ("Inactivo");

INSERT INTO tipoPromocion (nombre)
VALUES ("Descuento"),
	   ("Costo rebajado");

INSERT INTO categoriaPromocion (nombre)
VALUES ("Cine"),
	   ("Comida"),
       ("Ropa"),
       ("Limpieza"),
       ("Videojuegos"),
       ("Calzado"),
       ("Aerolínea"),
       ("Tecnología");

INSERT INTO rol (nombre)
VALUES ("Administrador Comercial"),
	   ("Administrador General");

INSERT INTO tipoPersona (nombre)
VALUES ("Encargado"),
	   ("Representante");

INSERT INTO tipoDireccion (nombre)
VALUES ("Empresa"),
	   ("Sucursal"),
       ("Usuario");