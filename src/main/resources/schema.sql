-- ========================================
-- CREACIÓN DE BASE DE DATOS
-- ========================================
CREATE DATABASE IF NOT EXISTS logistica_sjb_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE logistica_sjb_db;

-- ========================================
-- CONFIGURACIONES INICIALES
-- ========================================
SET FOREIGN_KEY_CHECKS = 1;
SET SQL_MODE = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';

-- ========================================
-- CREACIÓN DE TABLAS
-- ========================================

-- TABLA: CATEGORIAS
CREATE TABLE categorias (
    categoria_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    color_identificador VARCHAR(7) COMMENT 'Color hexadecimal (ej: #FF5733)',
    icono VARCHAR(50) COMMENT 'Icono FontAwesome (ej: fa-clipboard)',
    orden_visualizacion INT DEFAULT 0,
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo' NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_estado (estado),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Categorías de productos administrativos';

-- TABLA: DEPARTAMENTOS
CREATE TABLE departamentos (
    departamento_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    codigo VARCHAR(20) NOT NULL UNIQUE COMMENT 'Código corto (ej: ADM, FIN)',
    descripcion TEXT,
    presupuesto_mensual DECIMAL(10,2) DEFAULT 0.00 CHECK (presupuesto_mensual >= 0),
    jefe_departamento_id INT DEFAULT NULL,
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo' NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_codigo (codigo),
    INDEX idx_estado (estado),
    INDEX idx_jefe (jefe_departamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Departamentos administrativos de la clínica';

-- TABLA: USUARIOS
CREATE TABLE usuarios (
    usuario_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    contraseña_hash VARCHAR(255) NOT NULL COMMENT 'Contraseña encriptada con bcrypt',
    departamento_id INT DEFAULT NULL,
    puesto VARCHAR(100),
    rol VARCHAR(50) NOT NULL,
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo' NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso DATETIME DEFAULT NULL,
    
    CONSTRAINT fk_usuario_departamento FOREIGN KEY (departamento_id)
        REFERENCES departamentos(departamento_id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    
    INDEX idx_email (email),
    INDEX idx_rol (rol),
    INDEX idx_estado (estado),
    INDEX idx_departamento (departamento_id),
    INDEX idx_nombre_completo (nombre, apellido)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Usuarios del sistema con roles y permisos';

-- Agregar FK de jefe_departamento después de crear tabla usuarios
ALTER TABLE departamentos
ADD CONSTRAINT fk_departamento_jefe FOREIGN KEY (jefe_departamento_id)
    REFERENCES usuarios(usuario_id)
    ON UPDATE CASCADE
    ON DELETE SET NULL;

-- TABLA: PRODUCTOS
CREATE TABLE productos (
    producto_id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE COMMENT 'Código único del producto (ej: OFF-PAP-001)',
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    categoria_id INT NOT NULL,
    unidad_medida VARCHAR(50) NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL CHECK (precio_unitario >= 0),
    stock_minimo INT NOT NULL DEFAULT 0 CHECK (stock_minimo >= 0),
    stock_maximo INT NOT NULL DEFAULT 100,
    imagen VARCHAR(500) DEFAULT NULL,
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo' NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id)
        REFERENCES categorias(categoria_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT chk_stock_maximo CHECK (stock_maximo >= stock_minimo),
    
    INDEX idx_codigo (codigo),
    INDEX idx_nombre (nombre),
    INDEX idx_categoria (categoria_id),
    INDEX idx_estado (estado),
    INDEX idx_stock_minimo (stock_minimo),
    FULLTEXT idx_busqueda (nombre, descripcion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Catálogo de productos administrativos';

-- TABLA: SOLICITUDES
CREATE TABLE solicitudes (
    solicitud_id INT AUTO_INCREMENT PRIMARY KEY,
    numero_solicitud VARCHAR(50) NOT NULL UNIQUE COMMENT 'Formato: SJB-YYYYMMDD-NNNN',
    departamento_id INT NOT NULL,
    usuario_solicitante_id INT NOT NULL,
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    fecha_requerida DATE NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    prioridad VARCHAR(50) NOT NULL DEFAULT 'NORMAL',
    justificacion TEXT,
    usuario_aprobador_id INT DEFAULT NULL,
    fecha_aprobacion DATETIME DEFAULT NULL,
    motivo_rechazo TEXT DEFAULT NULL,
    usuario_despachador_id INT DEFAULT NULL,
    fecha_despacho DATETIME DEFAULT NULL,
    usuario_receptor_id INT DEFAULT NULL,
    fecha_recepcion DATETIME DEFAULT NULL,
    observaciones_recepcion TEXT DEFAULT NULL,
    costo_total_estimado DECIMAL(10,2) DEFAULT 0.00 CHECK (costo_total_estimado >= 0),
    origen_solicitud VARCHAR(50) NOT NULL DEFAULT 'MANUAL',
    
    CONSTRAINT fk_solicitud_departamento FOREIGN KEY (departamento_id)
        REFERENCES departamentos(departamento_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    
    CONSTRAINT fk_solicitud_solicitante FOREIGN KEY (usuario_solicitante_id)
        REFERENCES usuarios(usuario_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    
    CONSTRAINT fk_solicitud_aprobador FOREIGN KEY (usuario_aprobador_id)
        REFERENCES usuarios(usuario_id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    
    CONSTRAINT fk_solicitud_despachador FOREIGN KEY (usuario_despachador_id)
        REFERENCES usuarios(usuario_id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    
    CONSTRAINT fk_solicitud_receptor FOREIGN KEY (usuario_receptor_id)
        REFERENCES usuarios(usuario_id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    
    INDEX idx_numero_solicitud (numero_solicitud),
    INDEX idx_estado (estado),
    INDEX idx_prioridad (prioridad),
    INDEX idx_departamento (departamento_id),
    INDEX idx_solicitante (usuario_solicitante_id),
    INDEX idx_fecha_solicitud (fecha_solicitud),
    INDEX idx_fecha_requerida (fecha_requerida),
    INDEX idx_estado_prioridad (estado, prioridad)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Solicitudes de materiales administrativos';

-- TABLA: DETALLE_SOLICITUDES
CREATE TABLE detalle_solicitudes (
    detalle_id INT AUTO_INCREMENT PRIMARY KEY,
    solicitud_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad_solicitada INT NOT NULL CHECK (cantidad_solicitada > 0),
    cantidad_aprobada INT DEFAULT NULL CHECK (cantidad_aprobada >= 0),
    cantidad_despachada INT DEFAULT NULL CHECK (cantidad_despachada >= 0),
    cantidad_recibida INT DEFAULT NULL CHECK (cantidad_recibida >= 0),
    especificaciones TEXT DEFAULT NULL,
    precio_unitario_estimado DECIMAL(10,2) DEFAULT NULL,
    subtotal_estimado DECIMAL(10,2) GENERATED ALWAYS AS (cantidad_solicitada * IFNULL(precio_unitario_estimado, 0)) STORED,
    estado_item VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
    
    CONSTRAINT fk_detalle_solicitud FOREIGN KEY (solicitud_id)
        REFERENCES solicitudes(solicitud_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id)
        REFERENCES productos(producto_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    
    INDEX idx_solicitud (solicitud_id),
    INDEX idx_producto (producto_id),
    INDEX idx_estado_item (estado_item)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Detalle de productos en cada solicitud';

-- TABLA: MOVIMIENTOS_INVENTARIO
CREATE TABLE movimientos_inventario (
    movimiento_id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT NOT NULL,
    tipo_movimiento VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL COMMENT 'Puede ser positivo (entrada) o negativo (salida/ajuste)',
    stock_anterior INT NOT NULL CHECK (stock_anterior >= 0),
    stock_nuevo INT NOT NULL CHECK (stock_nuevo >= 0),
    fecha_movimiento DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    usuario_responsable_id INT NOT NULL,
    solicitud_id INT DEFAULT NULL COMMENT 'Si movimiento fue por solicitud',
    motivo VARCHAR(200),
    observaciones TEXT,
    lote_serie VARCHAR(100) DEFAULT NULL,
    fecha_vencimiento DATE DEFAULT NULL,
    ubicacion_fisica VARCHAR(100) DEFAULT NULL COMMENT 'Ubicación en almacén',
    precio_unitario DECIMAL(10,2) DEFAULT NULL,
    
    CONSTRAINT fk_movimiento_producto FOREIGN KEY (producto_id)
        REFERENCES productos(producto_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    
    CONSTRAINT fk_movimiento_usuario FOREIGN KEY (usuario_responsable_id)
        REFERENCES usuarios(usuario_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    
    CONSTRAINT fk_movimiento_solicitud FOREIGN KEY (solicitud_id)
        REFERENCES solicitudes(solicitud_id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    
    INDEX idx_producto (producto_id),
    INDEX idx_tipo_movimiento (tipo_movimiento),
    INDEX idx_fecha_movimiento (fecha_movimiento),
    INDEX idx_usuario (usuario_responsable_id),
    INDEX idx_solicitud (solicitud_id),
    INDEX idx_producto_fecha (producto_id, fecha_movimiento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Registro de movimientos de inventario';

-- TABLA: PROVEEDORES (Fase 2)
CREATE TABLE proveedores (
    proveedor_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    ruc VARCHAR(11) NOT NULL UNIQUE,
    direccion TEXT,
    telefono VARCHAR(20),
    email VARCHAR(200),
    contacto_nombre VARCHAR(100),
    contacto_telefono VARCHAR(20),
    condiciones_pago TEXT,
    calificacion DECIMAL(3,2) DEFAULT NULL CHECK (calificacion BETWEEN 1.00 AND 5.00),
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo' NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_ruc (ruc),
    INDEX idx_nombre (nombre),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Proveedores de productos administrativos';

-- TABLA: BITACORA_AUDITORIA
CREATE TABLE bitacora_auditoria (
    auditoria_id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    accion VARCHAR(100) NOT NULL COMMENT 'CREATE, UPDATE, DELETE, LOGIN, etc.',
    tabla_afectada VARCHAR(50) NOT NULL,
    registro_id INT DEFAULT NULL COMMENT 'ID del registro afectado',
    valores_anteriores JSON DEFAULT NULL,
    valores_nuevos JSON DEFAULT NULL,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ip_address VARCHAR(45) DEFAULT NULL,
    user_agent VARCHAR(500) DEFAULT NULL,
    
    CONSTRAINT fk_auditoria_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios(usuario_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    
    INDEX idx_usuario (usuario_id),
    INDEX idx_accion (accion),
    INDEX idx_tabla (tabla_afectada),
    INDEX idx_fecha (fecha_hora),
    INDEX idx_usuario_fecha (usuario_id, fecha_hora)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Registro de auditoría de todas las operaciones';

-- ========================================
-- VISTAS (VIEWS)
-- ========================================

CREATE OR REPLACE VIEW v_solicitudes_completas AS
SELECT 
    s.solicitud_id,
    s.numero_solicitud,
    s.estado,
    s.prioridad,
    s.fecha_solicitud,
    s.fecha_requerida,
    d.nombre AS departamento,
    d.codigo AS codigo_departamento,
    CONCAT(u1.nombre, ' ', u1.apellido) AS solicitante,
    u1.email AS solicitante_email,
    CONCAT(u2.nombre, ' ', u2.apellido) AS aprobador,
    s.fecha_aprobacion,
    CONCAT(u3.nombre, ' ', u3.apellido) AS despachador,
    s.fecha_despacho,
    CONCAT(u4.nombre, ' ', u4.apellido) AS receptor,
    s.fecha_recepcion,
    COUNT(ds.detalle_id) AS total_productos,
    SUM(ds.cantidad_solicitada) AS total_cantidad,
    s.costo_total_estimado,
    DATEDIFF(COALESCE(s.fecha_recepcion, CURRENT_DATE), s.fecha_solicitud) AS dias_transcurridos,
    CASE 
        WHEN s.estado = 'ENTREGADA' AND s.fecha_recepcion <= s.fecha_requerida THEN 'A Tiempo'
        WHEN s.estado = 'ENTREGADA' AND s.fecha_recepcion > s.fecha_requerida THEN 'Retrasada'
        WHEN s.estado IN ('PENDIENTE', 'EN_REVISION', 'APROBADA', 'DESPACHADA') 
             AND CURRENT_DATE > s.fecha_requerida THEN 'Vencida'
        ELSE 'En Proceso'
    END AS estado_cumplimiento
FROM solicitudes s
INNER JOIN departamentos d ON s.departamento_id = d.departamento_id
INNER JOIN usuarios u1 ON s.usuario_solicitante_id = u1.usuario_id
LEFT JOIN usuarios u2 ON s.usuario_aprobador_id = u2.usuario_id
LEFT JOIN usuarios u3 ON s.usuario_despachador_id = u3.usuario_id
LEFT JOIN usuarios u4 ON s.usuario_receptor_id = u4.usuario_id
LEFT JOIN detalle_solicitudes ds ON s.solicitud_id = ds.solicitud_id
GROUP BY s.solicitud_id;

CREATE OR REPLACE VIEW v_stock_actual AS
SELECT 
    p.producto_id,
    p.codigo,
    p.nombre,
    p.descripcion,
    c.nombre AS categoria,
    p.unidad_medida,
    p.precio_unitario,
    p.stock_minimo,
    p.stock_maximo,
    COALESCE(
        SUM(CASE 
            WHEN m.tipo_movimiento = 'ENTRADA' THEN m.cantidad
            WHEN m.tipo_movimiento IN ('SALIDA', 'AJUSTE') THEN -m.cantidad
            ELSE 0
        END), 
        0
    ) AS stock_actual,
    CASE 
        WHEN COALESCE(SUM(CASE 
                WHEN m.tipo_movimiento = 'ENTRADA' THEN m.cantidad
                WHEN m.tipo_movimiento IN ('SALIDA', 'AJUSTE') THEN -m.cantidad
                ELSE 0
            END), 0) = 0 THEN 'Crítico'
        WHEN COALESCE(SUM(CASE 
                WHEN m.tipo_movimiento = 'ENTRADA' THEN m.cantidad
                WHEN m.tipo_movimiento IN ('SALIDA', 'AJUSTE') THEN -m.cantidad
                ELSE 0
            END), 0) <= p.stock_minimo THEN 'Bajo'
        WHEN COALESCE(SUM(CASE 
                WHEN m.tipo_movimiento = 'ENTRADA' THEN m.cantidad
                WHEN m.tipo_movimiento IN ('SALIDA', 'AJUSTE') THEN -m.cantidad
                ELSE 0
            END), 0) >= p.stock_maximo THEN 'Exceso'
        ELSE 'Normal'
    END AS estado_stock,
    p.estado AS estado_producto,
    MAX(m.fecha_movimiento) AS ultimo_movimiento
FROM productos p
INNER JOIN categorias c ON p.categoria_id = c.categoria_id
LEFT JOIN movimientos_inventario m ON p.producto_id = m.producto_id
WHERE p.estado = 'Activo'
GROUP BY p.producto_id;

CREATE OR REPLACE VIEW v_productos_alerta_stock AS
SELECT 
    p.producto_id,
    p.codigo,
    p.nombre,
    c.nombre AS categoria,
    stock_actual,
    p.stock_minimo,
    (p.stock_minimo - stock_actual) AS cantidad_requerida,
    estado_stock
FROM v_stock_actual vsa
INNER JOIN productos p ON vsa.producto_id = p.producto_id
INNER JOIN categorias c ON p.categoria_id = c.categoria_id
WHERE estado_stock IN ('Crítico', 'Bajo')
ORDER BY 
    CASE estado_stock
        WHEN 'Crítico' THEN 1
        WHEN 'Bajo' THEN 2
    END,
    stock_actual ASC;

CREATE OR REPLACE VIEW v_dashboard_kpis AS
SELECT 
    (SELECT COUNT(*) FROM solicitudes WHERE MONTH(fecha_solicitud) = MONTH(CURRENT_DATE) AND YEAR(fecha_solicitud) = YEAR(CURRENT_DATE)) AS solicitudes_mes,
    (SELECT COUNT(*) FROM solicitudes WHERE estado = 'ENTREGADA' AND MONTH(fecha_recepcion) = MONTH(CURRENT_DATE) AND YEAR(fecha_recepcion) = YEAR(CURRENT_DATE)) AS solicitudes_completadas_mes,
    (SELECT COUNT(*) FROM solicitudes WHERE estado IN ('PENDIENTE', 'EN_REVISION', 'APROBADA', 'DESPACHADA')) AS solicitudes_pendientes,
    (SELECT AVG(DATEDIFF(fecha_recepcion, fecha_solicitud)) FROM solicitudes WHERE estado = 'ENTREGADA' AND MONTH(fecha_recepcion) = MONTH(CURRENT_DATE)) AS tiempo_promedio_atencion_dias,
    (SELECT COUNT(*) FROM v_stock_actual WHERE estado_stock = 'Crítico') AS productos_stock_critico,
    (SELECT COUNT(*) FROM v_stock_actual WHERE estado_stock = 'Bajo') AS productos_stock_bajo,
    (SELECT SUM(costo_total_estimado) FROM solicitudes WHERE estado = 'ENTREGADA' AND MONTH(fecha_recepcion) = MONTH(CURRENT_DATE) AND YEAR(fecha_recepcion) = YEAR(CURRENT_DATE)) AS costo_total_mes,
    (SELECT 
        ROUND(
            (COUNT(CASE WHEN fecha_recepcion <= fecha_requerida THEN 1 END) * 100.0 / COUNT(*)), 
            2
        )
     FROM solicitudes 
     WHERE estado = 'ENTREGADA' AND MONTH(fecha_recepcion) = MONTH(CURRENT_DATE)
    ) AS tasa_cumplimiento_ontime;

-- ========================================
-- TRIGGERS
-- ========================================

DELIMITER $$

CREATE TRIGGER trg_calcular_costo_solicitud_insert
AFTER INSERT ON detalle_solicitudes
FOR EACH ROW
BEGIN
    UPDATE solicitudes
    SET costo_total_estimado = (
        SELECT SUM(subtotal_estimado)
        FROM detalle_solicitudes
        WHERE solicitud_id = NEW.solicitud_id
    )
    WHERE solicitud_id = NEW.solicitud_id;
END$$

CREATE TRIGGER trg_calcular_costo_solicitud_update
AFTER UPDATE ON detalle_solicitudes
FOR EACH ROW
BEGIN
    UPDATE solicitudes
    SET costo_total_estimado = (
        SELECT SUM(subtotal_estimado)
        FROM detalle_solicitudes
        WHERE solicitud_id = NEW.solicitud_id
    )
    WHERE solicitud_id = NEW.solicitud_id;
END$$

CREATE TRIGGER trg_calcular_costo_solicitud_delete
AFTER DELETE ON detalle_solicitudes
FOR EACH ROW
BEGIN
    UPDATE solicitudes
    SET costo_total_estimado = (
        SELECT COALESCE(SUM(subtotal_estimado), 0)
        FROM detalle_solicitudes
        WHERE solicitud_id = OLD.solicitud_id
    )
    WHERE solicitud_id = OLD.solicitud_id;
END$$

CREATE TRIGGER trg_auditoria_solicitud_update
AFTER UPDATE ON solicitudes
FOR EACH ROW
BEGIN
    IF OLD.estado != NEW.estado THEN
        INSERT INTO bitacora_auditoria (
            usuario_id,
            accion,
            tabla_afectada,
            registro_id,
            valores_anteriores,
            valores_nuevos
        ) VALUES (
            NEW.usuario_aprobador_id,  -- Asume que el usuario que cambió estado es el aprobador
            'UPDATE_ESTADO',
            'solicitudes',
            NEW.solicitud_id,
            JSON_OBJECT('estado', OLD.estado, 'fecha', OLD.fecha_aprobacion),
            JSON_OBJECT('estado', NEW.estado, 'fecha', NEW.fecha_aprobacion)
        );
    END IF;
END$$

CREATE TRIGGER trg_validar_stock_antes_despacho
BEFORE UPDATE ON detalle_solicitudes
FOR EACH ROW
BEGIN
    DECLARE stock_disponible INT;
    
    IF NEW.cantidad_despachada IS NOT NULL AND NEW.cantidad_despachada > 0 THEN
        -- Obtener stock actual del producto
        SELECT COALESCE(SUM(CASE 
                WHEN tipo_movimiento = 'ENTRADA' THEN cantidad
                WHEN tipo_movimiento IN ('SALIDA', 'AJUSTE') THEN -cantidad
            END), 0)
        INTO stock_disponible
        FROM movimientos_inventario
        WHERE producto_id = NEW.producto_id;
        
        -- Validar que hay stock suficiente
        IF stock_disponible < NEW.cantidad_despachada THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Stock insuficiente para despachar la cantidad solicitada';
        END IF;
    END IF;
END$$

CREATE TRIGGER trg_generar_numero_solicitud
BEFORE INSERT ON solicitudes
FOR EACH ROW
BEGIN
    DECLARE siguiente_secuencial INT;
    DECLARE fecha_actual VARCHAR(8);
    
    SET fecha_actual = DATE_FORMAT(CURRENT_DATE, '%Y%m%d');
    
    -- Obtener el siguiente secuencial para hoy
    SELECT COALESCE(MAX(CAST(SUBSTRING(numero_solicitud, -4) AS UNSIGNED)), 0) + 1
    INTO siguiente_secuencial
    FROM solicitudes
    WHERE numero_solicitud LIKE CONCAT('SJB-', fecha_actual, '-%');
    
    -- Generar número con formato SJB-YYYYMMDD-NNNN
    SET NEW.numero_solicitud = CONCAT('SJB-', fecha_actual, '-', LPAD(siguiente_secuencial, 4, '0'));
END$$

DELIMITER ;

-- ========================================
-- STORED PROCEDURES
-- ========================================

DELIMITER $$

CREATE PROCEDURE sp_registrar_movimiento_inventario(
    IN p_producto_id INT,
    IN p_tipo_movimiento ENUM('Entrada', 'Salida', 'Ajuste', 'Transferencia'),
    IN p_cantidad INT,
    IN p_usuario_id INT,
    IN p_solicitud_id INT,
    IN p_motivo VARCHAR(200),
    IN p_observaciones TEXT
)
BEGIN
    DECLARE v_stock_anterior INT;
    DECLARE v_stock_nuevo INT;
    
    -- Obtener stock actual
    SELECT COALESCE(SUM(CASE 
            WHEN tipo_movimiento = 'ENTRADA' THEN cantidad
            WHEN tipo_movimiento IN ('SALIDA', 'AJUSTE') THEN -cantidad
        END), 0)
    INTO v_stock_anterior
    FROM movimientos_inventario
    WHERE producto_id = p_producto_id;
    
    -- Calcular nuevo stock
    IF p_tipo_movimiento = 'ENTRADA' THEN
        SET v_stock_nuevo = v_stock_anterior + p_cantidad;
    ELSEIF p_tipo_movimiento IN ('SALIDA', 'AJUSTE') THEN
        SET v_stock_nuevo = v_stock_anterior - p_cantidad;
    ELSE
        SET v_stock_nuevo = v_stock_anterior;
    END IF;
    
    -- Validar que stock no sea negativo
    IF v_stock_nuevo < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El movimiento resultaría en stock negativo';
    END IF;
    
    -- Registrar movimiento
    INSERT INTO movimientos_inventario (
        producto_id,
        tipo_movimiento,
        cantidad,
        stock_anterior,
        stock_nuevo,
        usuario_responsable_id,
        solicitud_id,
        motivo,
        observaciones
    ) VALUES (
        p_producto_id,
        p_tipo_movimiento,
        p_cantidad,
        v_stock_anterior,
        v_stock_nuevo,
        p_usuario_id,
        p_solicitud_id,
        p_motivo,
        p_observaciones
    );
    
    SELECT 'Movimiento registrado exitosamente' AS mensaje, LAST_INSERT_ID() AS movimiento_id;
END$$

CREATE PROCEDURE sp_aprobar_solicitud(
    IN p_solicitud_id INT,
    IN p_usuario_aprobador_id INT
)
BEGIN
    DECLARE v_estado_actual VARCHAR(50);
    
    -- Obtener estado actual
    SELECT estado INTO v_estado_actual
    FROM solicitudes
    WHERE solicitud_id = p_solicitud_id;
    
    -- Validar que esté en estado pendiente
    IF v_estado_actual != 'PENDIENTE' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Solo se pueden aprobar solicitudes en estado Pendiente';
    END IF;
    
    -- Actualizar solicitud
    UPDATE solicitudes
    SET 
        estado = 'APROBADA',
        usuario_aprobador_id = p_usuario_aprobador_id,
        fecha_aprobacion = CURRENT_TIMESTAMP
    WHERE solicitud_id = p_solicitud_id;
    
    -- Actualizar detalles
    UPDATE detalle_solicitudes
    SET 
        estado_item = 'APROBADO',
        cantidad_aprobada = cantidad_solicitada
    WHERE solicitud_id = p_solicitud_id;
    
    SELECT 'Solicitud aprobada exitosamente' AS mensaje;
END$$

DELIMITER ;
