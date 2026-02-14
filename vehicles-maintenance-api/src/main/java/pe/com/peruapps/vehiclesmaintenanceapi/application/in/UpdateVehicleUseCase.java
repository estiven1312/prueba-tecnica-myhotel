package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;

public interface UpdateVehicleUseCase {
  Vehicle execute(Long id, UpdateVehicleCommand command);
}
