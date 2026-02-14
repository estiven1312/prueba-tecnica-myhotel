package pe.com.peruapps.vehiclesmaintenanceapi.application.out;

import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.MaintenanceQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

public interface MaintenanceRepositoryPort {
  Maintenance save(Maintenance maintenance);

  Maintenance findById(Long id);

  Maintenance update(Long id, Maintenance maintenance);

  void deleteById(Long id, String deletedBy);

  PageResult<Maintenance> filter(PaginatedFilter<MaintenanceQuery> filter);
}
