package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.DeleteMaintenanceUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;

@Service
@RequiredArgsConstructor
public class DeleteMaintenanceUseCaseImpl implements DeleteMaintenanceUseCase {

  private final MaintenanceRepositoryPort maintenanceRepositoryPort;

  @Override
  public void execute(Long id, String deletedBy) {
    maintenanceRepositoryPort.deleteById(id, deletedBy);
  }
}
