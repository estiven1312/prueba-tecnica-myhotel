package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.AutomobileEntity;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.LorryEntity;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.VehicleEntity;

@Component
@RequiredArgsConstructor
public class VehicleMapperFactory {
  private final AutomobileMapper automobileMapper;
  private final LorryMapper lorryMapper;

  public VehicleEntity toEntity(Vehicle domain) {
    return switch (domain.getVehicleType()) {
      case AUTOMOBILE -> automobileMapper.toEntity((Automobile) domain);
      case LORRY -> lorryMapper.toEntity((Lorry) domain);
    };
  }

  public Vehicle toDomain(VehicleEntity entity) {
    return switch (entity.getVehicleType()) {
      case AUTOMOBILE -> automobileMapper.toDomain((AutomobileEntity) entity);
      case LORRY -> lorryMapper.toDomain((LorryEntity) entity);
    };
  }
}
