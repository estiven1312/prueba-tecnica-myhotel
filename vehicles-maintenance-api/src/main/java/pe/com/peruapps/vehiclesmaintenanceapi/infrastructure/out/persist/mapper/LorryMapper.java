package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.mapper;

import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.LorryEntity;

@Component
public class LorryMapper extends VehicleMapper<Lorry, LorryEntity> {

  @Override
  public LorryEntity toEntity(Lorry domain) {
    if (domain == null) {
      return null;
    }

    LorryEntity entity = new LorryEntity();

    mapCommonFieldsToEntity(domain, entity);

    entity.setLorryType(domain.getType());
    entity.setAxisNumber(domain.getAxisNumber());
    entity.setTonnageCapacity(domain.getTonnageCapacity());

    return entity;
  }

  @Override
  public Lorry toDomain(LorryEntity entity) {
    if (entity == null) {
      return null;
    }
    Lorry domain = new Lorry();
    mapCommonFieldsToDomain(entity, domain);
    domain.setType(entity.getLorryType());
    domain.setAxisNumber(entity.getAxisNumber());
    domain.setTonnageCapacity(entity.getTonnageCapacity());

    return domain;
  }
}
