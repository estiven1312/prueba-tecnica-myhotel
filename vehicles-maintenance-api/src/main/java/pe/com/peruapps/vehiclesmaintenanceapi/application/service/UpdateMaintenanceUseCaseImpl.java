package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.UpdateMaintenanceUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

@Service
@RequiredArgsConstructor
public class UpdateMaintenanceUseCaseImpl implements UpdateMaintenanceUseCase {

  private final MaintenanceRepositoryPort maintenanceRepositoryPort;

  @Override
  @Transactional
  public Maintenance execute(Long id, UpdateMaintenanceCommand command) {
    Maintenance maintenance = maintenanceRepositoryPort.findById(id);

    maintenance.setDescription(command.description());

    maintenance.setScheduledDate(command.scheduledDate());

    maintenance.setKilometersAtMaintenance(command.kilometersAtMaintenance());
    maintenance.setEngineStatus(command.engineStatus());
    maintenance.setBrakesStatus(command.brakesStatus());
    maintenance.setTiresStatus(command.tiresStatus());
    maintenance.setTransmissionStatus(command.transmissionStatus());
    maintenance.setElectricalStatus(command.electricalStatus());
    maintenance.setType(command.type());
    maintenance.setComments(command.comments());

    return maintenanceRepositoryPort.update(id, maintenance);
  }
}
