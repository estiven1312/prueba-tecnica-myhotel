package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

public interface FindMaintenanceByIdUseCase {
  Maintenance execute(Long id);
}
