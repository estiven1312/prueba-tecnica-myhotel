package pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

import java.math.BigDecimal;

public record RegisterLorryCommand(
    String brand,
    String model,
    String licensePlate,
    Long year,
    VehicleType vehicleType,
    BigDecimal cubicCapacity,
    BigDecimal mileage,
    String urlPhoto,
    Lorry.Type lorryType,
    Long axisNumber,
    Long tonnageCapacity)
    implements RegisterVehicleCommand {}
