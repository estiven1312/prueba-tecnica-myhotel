package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper;

import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.MaintenanceRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.UpdateMaintenanceRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response.MaintenanceResponse;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;

import java.util.stream.Collectors;

@Component
public class MaintenanceWebMapper {

  public RegisterMaintenanceCommand toCommand(MaintenanceRequest request) {
    return new RegisterMaintenanceCommand(
        request.vehicleId(),
        request.description(),
        request.scheduledDate(),
        request.kilometersAtMaintenance(),
        request.engineStatus(),
        request.brakesStatus(),
        request.tiresStatus(),
        request.transmissionStatus(),
        request.electricalStatus(),
        request.type(),
        request.comments());
  }

  public UpdateMaintenanceCommand toUpdateCommand(UpdateMaintenanceRequest request) {
    return new UpdateMaintenanceCommand(
        request.description(),
        request.scheduledDate(),
        request.kilometersAtMaintenance(),
        request.engineStatus(),
        request.brakesStatus(),
        request.tiresStatus(),
        request.transmissionStatus(),
        request.electricalStatus(),
        request.type(),
        request.comments());
  }

  public MaintenanceResponse toResponse(Maintenance maintenance) {
    return new MaintenanceResponse(
        maintenance.getId() != null ? maintenance.getId().value() : null,
        maintenance.getVehicleId() != null ? maintenance.getVehicleId().value() : null,
        maintenance.getDescription(),
        maintenance.getScheduledDate(),
        maintenance.getStartDateTime(),
        maintenance.getEndDateTime(),
        maintenance.getKilometersAtMaintenance(),
        maintenance.getEngineStatus() != null ? maintenance.getEngineStatus().name() : null,
        maintenance.getBrakesStatus() != null ? maintenance.getBrakesStatus().name() : null,
        maintenance.getTiresStatus() != null ? maintenance.getTiresStatus().name() : null,
        maintenance.getTransmissionStatus() != null
            ? maintenance.getTransmissionStatus().name()
            : null,
        maintenance.getElectricalStatus() != null ? maintenance.getElectricalStatus().name() : null,
        maintenance.getType() != null ? maintenance.getType().name() : null,
        maintenance.getComments(),
        maintenance.getStatus() != null ? maintenance.getStatus().name() : null);
  }

  public PageResult<MaintenanceResponse> toPageResponse(PageResult<Maintenance> pageResult) {
    return PageResult.<MaintenanceResponse>builder()
        .content(
            pageResult.getContent().stream().map(this::toResponse).collect(Collectors.toList()))
        .totalElements(pageResult.getTotalElements())
        .totalPages(pageResult.getTotalPages())
        .build();
  }
}
