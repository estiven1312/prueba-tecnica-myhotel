package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

import java.math.BigDecimal;

@Schema(description = "Request para registrar/actualizar un camión")
@JsonTypeName("LORRY")
public record LorryRequest(
    @Schema(
            description = "Marca del vehículo",
            example = "Volvo",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Brand is required")
        String brand,
    @Schema(
            description = "Modelo del vehículo",
            example = "FH16",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Model is required")
        String model,
    @Schema(
            description = "Placa de licencia del vehículo",
            example = "TRK-001",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "License plate is required")
        String licensePlate,
    @Schema(
            description = "Año de fabricación",
            example = "2023",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Year is required")
        @Positive(message = "Year must be positive")
        Long year,
    @Schema(
            description = "Tipo de vehículo",
            example = "LORRY",
            allowableValues = {"AUTOMOBILE", "LORRY"},
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType,
    @Schema(
            description = "Capacidad cúbica del motor (cc)",
            example = "16000.00",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Cubic capacity is required")
        @Positive(message = "Cubic capacity must be positive")
        BigDecimal cubicCapacity,
    @Schema(
            description = "Kilometraje actual",
            example = "25000.00",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Mileage is required")
        @Positive(message = "Mileage must be positive")
        BigDecimal mileage,
    @Schema(
            description = "URL de la foto del vehículo",
            example = "https://example.com/photos/volvo-fh16.jpg")
        String urlPhoto,
    @Schema(
            description = "Tipo de camión",
            example = "TRAILER",
            allowableValues = {
              "TYPE_THREE_QUARTER",
              "TRAILER",
              "SEMI_TRAILER",
              "TANKER",
              "DUMP",
              "REFRIGERATED"
            },
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Lorry type is required")
        Lorry.Type lorryType,
    @Schema(
            description = "Número de ejes",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Axis number is required")
        @Positive(message = "Axis number must be positive")
        Long axisNumber,
    @Schema(
            description = "Capacidad de tonelaje (kg)",
            example = "25000",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Tonnage capacity is required")
        @Positive(message = "Tonnage capacity must be positive")
        Long tonnageCapacity)
    implements VehicleRequest {}
