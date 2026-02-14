package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.VehicleQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.VehicleNotFoundException;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.VehicleEntity;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.mapper.VehicleMapperFactory;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.repository.VehicleRepository;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.mapper.PageRequestMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VehicleRepositoryAdapter implements VehicleRepositoryPort {

  private final VehicleRepository jpaRepository;
  private final VehicleMapperFactory mapperFactory;
  private final PageRequestMapper pageRequestMapper;

  @Override
  public Vehicle save(Vehicle vehicle) {
    VehicleEntity entity = mapperFactory.toEntity(vehicle);
    VehicleEntity saved = jpaRepository.save(entity);
    return mapperFactory.toDomain(saved);
  }

  @Override
  public Vehicle findById(Long id) {
    return jpaRepository
        .findByIdAndDeletedAtIsNull(id)
        .map(mapperFactory::toDomain)
        .orElseThrow(() -> new VehicleNotFoundException(id, "Vehicle not found with id: " + id));
  }

  @Override
  public Vehicle update(Vehicle vehicle) {
    if (vehicle.getId() == null) {
      throw new IllegalArgumentException("Cannot update vehicle without ID");
    }

    VehicleEntity existingEntity =
        jpaRepository
            .findByIdAndDeletedAtIsNull(vehicle.getId().value())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Vehicle not found with id: " + vehicle.getId().value()));

    VehicleEntity updatedEntity = mapperFactory.toEntity(vehicle);

    if (vehicle.getId() != null) {
      updatedEntity.setId(vehicle.getId().value());
    }

    updatedEntity.setCreatedAt(existingEntity.getCreatedAt());
    updatedEntity.setCreatedBy(existingEntity.getCreatedBy());

    VehicleEntity saved = jpaRepository.save(updatedEntity);
    return mapperFactory.toDomain(saved);
  }

  @Override
  public void deleteById(Long id, String deletedBy) {
    var entity =
        jpaRepository
            .findById(id)
            .orElseThrow(
                () -> new VehicleNotFoundException(id, "Vehicle not found with id: " + id));
    entity.softDelete(deletedBy);
    jpaRepository.save(entity);
  }

  @Override
  public PageResult<Vehicle> paginate(PaginatedFilter<VehicleQuery> filter) {
    Pageable pageable = pageRequestMapper.toPageRequest(filter);
    VehicleQuery query = filter.getFilter();

    Page<VehicleEntity> page =
        jpaRepository.findByFilters(
            query.brand(),
            query.model(),
            query.licensePlate(),
            query.vehicleType(),
            query.fromYear(),
            query.toYear(),
            pageable);

    List<Vehicle> vehicles =
        page.getContent().stream().map(mapperFactory::toDomain).collect(Collectors.toList());

    PageResult<Vehicle> result = new PageResult<>();
    result.setContent(vehicles);
    result.setTotalPages(page.getTotalPages());
    result.setTotalElements(page.getTotalElements());

    return result;
  }

  @Override
  public Optional<Vehicle> findByLicensePlate(String licensePlate) {
    return jpaRepository.findByLicensePlate(licensePlate).map(mapperFactory::toDomain);
  }
}
