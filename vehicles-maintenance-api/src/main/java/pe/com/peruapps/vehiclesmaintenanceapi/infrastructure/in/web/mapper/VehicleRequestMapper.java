package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper;

import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.VehicleRequest;

public interface VehicleRequestMapper<
    R extends VehicleRequest, C extends RegisterVehicleCommand, U extends UpdateVehicleCommand> {

  C toCommand(R request);

  U toUpdateCommand(R request);
}
