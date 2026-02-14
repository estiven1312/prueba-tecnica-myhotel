package pe.com.peruapps.vehiclesmaintenanceapi.domain.error;

import lombok.Getter;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

@Getter
public class ForbiddenChangeVehicleTypeException extends RuntimeException {
  private final Long vehicleId;
  private final VehicleType type;

  public ForbiddenChangeVehicleTypeException(Long id, VehicleType type, String message) {
    super(message);
    this.vehicleId = id;
    this.type = type;
  }
}
