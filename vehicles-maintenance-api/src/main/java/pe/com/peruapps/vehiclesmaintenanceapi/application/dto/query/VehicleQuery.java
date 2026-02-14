package pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

public record VehicleQuery(
    String brand,
    String model,
    String licensePlate,
    VehicleType vehicleType,
    Long fromYear,
    Long toYear) {}
