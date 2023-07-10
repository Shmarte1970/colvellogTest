lsDir = "\\lserver\Informatica\Proyectos\Covellog2\covellog2\BD\Migracion Covellog\"
SET DEFAULT TO (lsDir)
*!*	USE Empresa
*!*	COPY TO empresa.txt TYPE DELIMITED
*!*	USE Obra
*!*	COPY TO "obra.txt" TYPE DELIMITED
*!*	USE fpvendaempresa
*!*	COPY TO "fpvendaempresa.txt" TYPE DELIMITED
*!*	USE comercial
*!*	COPY TO "comercial.txt" TYPE DELIMITED
*!*	USE poblacio
*!*	COPY TO "poblacion.txt" TYPE DELIMITED
*!*	USE tipusvia
*!*	COPY TO "tipusvia.txt" TYPE DELIMITED
*DELETE FROM empresa2
INSERT INTO empresa2 (id, cif, nombre, idioma_id,  fiscal_direccion, fiscal_direccion2, fiscal_codigo_postal, fiscal_poblacion_id, palabras_clave) SELECT id, cif, nom, "es_ES", direccio, direccio2, codipostal, traducionPoblacion(localitat), "COVELLOG1" FROM empresa


FUNCTION traducionPoblacion(lsNombre)
	SELECT * FROM poblacio2 WHERE UPPER(nom)=UPPER(lsNombre) INTO CURSOR cur_aux
	IF _TALLY=0
		RETURN 100000
	ENDIF
	RETURN cur_aux.id
ENDFUNC