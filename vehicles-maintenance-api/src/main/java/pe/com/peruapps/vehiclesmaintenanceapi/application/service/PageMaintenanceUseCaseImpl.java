package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.MaintenanceQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.PageMaintenanceUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

@Service
@RequiredArgsConstructor
public class PageMaintenanceUseCaseImpl implements PageMaintenanceUseCase {

  private final MaintenanceRepositoryPort maintenanceRepositoryPort;

  @Override
  @Transactional(readOnly = true)
  public PageResult<Maintenance> execute(PaginatedFilter<MaintenanceQuery> filter) {

    return maintenanceRepositoryPort.filter(filter);
  }
}
