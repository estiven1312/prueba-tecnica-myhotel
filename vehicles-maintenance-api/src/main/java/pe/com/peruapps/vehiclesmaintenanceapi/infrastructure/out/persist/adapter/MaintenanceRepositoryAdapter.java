package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.MaintenanceQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.MaintenanceNotFoundException;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.MaintenanceEntity;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.mapper.MaintenanceMapper;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.repository.MaintenanceJpaRepository;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.mapper.PageRequestMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MaintenanceRepositoryAdapter implements MaintenanceRepositoryPort {

  private final MaintenanceJpaRepository jpaRepository;
  private final MaintenanceMapper mapper;
  private final PageRequestMapper pageRequestMapper;

  @Override
  public Maintenance save(Maintenance maintenance) {
    MaintenanceEntity entity = mapper.toEntity(maintenance);
    MaintenanceEntity saved = jpaRepository.save(entity);
    return mapper.toDomain(saved);
  }

  @Override
  public Maintenance findById(Long id) {
    return jpaRepository
        .findByIdAndDeletedAtIsNull(id)
        .map(mapper::toDomain)
        .orElseThrow(
            () -> new MaintenanceNotFoundException(id, "Maintenance not found with id: " + id));
  }

  @Override
  public Maintenance update(Long id, Maintenance maintenance) {

    MaintenanceEntity existingEntity =
        jpaRepository
            .findByIdAndDeletedAtIsNull(id)
            .orElseThrow(
                () ->
                    new MaintenanceNotFoundException(
                        maintenance.getId().value(),
                        "Maintenance not found with id: " + maintenance.getId().value()));

    mapper.updateEntity(maintenance, existingEntity);
    MaintenanceEntity updated = jpaRepository.save(existingEntity);
    return mapper.toDomain(updated);
  }

  @Override
  public void deleteById(Long id, String deletedBy) {
    var entity =
        jpaRepository
            .findByIdAndDeletedAtIsNull(id)
            .orElseThrow(
                () -> new MaintenanceNotFoundException(id, "Maintenance not found with id: " + id));
    entity.softDelete(deletedBy);
    jpaRepository.save(entity);
  }

  @Override
  public PageResult<Maintenance> filter(PaginatedFilter<MaintenanceQuery> filter) {
    Pageable pageable = pageRequestMapper.toPageRequest(filter);
    MaintenanceQuery query = filter.getFilter();

    Page<MaintenanceEntity> page =
        jpaRepository.findByFilters(
            query.description(),
            query.fromDate(),
            query.toDate(),
            query.vehicleId(),
            query.type(),
            query.status(),
            pageable);

    List<Maintenance> maintenances =
        page.getContent().stream().map(mapper::toDomain).collect(Collectors.toList());

    PageResult<Maintenance> result = new PageResult<>();
    result.setContent(maintenances);
    result.setTotalPages(page.getTotalPages());
    result.setTotalElements(page.getTotalElements());

    return result;
  }
}
