# Reports HR Project

Este proyecto se enfoca en la generación de reportes para el departamento de recursos humanos, con una arquitectura en
capas (Controller/Service/Repository) para mantener simplicidad y claridad.

## Prerequisitos

- Java 21
- Docker y Docker Compose (para MySQL local)
- Gradle Wrapper (incluido como `./gradlew`)

## Development

Para desarrollo local se usa Docker Compose con MySQL. La carpeta `development/` contiene la definicion del servicio y
los scripts de inicializacion.

- Archivo: `development/docker-compose.yml`
- Scripts SQL: `development/init/` (se montan en `/docker-entrypoint-initdb.d`)

Para levantar la base:

```bash
cd development

docker compose up -d
```

## Consideraciones adicionales

Si bien la prueba solo pedía exportación en JSON, se implementó una arquitectura que permite fácilmente agregar nuevos
formatos (CSV, XLSX) sin modificar la lógica de consultas, utilizando un patrón de estrategia para la exportación. Esto
debido a que se consideró que en un entorno real, la exportación a otros formatos es una necesidad común y se quiso
demostrar la capacidad de diseñar un sistema extensible desde el inicio.
