package pe.com.peruapps.vehiclesmaintenanceapi.domain.error;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

public class IllegalChangeStatusForMaintenanceException extends RuntimeException {
  public IllegalChangeStatusForMaintenanceException(
      Long id, Maintenance.Status illegalStatus, String message) {
    super(message);
  }
}
