package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper;

import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterLorryCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateLorryCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.LorryRequest;

@Component
public class LorryRequestMapper
    implements VehicleRequestMapper<LorryRequest, RegisterLorryCommand, UpdateLorryCommand> {

  @Override
  public RegisterLorryCommand toCommand(LorryRequest request) {
    return new RegisterLorryCommand(
        request.brand(),
        request.model(),
        request.licensePlate(),
        request.year(),
        request.vehicleType(),
        request.cubicCapacity(),
        request.mileage(),
        request.urlPhoto(),
        request.lorryType(),
        request.axisNumber(),
        request.tonnageCapacity());
  }

  @Override
  public UpdateLorryCommand toUpdateCommand(LorryRequest request) {
    return new UpdateLorryCommand(
        request.brand(),
        request.model(),
        request.licensePlate(),
        request.year(),
        request.vehicleType(),
        request.cubicCapacity(),
        request.mileage(),
        request.urlPhoto(),
        request.lorryType(),
        request.axisNumber(),
        request.tonnageCapacity());
  }
}
