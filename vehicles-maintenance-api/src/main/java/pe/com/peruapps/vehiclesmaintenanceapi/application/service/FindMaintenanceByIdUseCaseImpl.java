package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.FindMaintenanceByIdUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

@Service
@RequiredArgsConstructor
public class FindMaintenanceByIdUseCaseImpl implements FindMaintenanceByIdUseCase {

  private final MaintenanceRepositoryPort maintenanceRepositoryPort;

  @Override
  @Transactional(readOnly = true)
  public Maintenance execute(Long id) {
    return maintenanceRepositoryPort.findById(id);
  }
}
