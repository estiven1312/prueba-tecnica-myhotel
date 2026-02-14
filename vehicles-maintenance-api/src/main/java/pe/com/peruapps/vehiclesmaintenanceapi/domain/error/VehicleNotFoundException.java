package pe.com.peruapps.vehiclesmaintenanceapi.domain.error;

import lombok.Getter;

@Getter
public class VehicleNotFoundException extends RuntimeException {
  private final Long vehicleId;

  public VehicleNotFoundException(Long id, String message) {
    super(message);
    this.vehicleId = id;
  }
}
