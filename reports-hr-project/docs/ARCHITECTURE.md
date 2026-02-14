## Reportes disponibles

Formato de salida:

- Por defecto JSON.
- Para exportar: header `X-Report-Format` con valores `xlsx` o `csv`.

Rutas (todas con `GET`):

- `/reports/segments` - total de empleados por segmento salarial (A/B/C).
- `/reports/departments/segments` - total de empleados por segmento y departamento.
- `/reports/departments/top-earners` - empleado(s) con mayor salario por departamento.
- `/reports/managers?minYears=15` - managers con antigüedad mayor al mínimo indicado.
- `/reports/departments/salary-summary?minEmployees=10` - resumen de empleados y salario promedio por departamento.
- `/reports/countries/salary-summary` - resumen salarial por país (promedio, máximo, mínimo y años promedio de actividad).

## ADR - Architectural Decision Records

### ADR-1: Arquitectura en Capas sobre Clean Architecture

**Contexto**

El sistema es un servicio de reportes centrado en consultas a una base de datos relacional tipo HR. El esquema es estable, conocido y no se espera que cambie con frecuencia. El flujo principal es lectura (reportes), sin escrituras intensivas ni lógica de negocio compleja.

**Decisión**

Se adopta una arquitectura en capas (Controller/Service/Repository) en lugar de Clean Architecture.

**Justificación**

- La arquitectura en capas es suficiente para el alcance actual y reduce complejidad innecesaria.
- El esquema HR es estable y bien conocido, por lo que la separación adicional de dominios y adaptadores aporta poco valor.

**Consecuencias**

- Menor sobrecarga estructural y menor tiempo de implementación.
- Cambios futuros en el esquema podrían requerir ajustes directos en la capa Repository.

### ADR-2: JDBC sobre JPA/ORM

**Contexto**

El sistema requiere ejecutar consultas SQL específicas y directas para reportes. No se gestionan entidades con relaciones complejas ni se requiere sincronización de objetos en memoria. El enfoque es de lectura y consultas agregadas.

**Decisión**

Se utiliza Spring JDBC con `JdbcTemplate` en lugar de JPA/ORM.

**Justificación**

- Se necesitan consultas SQL directas y optimizadas para reportes.
- No se justifica el costo de un ORM cuando no hay un modelo de entidades complejo.
- Menor sobrecarga en tiempo de ejecución y mayor control del SQL.
- Reduce el esfuerzo de mapeo y la configuración de entidades.

**Consecuencias**

- El mapeo de resultados se realiza manualmente.
- Se mantiene control explícito de las consultas y su performance.
- Si en el futuro se requiere un modelo de dominio rico, se puede reconsiderar JPA.

### ADR-3: Exportacion en multiples formatos (JSON/CSV/XLSX)

**Contexto**

Los reportes deben consumirse tanto por integraciones (JSON) como por usuarios que requieren descargar archivos (CSV/XLSX). Se necesita un mecanismo simple para escoger el formato sin duplicar la logica de consultas.

**Decision**

Se define un header `X-Report-Format` para seleccionar el formato y se utiliza un `ReportExportFacade` con estrategias de exportacion por formato.

**Justificacion**

- Se mantiene el JSON como respuesta por defecto (facil de integrar).
- CSV y XLSX se resuelven mediante estrategias desacopladas y reutilizables.
- El facade centraliza la seleccion del formato y evita logica duplicada en el controller.

**Consecuencias**

- Las exportaciones agregan dependencias ligeras (OpenCSV y Apache POI).
- Si se agrega un nuevo formato, basta implementar `ReportExportStrategy`.

## DISEÑO DE FACADE Y STRATEGIES PARA EXPORTACION

**Objetivo**

Unificar la exportacion de reportes en distintos formatos (JSON/CSV/XLSX) sin duplicar la logica de consultas ni el armado de filas.

**Estructura**

- `ReportExportStrategy` define el contrato comun de exportacion (mismos metodos para cada reporte).
- `CsvExportService` y `XlsxExportService` implementan `ReportExportStrategy` y conocen como generar cada formato.
- `ReportExportFacade` recibe el `ReportFormat` y delega al strategy correcto.

**Flujo**

1. El controller obtiene las filas (JSON por defecto).
2. Si existe header `X-Report-Format`, el facade selecciona el strategy correspondiente.
3. El strategy devuelve `byte[]` y metadatos (content-type/ext).
4. El controller responde con JSON o archivo descargable segun formato.

**Beneficios**

- Agregar un nuevo formato requiere solo una nueva implementacion de `ReportExportStrategy`.
- Se evita duplicar logica en los controllers.
- La seleccion de formato queda centralizada y probada en una sola clase.
