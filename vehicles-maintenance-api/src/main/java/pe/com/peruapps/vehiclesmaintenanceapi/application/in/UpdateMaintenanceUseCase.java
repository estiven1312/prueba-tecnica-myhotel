package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

public interface UpdateMaintenanceUseCase {
  Maintenance execute(Long id, UpdateMaintenanceCommand command);
}
