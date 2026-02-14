package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

public interface RegisterMaintenanceUseCase {
  Maintenance execute(RegisterMaintenanceCommand command);
}
