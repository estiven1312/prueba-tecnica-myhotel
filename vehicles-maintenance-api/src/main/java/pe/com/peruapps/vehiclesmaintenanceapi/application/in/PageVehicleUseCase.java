package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.VehicleQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

public interface PageVehicleUseCase {
  PageResult<Vehicle> execute(PaginatedFilter<VehicleQuery> filter);
}
