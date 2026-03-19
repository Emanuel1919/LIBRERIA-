# LIBROVIBE - Sistema de Gestión de Librería
**Librovibe** es una aplicación web desarrollada para administrar una librería de forma eficiente, permitiendo controlar libros, stock, usuarios y ventas en un solo sistema.
## Objetivo del proyecto
Digitalizar los procesos principales de una librería para mejorar el control del inventario, registrar ventas de manera ordenada y facilitar la gestión diaria del negocio.
## Problema que resuelve
En una librería pequeña o mediana, llevar control manual de productos y ventas genera errores frecuentes.  
Librovibe centraliza la información para tener:
- stock actualizado,
- registro de ventas por usuario,
- historial de productos vendidos,
- mejor organización operativa.
## Funcionalidades principales
- Gestión de categorías de libros.
- Registro y administración de libros (título, autor, precio, stock e imagen).
- Gestión de usuarios con roles (`ROLE_ADMIN`, `ROLE_CLIENTE`).
- Registro de ventas y detalle por producto.
- Auditoría de cambios de stock (ventas, reposición, ajuste).
## Tecnologías utilizadas
- **Java + Spring Boot**
- **Spring Data JPA**
- **MySQL** (administrado con MySQL Workbench)
- **Maven**
- **HTML/CSS** para la interfaz
## Modelo de base de datos
La base de datos `librovibe_db` está compuesta por las tablas:
- `categorias`
- `libros`
- `usuarios`
- `ventas`
- `detalle_ventas`
- `auditoria_stock`
Relaciones destacadas:
- Una categoría puede tener muchos libros.
- Una venta puede tener varios ítems en `detalle_ventas`.
- Cada venta puede estar asociada a un usuario.
## Ejecución rápida
1. Clonar repositorio:
   ```bash
   git clone https://github.com/Emanuel1919/LIBRERIA-.git
   cd LIBRERIA-
Crear base de datos:

CREATE DATABASE librovibe_db;
Luego ejecutar el script SQL del proyecto (estructura + datos de prueba).

Configurar application.properties con MySQL local:

usuario: root
contraseña: root
Ejecutar aplicación:

mvnw.cmd spring-boot:run
Abrir en navegador:

http://localhost:8080/
Configuración principal usada
spring.application.name=librovibe
spring.datasource.url=jdbc:mysql://localhost:3306/librovibe_db?useSSL=false&serverTimezone=America/Lima
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jackson.time-zone=America/Lima
Estado del proyecto
Proyecto funcional para fines académicos, con estructura preparada para escalar a mejoras como:

autenticación más robusta,
reportes avanzados de ventas,
panel administrativo con métricas.
Autor
Emanuel1919

