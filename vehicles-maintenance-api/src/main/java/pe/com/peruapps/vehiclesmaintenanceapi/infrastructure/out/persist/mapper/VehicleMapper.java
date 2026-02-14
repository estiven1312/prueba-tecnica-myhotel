package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.mapper;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Id;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.VehicleEntity;

public abstract class VehicleMapper<D extends Vehicle, E extends VehicleEntity>
    implements EntityMapper<D, E> {

  protected void mapCommonFieldsToDomain(E entity, D domain) {
    if (entity.getId() != null) {
      domain.setId(new Id(entity.getId()));
    }
    domain.setBrand(entity.getBrand());
    domain.setModel(entity.getModel());
    domain.setLicensePlate(entity.getLicensePlate());
    domain.setYear(entity.getYear());
    domain.setVehicleType(entity.getVehicleType());
    domain.setCubicCapacity(entity.getCubicCapacity());
    domain.setMileage(entity.getMileage());
    domain.setUrlPhoto(entity.getUrlPhoto());
  }

  protected void mapCommonFieldsToEntity(D domain, E entity) {
    if (domain.getId() != null) {
      entity.setId(domain.getId().value());
    }
    entity.setBrand(domain.getBrand());
    entity.setModel(domain.getModel());
    entity.setLicensePlate(domain.getLicensePlate());
    entity.setYear(domain.getYear());
    entity.setVehicleType(domain.getVehicleType());
    entity.setCubicCapacity(domain.getCubicCapacity());
    entity.setMileage(domain.getMileage());
    entity.setUrlPhoto(domain.getUrlPhoto());
  }
}
