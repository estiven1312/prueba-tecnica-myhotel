# Endpoints (cURL)

Base URL local (segun configuracion actual):

```bash
BASE_URL="http://localhost:8080/api/v1"
```
## Swagger

Si la aplicacion esta en ejecucion, Swagger UI se encuentra en:

```bash
http://localhost:8080/api/v1/swagger-ui.html
```

## Vehicles

### Crear automovil

```bash
curl -X POST "$BASE_URL/vehicles" \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Toyota",
    "model": "Corolla",
    "licensePlate": "ABC-123",
    "year": 2024,
    "vehicleType": "AUTOMOBILE",
    "cubicCapacity": 1800.00,
    "mileage": 5000.50,
    "urlPhoto": "https://example.com/photos/toyota-corolla.jpg",
    "automobileType": "SEDAN",
    "numberOfDoors": 4,
    "passengerCapacity": 5,
    "trunkCapacity": 450
  }'
```

### Crear camion

```bash
curl -X POST "$BASE_URL/vehicles" \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Volvo",
    "model": "FH16",
    "licensePlate": "TRK-001",
    "year": 2023,
    "vehicleType": "LORRY",
    "cubicCapacity": 16000.00,
    "mileage": 25000.00,
    "urlPhoto": "https://example.com/photos/volvo-fh16.jpg",
    "lorryType": "TRAILER",
    "axisNumber": 3,
    "tonnageCapacity": 25000
  }'
```

### Obtener vehiculo por ID

```bash
curl -X GET "$BASE_URL/vehicles/1"
```

### Actualizar vehiculo (automovil)

```bash
curl -X PUT "$BASE_URL/vehicles/1" \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Toyota",
    "model": "Corolla",
    "licensePlate": "ABC-123",
    "year": 2024,
    "vehicleType": "AUTOMOBILE",
    "cubicCapacity": 1800.00,
    "mileage": 5500.00,
    "urlPhoto": "https://example.com/photos/toyota-corolla.jpg",
    "automobileType": "SEDAN",
    "numberOfDoors": 4,
    "passengerCapacity": 5,
    "trunkCapacity": 450
  }'
```

### Eliminar vehiculo

```bash
curl -X DELETE "$BASE_URL/vehicles/1" \
  -H "X-User: admin"
```

### Listar vehiculos (con filtros opcionales)

```bash
curl -X GET "$BASE_URL/vehicles?page=0&size=10&sortBy=brand&order=ASC&brand=Toyota&model=Corolla&licensePlate=ABC-123&vehicleType=AUTOMOBILE&fromYear=2020&toYear=2024"
```

## Maintenances

### Crear mantenimiento

```bash
curl -X POST "$BASE_URL/maintenances" \
  -H "Content-Type: application/json" \
  -d '{
    "vehicleId": 1,
    "description": "Cambio de aceite y filtro",
    "scheduledDate": "2026-03-15",
    "kilometersAtMaintenance": 15000.50,
    "engineStatus": "GOOD",
    "brakesStatus": "GOOD",
    "tiresStatus": "NEEDS_ATTENTION",
    "transmissionStatus": "GOOD",
    "electricalStatus": "GOOD",
    "type": "OIL_CHANGE",
    "comments": "Se recomienda revisar el nivel de liquido de frenos"
  }'
```

### Obtener mantenimiento por ID

```bash
curl -X GET "$BASE_URL/maintenances/1"
```

### Actualizar mantenimiento

```bash
curl -X PUT "$BASE_URL/maintenances/1" \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Cambio de aceite y filtro de aire",
    "scheduledDate": "2026-03-20",
    "kilometersAtMaintenance": 15500.00,
    "engineStatus": "GOOD",
    "brakesStatus": "NEEDS_ATTENTION",
    "tiresStatus": "GOOD",
    "transmissionStatus": "GOOD",
    "electricalStatus": "GOOD",
    "type": "OIL_CHANGE",
    "comments": "Revisar liquido de frenos"
  }'
```

### Actualizar estado de mantenimiento

```bash
curl -X PATCH "$BASE_URL/maintenances/1/status" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "IN_PROGRESS"
  }'
```

### Eliminar mantenimiento

```bash
curl -X DELETE "$BASE_URL/maintenances/1\
  -H "X-User: admin"
```

### Listar mantenimientos (con filtros opcionales)

```bash
curl -X GET "$BASE_URL/maintenances?page=0&size=10&sortBy=scheduledDate&order=ASC&description=aceite&fromDate=2024-01-01&toDate=2024-12-31&vehicleId=1&type=OIL_CHANGE&status=SCHEDULED"
```


