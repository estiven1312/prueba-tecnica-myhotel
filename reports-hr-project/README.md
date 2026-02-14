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

## Puntos de mejora

- Implementar pruebas unitarias y de integración para asegurar la calidad del código.
- Agregar validaciones adicionales en los endpoints para mejorar la robustez.
- Implementar autenticación y autorización para proteger los endpoints.
- Agregar soporte para exportación en otros formatos (PDF, XML).
- Agregar métricas de uso de reportes para análisis de uso y optimización futura.
- Agregar generación de reportes programados (ej. reportes diarios/semanales) usando Spring Scheduler o un sistema de
  colas como RabbitMQ.
- Implementar un sistema de caché para reportes que se consultan frecuentemente, mejorando la performance y reduciendo
  la carga en la base de datos.
- Considerar el uso de vistas materializadas en la base de datos para reportes complejos que requieren agregaciones,
  mejorando la velocidad de consulta.

## Referencias

- `docs/ARCHITECTURE.md` - decisiones de arquitectura y diagrama de componentes.
- `docs/DEPLOY.md` - diagrama de despliegue y notas de CI/CD.
- `docs/ENDPOINTS.md` - listado de endpoints y formatos de exportacion.
- `docs/DEVELOPMENT.md` - guia de desarrollo local y Docker Compose. También incluye instrucciones para construir y
  ejecutar la imagen Docker de la aplicación.
