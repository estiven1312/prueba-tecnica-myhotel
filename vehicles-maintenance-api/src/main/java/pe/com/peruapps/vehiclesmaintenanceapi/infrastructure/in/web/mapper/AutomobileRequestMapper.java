package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper;

import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterAutomobileCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateAutomobileCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.AutomobileRequest;

@Component
public class AutomobileRequestMapper
    implements VehicleRequestMapper<
        AutomobileRequest, RegisterAutomobileCommand, UpdateAutomobileCommand> {

  @Override
  public RegisterAutomobileCommand toCommand(AutomobileRequest request) {
    return new RegisterAutomobileCommand(
        request.brand(),
        request.model(),
        request.licensePlate(),
        request.year(),
        request.vehicleType(),
        request.cubicCapacity(),
        request.mileage(),
        request.urlPhoto(),
        request.automobileType(),
        request.numberOfDoors(),
        request.passengerCapacity(),
        request.trunkCapacity());
  }

  @Override
  public UpdateAutomobileCommand toUpdateCommand(AutomobileRequest request) {
    return new UpdateAutomobileCommand(
        request.brand(),
        request.model(),
        request.licensePlate(),
        request.year(),
        request.vehicleType(),
        request.cubicCapacity(),
        request.mileage(),
        request.urlPhoto(),
        request.automobileType(),
        request.numberOfDoors(),
        request.passengerCapacity(),
        request.trunkCapacity());
  }
}
