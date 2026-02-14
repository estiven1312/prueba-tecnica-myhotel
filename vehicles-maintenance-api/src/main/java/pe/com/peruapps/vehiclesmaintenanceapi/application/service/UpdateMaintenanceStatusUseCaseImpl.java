package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.UpdateMaintenanceStatusUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

@Service
@RequiredArgsConstructor
public class UpdateMaintenanceStatusUseCaseImpl implements UpdateMaintenanceStatusUseCase {

  private final MaintenanceRepositoryPort maintenanceRepositoryPort;

  @Override
  @Transactional
  public void execute(Long maintenanceId, Maintenance.Status newStatus) {
    Maintenance maintenance = maintenanceRepositoryPort.findById(maintenanceId);

    switch (newStatus) {
      case IN_PROGRESS -> maintenance.startMaintenance();
      case COMPLETED -> maintenance.completeMaintenance();
      case CANCELED -> maintenance.cancelMaintenance();
      case SCHEDULED ->
          throw new IllegalStateException(
              "Cannot manually set status to SCHEDULED. Create a new maintenance instead.");
    }

    maintenanceRepositoryPort.save(maintenance);
  }
}
