# Endpoints

## Reportes

Todas las rutas son `GET` y responden JSON por defecto. Para exportar, usar el header `X-Report-Format` con valores `XLSX` o `CSV`.

- `/reports/segments` - total de empleados por segmento salarial (A/B/C).
- `/reports/departments/segments` - total de empleados por segmento y departamento.
- `/reports/departments/top-earners` - empleado(s) con mayor salario por departamento.
- `/reports/managers?minYears=15` - managers con antiguedad mayor al minimo indicado.
- `/reports/departments/salary-summary?minEmployees=10` - resumen de empleados y salario promedio por departamento, solo contabiliza los departamentos que tengan más del número indicado de empleados.
- `/reports/countries/salary-summary` - resumen salarial por pais (promedio, maximo, minimo y años promedio de actividad).

## Exportacion

Header:

- `X-Report-Format`: `JSON` (default), `XLSX`, `CSV`.
