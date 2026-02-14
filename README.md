# Prueba Técnica MyHotel

Repositorio con 3 iniciativas relacionadas a backend y base de datos en Java/Spring Boot + MySQL.

## Contenido del repositorio

### 1) Base de datos de soporte
- Carpeta: **bd-myhotel/**
- Incluye:
	- `docker-compose.yml` para levantar MySQL local.
	- Scripts SQL en `init/` y `solutions.sql`.

### 2) Proyecto de reportes de RRHH
- Carpeta: **reports-hr-project/**
- Stack principal:
	- Java 21
	- Spring Boot (MVC + JDBC)
	- MySQL
	- OpenAPI (springdoc)

### 3) API de mantenimiento de vehículos
- Carpeta: **vehicles-maintenance-api/**
- Stack principal:
	- Java 21
	- Spring Boot (MVC + JPA)
	- MySQL + Flyway
	- OpenAPI (springdoc)
	- JaCoCo para cobertura de pruebas

## Prerrequisitos

- Java 21
- Docker + Docker Compose
- Git

> Ambos servicios Java usan **Gradle Wrapper**, por lo que no es obligatorio tener Gradle instalado globalmente.

## Levantar cada componente

### A. Base MySQL para `reports-hr-project`

Desde la carpeta `reports-hr-project/development`:

- Puerto: `3308`
- Base: `mydatabase`
- Usuario: `myuser`
- Password: `mypassword`

### B. Base MySQL para `vehicles-maintenance-api`

Desde la carpeta `vehicles-maintenance-api/development`:

- Puerto: `3306`
- Base: `testdb`
- Usuario: `testuser`
- Password: `testpassword`

### C. Ejecutar los servicios

#### reports-hr-project
- Context path: `/api/v1`
- Puerto HTTP: `8081`

Ejecutar desde la carpeta `reports-hr-project` usando `./gradlew bootRun`.

#### vehicles-maintenance-api
- Context path: `/api/v1`
- Puerto HTTP por defecto de Spring Boot: `8080`

Ejecutar desde la carpeta `vehicles-maintenance-api` usando `./gradlew bootRun`.

## Documentación y referencias

- Reportes RRHH (detalle): `reports-hr-project/README.md`
- API Vehículos (detalle): `vehicles-maintenance-api/README.md`
- Arquitectura de reportes: `reports-hr-project/docs/ARCHITECTURE.md`
- Scripts SQL de apoyo: `bd-myhotel/solutions.sql`

## Notas

- Si ejecutas ambos servicios en paralelo, no hay conflicto de puertos HTTP (`8080` y `8081`).
- Las dos bases MySQL están separadas para evitar mezcla de datos y facilitar pruebas locales.
