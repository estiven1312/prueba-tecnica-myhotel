# Development Guide - Vehicles Maintenance API

Esta guía proporciona instrucciones paso a paso para configurar el entorno de desarrollo y ejecutar la aplicación.

## 📋 Prerrequisitos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

| Herramienta | Versión Requerida | Verificar Instalación |
|-------------|-------------------|----------------------|
| **Java JDK** | 21 o superior | `java -version` |
| **Docker** | 20.x o superior | `docker --version` |
| **Docker Compose** | 2.x o superior | `docker-compose --version` |
| **Git** | 2.x o superior | `git --version` |

## Inicio Rápido

Existen dos formas de ejecutar la aplicación:

### Opción A: Usando Docker Compose (Recomendado)

Esta opción ejecuta tanto la base de datos MySQL como la aplicación en contenedores Docker.

```bash
# Construir y levantar todos los servicios
docker-compose up --build

# O en modo detached (background)
docker-compose up -d --build

# Ver logs
docker-compose logs -f app

# Detener los servicios
docker-compose down

# Detener y eliminar volúmenes (limpieza completa)
docker-compose down -v
```

**Salida esperada:**
```
✔ Container vehicles-maintenance-mysql  Started
✔ Container vehicles-maintenance-app    Started
```

La aplicación estará disponible en `http://localhost:8080/api/v1`

### Opción B: Desarrollo Local con MySQL en Docker

Esta opción ejecuta solo MySQL en Docker y la aplicación localmente.

#### 1. Clonar el Repositorio

```bash
git clone <repository-url>
cd vehicles-maintenance-api
```

#### 2. Levantar el Contenedor de MySQL

El proyecto incluye un archivo `docker-compose.yml` en el directorio `development/` para facilitar la configuración de MySQL.

```bash
cd development

docker-compose up -d

docker-compose ps
```

**Salida esperada:**
```
NAME                  SERVICE     STATUS      PORTS
development-mysql-1   mysql       running     0.0.0.0:3306->3306/tcp
```

#### Configuración del Contenedor Docker

El archivo `docker-compose.yml` configura:

```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: vehicles-maintenance-mysql
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: testdb
      MYSQL_USER: testuser
      MYSQL_PASSWORD: testpassword
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - vehicles-network

volumes:
  mysql_data:

networks:
  vehicles-network:
    driver: bridge
```

#### 3. Ejecutar la Aplicación

Regresa al directorio raíz del proyecto y ejecuta:

```bash
cd ..

./gradlew bootRun
```

**O usando el wrapper de Gradle en Windows:**
```bash
gradlew.bat bootRun
```

#### 4. Verificar que la Aplicación está Corriendo

La aplicación se iniciará en `http://localhost:8080/api/v1`

**Verificar el estado:**
```bash
curl http://localhost:8080/api/v1/actuator/health

http://localhost:8080/api/v1/swagger-ui.html
```

## 🐳 Docker - Construcción de Imagen con Layered JARs

### Requisito: JAR precompilado

El `Dockerfile` **no compila** la aplicación. Debes generar el JAR antes de construir la imagen.

```bash
./gradlew bootJar
```

El artefacto esperado es `build/libs/*.jar`.

### Construcción de imagen

```bash
docker build -t vehicles-maintenance-api:latest .
```

### Ejecución de la imagen

```bash
docker run -d \
  --name vehicles-app \
  --network vehicles-network \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/testdb \
  -e SPRING_DATASOURCE_USERNAME=testuser \
  -e SPRING_DATASOURCE_PASSWORD=testpassword \
  vehicles-maintenance-api:latest

docker logs -f vehicles-app

docker history vehicles-maintenance-api:latest
```

### Optimizaciones del Dockerfile

- **Multi-stage build**: Separa el proceso de build del runtime
- **JVM optimizado**: Configuración de JVM para entornos containerizados
- **Imagen Alpine**: Usa imágenes ligeras basadas en Alpine Linux

## 🗃️ Migraciones de Base de Datos con Flyway

La aplicación usa **Flyway** para gestionar las migraciones de base de datos automáticamente.

### Migraciones Disponibles

Las migraciones se encuentran en `src/main/resources/db/migration/`:

1. **V1_0_0__init_schema.sql**
   - Crea las tablas: `vehicles`, `automobiles`, `lorries`, `maintenances`
   - Crea índices para optimizar búsquedas
   - Configura relaciones y foreign keys

2. **V2_0_0__insert_sample_data.sql**
   - Inserta datos de ejemplo:
     - 2 automóviles (Toyota Corolla, Honda Civic)
     - 2 camiones (Volvo FH16, Scania R450)
     - 2 mantenimientos programados

### Ejecución de Migraciones

Las migraciones se ejecutan **automáticamente** al iniciar la aplicación.

**Configuración en `application.yaml`:**
```yaml
spring:
  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
    validate-on-migrate: true
```
