# Vista General de Arquitectura

Este proyecto implementa **Clean Architecture** (Arquitectura Limpia) de Robert C. Martin, organizando el código en capas con dependencias que fluyen hacia el centro (el dominio).
Se eligió esta arquitectura para lograr una alta cohesión y bajo acoplamiento entre las diferentes partes del sistema, lo que facilita el mantenimiento, la escalabilidad y la testabilidad del código. Además, Clean Architecture permite una clara separación de responsabilidades, lo que hace que el código sea más fácil de entender y modificar en el futuro.
Además, en el contexto de Startups, en donde las tecnologías y los requisitos pueden cambiar rápidamente, Clean Architecture proporciona la flexibilidad necesaria para adaptarse a estos cambios sin afectar significativamente el código existente. Esto es especialmente importante en un entorno de desarrollo ágil, donde la capacidad de iterar rápidamente y adaptarse a nuevas necesidades es crucial para el éxito del proyecto.

## Estructura en Resumen

- **Capa de Aplicacion**: Casos de uso coordinan acciones de negocio.
- **Capa de Dominio**: Entidades y reglas de dominio.
- **Capa de Infraestructura**: Adaptadores de persistencia e integraciones externas.
- **Compartido**: DTOs transversales y utilidades.

## Componentes

### Estructura de Capas

```
┌─────────────────────────────────────────────────────────────┐
│                    Infrastructure Layer                      │
│  (Controllers, Repositories, Configs, External Services)    │
│                  ┌─────────────────────┐                     │
│                  │  Application Layer  │                     │
│                  │  (Use Cases, DTOs)  │                     │
│                  │  ┌───────────────┐  │                     │
│                  │  │ Domain Layer  │  │                     │
│                  │  │   (Entities)  │  │                     │
│                  │  └───────────────┘  │                     │
│                  └─────────────────────┘                     │
└─────────────────────────────────────────────────────────────┘
```

### Capa de Aplicacion
- Ubicacion: `src/main/java/pe/com/peruapps/vehiclesmaintenanceapi/application`
- Responsabilidades:
  - Orquesta acciones de negocio mediante casos de uso.
  - Define puertos de entrada/salida (interfaces) para dependencias.
  - Contiene DTOs para comandos/consultas.

### Capa de Dominio
- Ubicacion: `src/main/java/pe/com/peruapps/vehiclesmaintenanceapi/domain`
- Responsabilidades:
  - Entidades centrales como `Vehicle`, `Maintenance` y objetos de valor.
  - Reglas y excepciones de dominio.

### Capa de Infraestructura
- Ubicacion: `src/main/java/pe/com/peruapps/vehiclesmaintenanceapi/infrastructure`
- Responsabilidades:
  - Implementa puertos de repositorio.
  - Integra persistencia (por ejemplo, JPA/MySQL) y otros servicios.

### Compartido
- Ubicacion: `src/main/java/pe/com/peruapps/vehiclesmaintenanceapi/shared`
- Responsabilidades:
  - DTOs comunes como modelos de paginacion.
  - Utilidades compartidas entre capas.

## Flujo de Dependencias

- API -> Aplicacion -> Dominio
- Infraestructura depende de puertos de Aplicacion y modelos de Dominio
- Dominio no depende de otras capas

## Pruebas

- Los Usecase de la aplicacion se prueban con puertos simulados bajo `src/test/java`.
- Las entidades de dominio se validan con pruebas unitarias.

## ADR - Architectural Decision Records

En este proyecto se han tomado varias decisiones arquitectónicas importantes, las cuales se han documentado utilizando el formato de Architectural Decision Records (ADR). Estas decisiones incluyen:

1. **Elección del Estilo de Arquitectura**: Se decidió utilizar la arquitectura limpia (Clean Architecture) para organizar el código y separar las responsabilidades de manera clara.
2. **Uso de SWAGGER**: Se optó por utilizar SWAGGER para documentar y definir las APIs REST, lo que facilita la comunicación entre el frontend y el backend. Este estandar facilita la generación de documentación y la creación de clientes para consumir las APIs.
3. **Persistencia de Datos**: Se eligió MySQL como sistema de gestión de base de datos, ya que el siguiente punto de la prueba técnica lo requiere, y se utilizó JPA para la gestión de la persistencia de datos.

## Clean Architecture

### Entidades Principales
- **Vehicle**: Representa un vehículo con atributos como `id`, `licensePlate`, `type` (Automobile o Lorry).
- **Lorry**: Representa un camión con atributos como `axisNumber`, `tonnageCapacity`.
- **Automobile**: Representa un automóvil con atributos como `numberOfDoors`, `passengerCapacity`.
- **Maintenance**: Representa un mantenimiento con atributos como `id`, `description`

#### ¿Por qué se eligió la herencia?
Se eligió la herencia para modelar la relación entre `Vehicle`, `Automobile` y `Lorry` porque ambos tipos de vehículos comparten atributos comunes (como `id` y `licensePlate`), pero también tienen atributos específicos que los diferencian. La herencia permite reutilizar el código común en la clase base `Vehicle`, mientras que las clases derivadas `Automobile` y `Lorry` pueden tener sus propios atributos y comportamientos específicos.
Se evaluó con otras alternativas:
- Una tabla única con un campo discriminator para diferenciar entre automóviles y camiones, pero esto podría llevar a una gran cantidad de campos nulos y dificultar la gestión de las entidades.
- Composición, donde `Vehicle` tendría una relación con `Automobile` y `Lorry`, pero esto complicaría la consulta y gestión de los vehículos, ya que se necesitarían múltiples ids y relaciones para manejar los diferentes tipos de vehículos.
- Interfaces, pero esto no sería adecuado para modelar la relación de herencia entre los vehículos, ya que ambos tipos de vehículos comparten atributos comunes que se beneficiarían de una clase base común.

Se eligió la herencia porque proporciona una estructura clara y eficiente para modelar la relación entre los vehículos, permitiendo una fácil extensión en el futuro si se agregan nuevos tipos de vehículos sin afectar la estructura existente.

Bastaría con agregar el tipo de vehículo, los mappers necesarios y las requests específicas para tener un nuevo vehículo funcionando.

También, al ser una prueba técnica, se decidió implementar la herencia para demostrar el conocimiento en este concepto de programación orientada a objetos, aunque en un proyecto real se podría considerar otras alternativas dependiendo de los requisitos específicos (hacer joins es costoso en terminos de rendimiento, y solo tenerlos en una tabla puede ser una mejor alternativa en casos donde el rendimiento para reportes importe mucho) y la complejidad del dominio.


### Manejo de Errores

#### Problem Details
Todos los errores se devuelven en el formato:
```json
{
  "title": "Vehicle Not Found",
  "status": 404,
  "detail": "Vehicle not found with id: 999",
  "timestamp": "2026-02-12T10:30:00Z",
  "vehicleId": 999
}
```

#### Excepciones de Dominio
- `VehicleNotFoundException` (404)
- `MaintenanceNotFoundException` (404)
- `DuplicatedLicensePlateInActiveVehicleException` (409)
- `ForbiddenChangeVehicleTypeException` (409)
- `IllegalChangeStatusForMaintenanceException` (400)
