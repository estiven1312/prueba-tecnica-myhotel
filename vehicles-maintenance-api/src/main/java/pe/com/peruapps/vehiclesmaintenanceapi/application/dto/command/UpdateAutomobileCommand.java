package pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

import java.math.BigDecimal;

public record UpdateAutomobileCommand(
    String brand,
    String model,
    String licensePlate,
    Long year,
    VehicleType vehicleType,
    BigDecimal cubicCapacity,
    BigDecimal mileage,
    String urlPhoto,
    Automobile.Type automobileType,
    Long numberOfDoors,
    Long passengerCapacity,
    Long trunkCapacity)
    implements UpdateVehicleCommand {}
