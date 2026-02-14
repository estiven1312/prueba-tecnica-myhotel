package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.mapper;

public interface EntityMapper<D, E> {

  E toEntity(D domain);

  D toDomain(E entity);
}
