package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.mapper;

import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Id;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.MaintenanceEntity;

@Component
public class MaintenanceMapper {

  public MaintenanceEntity toEntity(Maintenance domain) {
    if (domain == null) {
      return null;
    }

    MaintenanceEntity entity = new MaintenanceEntity();

    if (domain.getId() != null) {
      entity.setId(domain.getId().value());
    }

    if (domain.getVehicleId() != null) {
      entity.setVehicleId(domain.getVehicleId().value());
    }
    entity.setDescription(domain.getDescription());
    entity.setScheduledDate(domain.getScheduledDate());
    entity.setStartDateTime(domain.getStartDateTime());
    entity.setEndDateTime(domain.getEndDateTime());
    entity.setKilometersAtMaintenance(domain.getKilometersAtMaintenance());
    entity.setEngineStatus(domain.getEngineStatus());
    entity.setBrakesStatus(domain.getBrakesStatus());
    entity.setTiresStatus(domain.getTiresStatus());
    entity.setTransmissionStatus(domain.getTransmissionStatus());
    entity.setElectricalStatus(domain.getElectricalStatus());
    entity.setType(domain.getType());
    entity.setComments(domain.getComments());
    entity.setStatus(domain.getStatus());

    return entity;
  }

  public Maintenance toDomain(MaintenanceEntity entity) {
    if (entity == null) {
      return null;
    }

    return Maintenance.builder()
        .id(entity.getId() != null ? new Id(entity.getId()) : null)
        .vehicleId(entity.getVehicleId() != null ? new Id(entity.getVehicleId()) : null)
        .description(entity.getDescription())
        .scheduledDate(entity.getScheduledDate())
        .startDateTime(entity.getStartDateTime())
        .endDateTime(entity.getEndDateTime())
        .kilometersAtMaintenance(entity.getKilometersAtMaintenance())
        .engineStatus(entity.getEngineStatus())
        .brakesStatus(entity.getBrakesStatus())
        .tiresStatus(entity.getTiresStatus())
        .transmissionStatus(entity.getTransmissionStatus())
        .electricalStatus(entity.getElectricalStatus())
        .type(entity.getType())
        .comments(entity.getComments())
        .status(entity.getStatus())
        .build();
  }

  public void updateEntity(Maintenance domain, MaintenanceEntity entity) {
    if (domain == null || entity == null) {
      return;
    }
    entity.setDescription(domain.getDescription());
    entity.setScheduledDate(domain.getScheduledDate());
    entity.setStartDateTime(domain.getStartDateTime());
    entity.setEndDateTime(domain.getEndDateTime());
    entity.setKilometersAtMaintenance(domain.getKilometersAtMaintenance());
    entity.setEngineStatus(domain.getEngineStatus());
    entity.setBrakesStatus(domain.getBrakesStatus());
    entity.setTiresStatus(domain.getTiresStatus());
    entity.setTransmissionStatus(domain.getTransmissionStatus());
    entity.setElectricalStatus(domain.getElectricalStatus());
    entity.setType(domain.getType());
    entity.setComments(domain.getComments());
    entity.setStatus(domain.getStatus());
  }
}
