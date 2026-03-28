# LIBROVIBE - Sistema de Gestión de Librería
**Librovibe** es una aplicación web desarrollada para administrar una librería de forma eficiente, permitiendo controlar libros, stock, usuarios y ventas en un solo sistema.
## Objetivo del proyecto
Digitalizar y optimizar los procesos principales de una librería para mejorar el control del inventario, registrar ventas de manera ordenada y facilitar la gestión diaria del negocio.
## Problema que resuelve
En muchos negocios pequeños, llevar control manual de productos y ventas provoca errores en inventario y pérdidas de información.  
Librovibe centraliza estos procesos para tener:
- stock actualizado en tiempo real,
- registro de ventas por usuario,
- historial de productos vendidos,
- mejor organización general del negocio.
## Funcionalidades principales
- Gestión de categorías de libros.
- Registro y administración de libros (título, autor, precio, stock e imagen).
- Gestión de usuarios con roles (`ROLE_ADMIN`, `ROLE_CLIENTE`).
- Registro de ventas y detalle de productos por venta.
- Auditoría de cambios de stock (venta, reposición, ajuste).
## Tecnologías y herramientas
- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **MySQL Workbench**
- **Maven**
- **Eclipse IDE**
- **HTML/CSS**
## Entorno de desarrollo utilizado
Este proyecto fue desarrollado principalmente en:
- **Eclipse IDE** (implementación del backend y estructura del sistema)
- **Spring Boot** (controladores, servicios, entidades y repositorios)
- **MySQL Workbench** (creación y administración de la base de datos)
## Arquitectura general
El sistema sigue una estructura en capas:
- **Controller**: recibe peticiones y controla el flujo.
- **Service**: contiene la lógica de negocio.
- **Repository**: acceso a datos con JPA.
- **Entity/Model**: representación de tablas de la base de datos.
## Modelo de base de datos
La base de datos `librovibe_db` está compuesta por:
- `categorias`
- `libros`
- `usuarios`
- `ventas`
- `detalle_ventas`
- `auditoria_stock`
Relaciones destacadas:
- Una categoría puede tener muchos libros.
- Una venta puede tener varios ítems en `detalle_ventas`.
- Cada venta está asociada a un usuario.
- Se registra auditoría para cambios de stock.
## Configuración de base de datos
1. Crear base de datos en MySQL:
```sql
CREATE DATABASE librovibe_db;
Ejecutar el script SQL del proyecto (estructura + datos de prueba).
Configuración de application.properties
spring.application.name=librovibe
spring.datasource.url=jdbc:mysql://localhost:3306/librovibe_db?useSSL=false&serverTimezone=America/Lima
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
spring.servlet.multipart.max-file-size=2MB
spring.servlet.multipart.max-request-size=2MB
spring.jackson.time-zone=America/Lima
Ejecución del proyecto
Clonar repositorio:
git clone https://github.com/Emanuel1919/LIBRERIA-.git
cd LIBRERIA-
Verificar configuración de base de datos en application.properties.
Ejecutar con Maven Wrapper:
mvnw.cmd spring-boot:run
Abrir en navegador:
http://localhost:8080/
Estado del proyecto
Proyecto académico funcional, preparado para seguir escalando con mejoras como:

autenticación avanzada,
reportes de ventas por fechas,
alertas automáticas de stock bajo,
dashboard administrativo.
Autor
Emanuel1919
