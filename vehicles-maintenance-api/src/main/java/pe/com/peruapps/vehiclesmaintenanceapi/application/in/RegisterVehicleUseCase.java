package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;

public interface RegisterVehicleUseCase {
  Vehicle execute(RegisterVehicleCommand command);
}
