-- ==========================================================
-- ESTRUCTURA DE BASE DE DATOS: LIBROVIBE
-- ==========================================================

CREATE DATABASE IF NOT EXISTS librovibe_db;
USE librovibe_db;

-- 1. Categorías (Maestra)
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- 2. Libros (Inventario)
CREATE TABLE libros (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    imagen_url VARCHAR(255), -- Aquí guardaremos el link de la foto (ej: de Google Images)
    id_categoria INT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_categoria_libro FOREIGN KEY (id_categoria) 
        REFERENCES categorias(id_categoria) ON DELETE SET NULL
);

-- 3. Usuarios (Seguridad y Roles)
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Almacenará el Hash de BCrypt
    email VARCHAR(100) UNIQUE,
    rol ENUM('ROLE_ADMIN', 'ROLE_CLIENTE') DEFAULT 'ROLE_CLIENTE',
    activo BOOLEAN DEFAULT TRUE
);

-- 4. Ventas (Cabecera)
CREATE TABLE ventas (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_venta DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    id_usuario INT, -- Quién realizó la venta
    CONSTRAINT fk_usuario_venta FOREIGN KEY (id_usuario) 
        REFERENCES usuarios(id_usuario)
);

-- 5. Detalle de Ventas (Para Reportes de productos más vendidos)
CREATE TABLE detalle_ventas (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_libro INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL, -- Precio al momento de la compra
    subtotal DECIMAL(10,2) AS (cantidad * precio_unitario),
    CONSTRAINT fk_venta_detalle FOREIGN KEY (id_venta) 
        REFERENCES ventas(id_venta) ON DELETE CASCADE,
    CONSTRAINT fk_libro_detalle FOREIGN KEY (id_libro) 
        REFERENCES libros(id_libro)
);

-- 6. Auditoría de Stock (Puntos extra en la Expo)
CREATE TABLE auditoria_stock (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    id_libro INT,
    cantidad_anterior INT,
    cantidad_nueva INT,
    motivo VARCHAR(100), -- 'VENTA', 'REPOSICIÓN', 'AJUSTE'
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_libro_audit FOREIGN KEY (id_libro) 
        REFERENCES libros(id_libro)
);

-- 1. CATEGORÍAS (10 filas)
INSERT INTO categorias (nombre, descripcion) VALUES 
('Ficción', 'Novelas, cuentos y literatura narrativa'),
('Tecnología', 'Programación, IA y Hardware'),
('Ciencia', 'Física, Astronomía y Biología'),
('Historia', 'Hechos históricos y biografías'),
('Autoayuda', 'Crecimiento personal y psicología'),
('Arte', 'Pintura, Música y Arquitectura'),
('Cocina', 'Recetarios y gastronomía internacional'),
('Infantil', 'Libros para niños y fábulas'),
('Negocios', 'Finanzas, Marketing y Emprendimiento'),
('Cómics', 'Novelas gráficas y manga');

-- 2. LIBROS (10 filas con fotos reales)
INSERT INTO libros (titulo, autor, precio, stock, imagen_url, id_categoria) VALUES 
('Clean Code', 'Robert C. Martin', 180.00, 15, '/fotos/clean_code.jpg', 2),
('El Principito', 'Antoine de Saint-Exupéry', 45.00, 20, '/fotos/elprincipito.jpg', 1),
('Cosmos', 'Carl Sagan', 95.00, 8, '/fotos/cosmos.jpg', 3),
('Sapiens', 'Yuval Noah Harari', 75.00, 12, '/fotos/sapiens.jpg', 4),
('Hábitos Atómicos', 'James Clear', 65.00, 25, '/fotos/habitos_atomicos.jpg', 5),
('Gomorra', 'Roberto Saviano', 55.00, 4, '/fotos/gomorra.jpg', 1),
('Padre Rico Padre Pobre', 'Robert Kiyosaki', 50.00, 30, '/fotos/padre_rico.jpg', 9),
('Steve Jobs', 'Walter Isaacson', 85.00, 6, '/fotos/steve_jobs.jpg', 4),
('The Pragmatic Programmer', 'Andrew Hunt', 190.00, 10, '/fotos/the_pragmatic.jpg', 2),
('Batman: Year One', 'Frank Miller', 120.00, 3, '/fotos/batman.jpg', 10);

-- 3. USUARIOS (10 filas - Clave genérica '123456')
INSERT INTO usuarios (username, password, email, rol, activo) VALUES 
('admin_lucho', '123456', 'lucho@librovibe.com', 'ROLE_ADMIN', 1),
('ana_cliente', '123456', 'ana@gmail.com', 'ROLE_CLIENTE', 1),
('pedro_cliente', '123456', 'pedro@gmail.com', 'ROLE_CLIENTE', 1),
('marta_admin', '123456', 'marta@librovibe.com', 'ROLE_ADMIN', 1),
('juan_admin', '123456', 'juan@librovibe.com', 'ROLE_ADMIN', 1),
('carla_cliente', '123456', 'carla@gmail.com', 'ROLE_CLIENTE', 1),
('diego_cliente', '123456', 'diego@gmail.com', 'ROLE_CLIENTE', 1),
('sofia_admin', '123456', 'sofia@librovibe.com', 'ROLE_ADMIN', 1),
('lucas_cliente', '123456', 'lucas@gmail.com', 'ROLE_CLIENTE', 1),
('elena_cliente', '123456', 'elena@gmail.com', 'ROLE_CLIENTE', 1);

-- 4. VENTAS (10 filas)
INSERT INTO ventas (total_venta, id_usuario) VALUES 
(225.00, 2), (180.00, 3), (95.00, 6), (125.00, 2), (50.00, 7),
(370.00, 3), (45.00, 9), (190.00, 2), (150.00, 10), (240.00, 6);

-- 5. DETALLE_VENTAS (10 filas vinculadas)
INSERT INTO detalle_ventas (id_venta, id_libro, cantidad, precio_unitario) VALUES 
(1, 1, 1, 180.00), (1, 2, 1, 45.00),
(2, 1, 1, 180.00), (3, 3, 1, 95.00),
(4, 2, 1, 45.00), (4, 4, 1, 75.00),
(5, 7, 1, 50.00), (6, 9, 1, 190.00),
(6, 1, 1, 180.00), (7, 2, 1, 45.00);

select * from usuarios

http://localhost:8080/
