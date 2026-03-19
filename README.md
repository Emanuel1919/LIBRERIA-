# LIBRERIA-

Sistema de librería desarrollado con **Spring Boot**, **Java** y **MySQL** para la gestión de libros, control de stock y registro de ventas.

## Descripción

Este proyecto permite administrar una librería mediante una aplicación web responsiva, facilitando procesos clave del negocio como:

- Gestión de libros (alta, edición, eliminación y consulta)
- Control de inventario/stock
- Registro de ventas
- Administración básica de usuarios/clientes

## Tecnologías utilizadas

- **Java**
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA**
- **MySQL** (gestionado con MySQL Workbench)
- **Maven**
- **HTML/CSS** (frontend)

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java JDK 17** (o la versión que use tu `pom.xml`)
- **Maven 3.8+**
- **MySQL Server 8+**
- **MySQL Workbench** (opcional, para administrar la base de datos)

## Configuración de base de datos

1. Crear una base de datos en MySQL, por ejemplo:

```sql
CREATE DATABASE libreria_db;
