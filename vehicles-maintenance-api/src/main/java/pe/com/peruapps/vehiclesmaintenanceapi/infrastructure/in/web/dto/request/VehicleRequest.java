package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

import java.math.BigDecimal;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "vehicleType",
    visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = AutomobileRequest.class, name = "AUTOMOBILE"),
  @JsonSubTypes.Type(value = LorryRequest.class, name = "LORRY")
})
public sealed interface VehicleRequest permits AutomobileRequest, LorryRequest {
  String brand();

  String model();

  String licensePlate();

  Long year();

  VehicleType vehicleType();

  BigDecimal cubicCapacity();

  BigDecimal mileage();

  String urlPhoto();
}
