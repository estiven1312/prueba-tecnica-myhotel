package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.FindVehicleByIdUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;

@Service
@RequiredArgsConstructor
public class FindVehicleByIdUseCaseImpl implements FindVehicleByIdUseCase {

  private final VehicleRepositoryPort vehicleRepositoryPort;

  @Override
  @Transactional(readOnly = true)
  public Vehicle execute(Long id) {
    return vehicleRepositoryPort.findById(id);
  }
}
