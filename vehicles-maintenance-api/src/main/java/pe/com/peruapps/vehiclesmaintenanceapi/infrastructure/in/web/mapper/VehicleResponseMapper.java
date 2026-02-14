package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response.VehicleResponse;

public interface VehicleResponseMapper<T extends Vehicle> {

  VehicleResponse.Details createSpecificData(T vehicle);
}
