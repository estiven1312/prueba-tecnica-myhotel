package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

import java.math.BigDecimal;

@Schema(description = "Respuesta con información de un vehículo")
public record VehicleResponse(
    @Schema(description = "ID del vehículo", example = "1") Long id,
    @Schema(description = "Marca del vehículo", example = "Toyota") String brand,
    @Schema(description = "Modelo del vehículo", example = "Corolla") String model,
    @Schema(description = "Placa de licencia", example = "ABC-123") String licensePlate,
    @Schema(description = "Año de fabricación", example = "2024") Long year,
    @Schema(
            description = "Tipo de vehículo",
            example = "AUTOMOBILE",
            allowableValues = {"AUTOMOBILE", "LORRY"})
        VehicleType vehicleType,
    @Schema(description = "Capacidad cúbica del motor (cc)", example = "1800.00")
        BigDecimal cubicCapacity,
    @Schema(description = "Kilometraje actual", example = "5000.50") BigDecimal mileage,
    @Schema(
            description = "URL de la foto del vehículo",
            example = "https://example.com/photos/toyota-corolla.jpg")
        String urlPhoto,
    @Schema(description = "Detalles específicos según el tipo de vehículo") Details details) {

  @Schema(description = "Interfaz para detalles específicos del vehículo")
  public sealed interface Details permits AutomobileData, LorryData {}

  @Schema(description = "Datos específicos de un automóvil")
  public record AutomobileData(
      @Schema(
              description = "Tipo de automóvil",
              example = "SEDAN",
              allowableValues = {"SEDAN", "HATCHBACK", "SUV", "COUPE", "CONVERTIBLE"})
          String automobileType,
      @Schema(description = "Número de puertas", example = "4") Long numberOfDoors,
      @Schema(description = "Capacidad de pasajeros", example = "5") Long passengerCapacity,
      @Schema(description = "Capacidad del maletero (litros)", example = "450") Long trunkCapacity)
      implements Details {}

  @Schema(description = "Datos específicos de un camión")
  public record LorryData(
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
              })
          String lorryType,
      @Schema(description = "Número de ejes", example = "3") Long axisNumber,
      @Schema(description = "Capacidad de tonelaje (kg)", example = "25000") Long tonnageCapacity)
      implements Details {}
}
