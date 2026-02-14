package pe.com.peruapps.vehiclesmaintenanceapi.application.out;

import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.VehicleQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

import java.util.Optional;

public interface VehicleRepositoryPort {
  Vehicle save(Vehicle vehicle);

  Vehicle findById(Long id);

  Optional<Vehicle> findByLicensePlate(String licensePlate);

  Vehicle update(Vehicle vehicle);

  void deleteById(Long id, String deletedBy);

  PageResult<Vehicle> paginate(PaginatedFilter<VehicleQuery> filter);
}
