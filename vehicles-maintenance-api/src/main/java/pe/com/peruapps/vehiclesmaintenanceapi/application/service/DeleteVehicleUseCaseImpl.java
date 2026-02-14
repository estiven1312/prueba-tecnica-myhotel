package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.DeleteVehicleUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;

@Service
@RequiredArgsConstructor
public class DeleteVehicleUseCaseImpl implements DeleteVehicleUseCase {

  private final VehicleRepositoryPort vehicleRepositoryPort;

  @Override
  @Transactional
  public void execute(Long id, String deletedBy) {
    vehicleRepositoryPort.deleteById(id, deletedBy);
  }
}
