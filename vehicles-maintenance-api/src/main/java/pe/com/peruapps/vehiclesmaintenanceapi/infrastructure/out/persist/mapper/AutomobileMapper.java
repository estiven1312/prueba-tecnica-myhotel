package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.mapper;

import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.AutomobileEntity;

@Component
public class AutomobileMapper extends VehicleMapper<Automobile, AutomobileEntity> {

  @Override
  public AutomobileEntity toEntity(Automobile domain) {
    if (domain == null) {
      return null;
    }
    AutomobileEntity entity = new AutomobileEntity();
    mapCommonFieldsToEntity(domain, entity);

    entity.setAutomobileType(domain.getType());
    entity.setNumberOfDoors(domain.getNumberOfDoors());
    entity.setPassengerCapacity(domain.getPassengerCapacity());
    entity.setTrunkCapacity(domain.getTrunkCapacity());

    return entity;
  }

  @Override
  public Automobile toDomain(AutomobileEntity entity) {
    if (entity == null) {
      return null;
    }

    Automobile domain = new Automobile();

    mapCommonFieldsToDomain(entity, domain);

    domain.setType(entity.getAutomobileType());
    domain.setNumberOfDoors(entity.getNumberOfDoors());
    domain.setPassengerCapacity(entity.getPassengerCapacity());
    domain.setTrunkCapacity(entity.getTrunkCapacity());

    return domain;
  }
}
