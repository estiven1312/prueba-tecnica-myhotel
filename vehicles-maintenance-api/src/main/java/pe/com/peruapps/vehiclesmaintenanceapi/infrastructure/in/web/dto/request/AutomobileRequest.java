package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

import java.math.BigDecimal;

@Schema(description = "Request para registrar/actualizar un automóvil")
@JsonTypeName("AUTOMOBILE")
public record AutomobileRequest(
    @Schema(
            description = "Marca del vehículo",
            example = "Toyota",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Brand is required")
        String brand,
    @Schema(
            description = "Modelo del vehículo",
            example = "Corolla",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Model is required")
        String model,
    @Schema(
            description = "Placa de licencia del vehículo",
            example = "ABC-123",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "License plate is required")
        String licensePlate,
    @Schema(
            description = "Año de fabricación",
            example = "2024",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Year is required")
        @Positive(message = "Year must be positive")
        Long year,
    @Schema(
            description = "Tipo de vehículo",
            example = "AUTOMOBILE",
            allowableValues = {"AUTOMOBILE", "LORRY"},
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType,
    @Schema(
            description = "Capacidad cúbica del motor (cc)",
            example = "1800.00",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Cubic capacity is required")
        @Positive(message = "Cubic capacity must be positive")
        BigDecimal cubicCapacity,
    @Schema(
            description = "Kilometraje actual",
            example = "5000.50",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Mileage is required")
        @Positive(message = "Mileage must be positive")
        BigDecimal mileage,
    @Schema(
            description = "URL de la foto del vehículo",
            example = "https://example.com/photos/toyota-corolla.jpg")
        String urlPhoto,
    @Schema(
            description = "Tipo de automóvil",
            example = "SEDAN",
            allowableValues = {"SEDAN", "HATCHBACK", "SUV", "COUPE", "CONVERTIBLE"},
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Automobile type is required")
        Automobile.Type automobileType,
    @Schema(
            description = "Número de puertas",
            example = "4",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Number of doors is required")
        @Positive(message = "Number of doors must be positive")
        Long numberOfDoors,
    @Schema(
            description = "Capacidad de pasajeros",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Passenger capacity is required")
        @Positive(message = "Passenger capacity must be positive")
        Long passengerCapacity,
    @Schema(
            description = "Capacidad del maletero (litros)",
            example = "450",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Trunk capacity is required")
        @Positive(message = "Trunk capacity must be positive")
        Long trunkCapacity)
    implements VehicleRequest {}
