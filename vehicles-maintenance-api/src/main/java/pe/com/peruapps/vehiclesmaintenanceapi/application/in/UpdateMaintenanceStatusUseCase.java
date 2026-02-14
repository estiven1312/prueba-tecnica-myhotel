package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

public interface UpdateMaintenanceStatusUseCase {
  void execute(Long maintenanceId, Maintenance.Status newStatus);
}
