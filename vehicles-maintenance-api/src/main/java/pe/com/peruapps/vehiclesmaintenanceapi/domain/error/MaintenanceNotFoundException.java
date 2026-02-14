package pe.com.peruapps.vehiclesmaintenanceapi.domain.error;

import lombok.Getter;

@Getter
public class MaintenanceNotFoundException extends RuntimeException {
  private final Long maintenanceId;

  public MaintenanceNotFoundException(Long id, String message) {
    super(message);
    this.maintenanceId = id;
  }
}
