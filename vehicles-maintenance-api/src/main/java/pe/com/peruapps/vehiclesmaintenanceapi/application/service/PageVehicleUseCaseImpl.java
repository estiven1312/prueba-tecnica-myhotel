package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.VehicleQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.PageVehicleUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

@Service
@RequiredArgsConstructor
public class PageVehicleUseCaseImpl implements PageVehicleUseCase {

  private final VehicleRepositoryPort vehicleRepositoryPort;

  @Override
  @Transactional(readOnly = true)
  public PageResult<Vehicle> execute(PaginatedFilter<VehicleQuery> filter) {

    return vehicleRepositoryPort.paginate(filter);
  }
}
