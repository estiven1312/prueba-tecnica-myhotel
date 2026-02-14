# README

Este proyecto se encuentra en desarrollo, por lo que no se desarrolló como parte de una prueba técnica para la posición
de Backend Engineer.

El proyecto se basa en un Backend que se centra en el CRUD de Mantenimientos y Vehículos, utilizando SPRING BOOT, JPA,
MySQL, Flyway y el concepto de CLEAN ARQUITECTURE

## Documentación

- Arquitectura: `docs/ARCHITECTURE.md`
- Desarrollo: `docs/DEVELOPMENT.md`
- Despliegue: `docs/DEPLOY.md`
- Endpoints: `docs/ENDPOINTS.md`

## Endpoints

- cURL y ejemplos: `docs/ENDPOINTS.md`
- Se recomienda levantar la app y probar usando swagger-ui en `http://localhost:8080/api/v1/swagger-ui.html`

### Puntos Pendientes

- Implementar más pruebas unitarias y de integración.
- Implementar validaciones adicionales en los endpoints.
- Agregar autenticación y autorización para proteger los endpoints.
- Implementar subida de imágenes para los vehículos, mantenimientos (Usando S3 o un sistema parecido más barato como
  DIGITAL OCEAN)
- Implementar un sistema de notificaciones para los mantenimientos programados (usando RabbitMQ o AMAZON SQS, o
  implementando directamente el envio de correos con MAILTRAP)
- Implementación de observabilidad con Spring Actuator y Micrometer, integrando con Prometheus y Grafana para monitoreo
  en tiempo real.
- Implementación de un algoritmo de regresión o arbol de decisión para alertar a los usuarios sobre mantenimientos
  próximos o posibles fallas basándose en el historial de mantenimientos y el kilometraje de los vehículos.
