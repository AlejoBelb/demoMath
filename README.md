CREATE OR REPLACE FUNCTION n_ejemplo(n_sales NUMBER) RETURN VARCHAR2 IS n_commission NUMBER; BEGIN CASE WHEN n_sales > 200000 THEN n_commission := 0.2; WHEN n_sales >= 100000 AND n_sales < 200000 THEN n_commission := 0.15; WHEN n_sales >= 50000 AND n_sales < 100000 THEN n_commission := 0.1; WHEN n_sales >= 30000 THEN n_commission := 0.05; ELSE n_commission := 0; END CASE; RETURN 'Commission is ' || n_commission * 100 || '%'; END;

CREATE OR REPLACE FUNCTION asignar_ranking ( c_grade IN VARCHAR2 ) RETURN VARCHAR2 IS c_rank VARCHAR2(20); BEGIN CASE c_grade WHEN 'A' THEN c_rank := 'Excellent'; WHEN 'B' THEN c_rank := 'Very Good'; WHEN 'C' THEN c_rank := 'Good'; WHEN 'D' THEN c_rank := 'Fair'; WHEN 'F' THEN c_rank := 'Poor'; ELSE c_rank := 'No such grade'; END CASE; RETURN c_rank; END;


CREATE OR REPLACE FUNCTION calcular_factorial(n IN NUMBER) 
RETURN NUMBER IS
    resultado NUMBER := 1;
BEGIN
    IF n = 0 THEN
        RETURN 1;
    ELSE
        FOR i IN 1..n LOOP
            resultado := resultado * i;
        END LOOP;
    END IF;
    RETURN resultado;
END;
/

CREATE OR REPLACE FUNCTION calcular_mcd(a IN NUMBER, b IN NUMBER) 
RETURN NUMBER IS
BEGIN
    IF b = 0 THEN
        RETURN a;
    ELSE
        RETURN calcular_mcd(b, a MOD b);
    END IF;
END;
/

CREATE OR REPLACE FUNCTION es_primo(n IN NUMBER) 
RETURN VARCHAR2 IS
BEGIN
    IF n <= 1 THEN
        RETURN 'NO';
    END IF;
    FOR i IN 2..FLOOR(SQRT(n)) LOOP
        IF n MOD i = 0 THEN
            RETURN 'NO';
        END IF;
    END LOOP;
    RETURN 'SI';
END;
/

CREATE OR REPLACE FUNCTION fibonacci(n IN NUMBER) 
RETURN VARCHAR2 IS
    resultado VARCHAR2(4000) := '';
    a NUMBER := 0;
    b NUMBER := 1;
    temp NUMBER;
BEGIN
    IF n = 0 THEN
        RETURN '0';
    ELSIF n = 1 THEN
        RETURN '0, 1';
    ELSE
        resultado := '0, 1';
        FOR i IN 2..n LOOP
            temp := a + b;
            a := b;
            b := temp;
            resultado := resultado || ', ' || temp;
        END LOOP;
    END IF;
    RETURN resultado;
END;
/

-- CREAR TABLAS Y RELACIONES (Ejecutar en Oracle SQL Developer)
CREATE TABLE clientes (
  cod_cliente CHAR(5) NOT NULL PRIMARY KEY,
  nombres VARCHAR2(30) NOT NULL,
  distrito VARCHAR2(30) NULL,
  telefono VARCHAR2(10) NULL
);

CREATE TABLE productos (
  cod_producto CHAR(5) NOT NULL PRIMARY KEY,
  descripcion VARCHAR2(30) NULL,
  precio_unitario NUMBER(9,2) NULL
);

CREATE TABLE facturas (
  cod_factura CHAR(5) NOT NULL PRIMARY KEY,
  cod_cliente CHAR(5) NOT NULL,
  fecha_emision DATE NULL,
  importe_total DECIMAL(9,2) NULL
);

CREATE TABLE detalle_facturas (
  cod_factura CHAR(5) NOT NULL,
  cod_producto CHAR(5) NOT NULL,
  cantidad SMALLINT NOT NULL,
  subtotal DECIMAL(9,2) NOT NULL
);

ALTER TABLE facturas
ADD FOREIGN KEY (cod_cliente)
REFERENCES clientes(cod_cliente);

ALTER TABLE detalle_facturas
ADD PRIMARY KEY (cod_factura,cod_producto);

ALTER TABLE detalle_facturas
ADD FOREIGN KEY (cod_factura)
REFERENCES facturas(cod_factura);

ALTER TABLE detalle_facturas
ADD FOREIGN KEY (cod_producto)
REFERENCES productos(cod_producto);

-- INSERTANDO REGISTROS A LA TABLA CLIENTES
 
INSERT INTO clientes
  VALUES('C0001','Julián Pérez','Lince','3214568');
INSERT INTO clientes
  VALUES('C0002','Maria Chavez','Jesus Maria','4215678');
INSERT INTO clientes
  VALUES('C0003','Juan Chacaltana','Comas','5416321');
INSERT INTO clientes
  VALUES('C0004','Guadalupe Fernández','Cercado','5216364');
INSERT INTO clientes
  VALUES('C0005','José Gómez','Lince','3458974');
INSERT INTO clientes
  VALUES('C0006','Carlos Segura','Rimac','4562312');
INSERT INTO clientes
  VALUES('C0007','Javier Buleje','Lince','3218974');
INSERT INTO clientes
  VALUES('C0008','Mary Huaman','Cercado','5631278');
INSERT INTO clientes
  VALUES('C0009','Cristian Sánchez','Cercado','5410591');
INSERT INTO clientes
  VALUES('C0010','Flor Paz','Lince','3564165');
 
-- INSERTANDO REGISTROS A LA TABLA PRODUCTOS
 
INSERT INTO productos
  VALUES('P0001','Memoria DDR 256 Mb',120.00);
INSERT INTO productos
  VALUES('P0002','Memoria DDR 500 Mb',190.00);
INSERT INTO productos
  VALUES('P0003','Mainboard PcChip v25',280.00);
INSERT INTO productos
  VALUES('P0004','Mainboard Intel V101',320.00);
INSERT INTO productos
  VALUES('P0005','Procesador Intel 3.6',390.00);
INSERT INTO productos
  VALUES('P0006','Procesador Intel 2.6',340.00);
INSERT INTO productos
  VALUES('P0007','Procesador Celeron 3.2',320.00);
INSERT INTO productos
  VALUES('P0008','Disco Duro SATA 80 GB',180.00);
INSERT INTO productos
  VALUES('P0009','Disco Duro SATA 120 GB',220.00);
INSERT INTO productos
  VALUES('P0010','Case Prezcom 500 Wats',120.00);
   
-- INSERTANDO REGISTROS A TABLA FACTURAS
INSERT INTO facturas
  VALUES('F0001','C0004',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0002','C0006',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0003','C0007',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0004','C0004',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0005','C0008',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0006','C0002',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0007','C0006',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0008','C0009',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0009','C0010',SYSDATE,0);
INSERT INTO facturas
  VALUES('F0010','C0004',SYSDATE,0);
 
-- INSERTANDO REGISTROS A LA TABLA DETALLE FACTURAS
INSERT INTO detalle_facturas
  VALUES('F0001','P0003',3,840.00);
INSERT INTO detalle_facturas
  VALUES('F0001','P0005',5,1950.00);
INSERT INTO detalle_facturas
  VALUES('F0001','P0006',2,680.00);
INSERT INTO detalle_facturas
  VALUES('F0001','P0009',1,220.00);
INSERT INTO detalle_facturas
  VALUES('F0002','P0009',3,660.00);
INSERT INTO detalle_facturas
  VALUES('F0002','P0005',8,3120.00);
INSERT INTO detalle_facturas
  VALUES('F0002','P0002',2,380.00);
INSERT INTO detalle_facturas
  VALUES('F0002','P0007',3,960.00);
INSERT INTO detalle_facturas
  VALUES('F0002','P0010',1,120.00);
INSERT INTO detalle_facturas
  VALUES('F0003','P0010',4,480.00);
INSERT INTO detalle_facturas
  VALUES('F0003','P0003',7,1960.00);
INSERT INTO detalle_facturas
  VALUES('F0003','P0006',2,680.00);
INSERT INTO detalle_facturas
  VALUES('F0003','P0009',1,220.00);
INSERT INTO detalle_facturas
  VALUES('F0004','P0005',3,1170.00);
INSERT INTO detalle_facturas
  VALUES('F0004','P0006',6,2040.00);
INSERT INTO detalle_facturas
  VALUES('F0004','P0007',8,2560.00);
INSERT INTO detalle_facturas
  VALUES('F0005','P0003',3,840.00);
INSERT INTO detalle_facturas
  VALUES('F0005','P0005',5,1950.00);
INSERT INTO detalle_facturas
  VALUES('F0005','P0002',1,190.00);
INSERT INTO detalle_facturas
  VALUES('F0006','P0007',3,960.00);
INSERT INTO detalle_facturas
  VALUES('F0006','P0002',2,380.00);
INSERT INTO detalle_facturas
  VALUES('F0007','P0003',3,840.00);
INSERT INTO detalle_facturas
  VALUES('F0007','P0008',4,720.00);
INSERT INTO detalle_facturas
  VALUES('F0008','P0003',3,840.00);
INSERT INTO detalle_facturas
  VALUES('F0008','P0006',1,340.00);
INSERT INTO detalle_facturas
  VALUES('F0009','P0003',3,840.00);
INSERT INTO detalle_facturas
  VALUES('F0010','P0009',5,1100.00);


CREATE OR REPLACE PROCEDURE actualizar_precio_producto(
    p_cod_producto IN CHAR, 
    p_nuevo_precio IN NUMBER
) AS
BEGIN
    UPDATE productos
    SET precio_unitario = p_nuevo_precio
    WHERE cod_producto = p_cod_producto;
    
    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'El producto con el código especificado no existe');
    END IF;
END;
/
