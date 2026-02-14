package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.RegisterMaintenanceUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Id;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

@Service
@RequiredArgsConstructor
public class RegisterMaintenanceUseCaseImpl implements RegisterMaintenanceUseCase {

  private final MaintenanceRepositoryPort maintenanceRepositoryPort;
  private final VehicleRepositoryPort vehicleRepositoryPort;

  @Override
  @Transactional
  public Maintenance execute(RegisterMaintenanceCommand command) {
    vehicleRepositoryPort.findById(command.vehicleId());

    Maintenance maintenance =
        Maintenance.builder()
            .vehicleId(new Id(command.vehicleId()))
            .description(command.description())
            .scheduledDate(command.scheduledDate())
            .kilometersAtMaintenance(command.kilometersAtMaintenance())
            .engineStatus(command.engineStatus())
            .brakesStatus(command.brakesStatus())
            .tiresStatus(command.tiresStatus())
            .transmissionStatus(command.transmissionStatus())
            .electricalStatus(command.electricalStatus())
            .type(command.type())
            .comments(command.comments())
            .status(Maintenance.Status.SCHEDULED)
            .build();

    return maintenanceRepositoryPort.save(maintenance);
  }
}
