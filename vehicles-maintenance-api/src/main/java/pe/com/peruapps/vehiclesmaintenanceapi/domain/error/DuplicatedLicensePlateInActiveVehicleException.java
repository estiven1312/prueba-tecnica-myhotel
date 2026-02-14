package pe.com.peruapps.vehiclesmaintenanceapi.domain.error;

import lombok.Getter;

@Getter
public class DuplicatedLicensePlateInActiveVehicleException extends RuntimeException {
  private final Long vehicleId;
  private final String licensePlate;

  public DuplicatedLicensePlateInActiveVehicleException(
      Long id, String licensePlate, String message) {
    super(message);
    this.vehicleId = id;
    this.licensePlate = licensePlate;
  }
}
