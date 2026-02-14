package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;

public interface FindVehicleByIdUseCase {
  Vehicle execute(Long id);
}
