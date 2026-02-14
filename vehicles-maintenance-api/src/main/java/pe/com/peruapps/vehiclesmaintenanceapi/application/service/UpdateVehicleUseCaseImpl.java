package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateAutomobileCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateLorryCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.UpdateVehicleUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.DuplicatedLicensePlateInActiveVehicleException;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.ForbiddenChangeVehicleTypeException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateVehicleUseCaseImpl implements UpdateVehicleUseCase {

  private final VehicleRepositoryPort vehicleRepositoryPort;

  @Override
  @Transactional
  public Vehicle execute(Long id, UpdateVehicleCommand command) {
    Vehicle existingVehicle = vehicleRepositoryPort.findById(id);

    if (!command.licensePlate().equals(existingVehicle.getLicensePlate())) {
      validateLicensePlateNotDuplicated(id, command.licensePlate());
    }

    if (existingVehicle.getVehicleType() != command.vehicleType()) {
      throw new ForbiddenChangeVehicleTypeException(
          existingVehicle.getId().value(),
          existingVehicle.getVehicleType(),
          "Cannot change vehicle type");
    }

    updateVehicleFromCommand(existingVehicle, command);

    return vehicleRepositoryPort.update(existingVehicle);
  }

  private void validateLicensePlateNotDuplicated(Long currentVehicleId, String newLicensePlate) {
    Optional<Vehicle> vehicleWithSamePlate =
        vehicleRepositoryPort.findByLicensePlate(newLicensePlate);

    if (vehicleWithSamePlate.isPresent()) {
      Vehicle existingVehicle = vehicleWithSamePlate.get();
      if (!existingVehicle.getId().value().equals(currentVehicleId)) {
        throw new DuplicatedLicensePlateInActiveVehicleException(
            existingVehicle.getId().value(),
            newLicensePlate,
            "Cannot update vehicle: License plate '"
                + newLicensePlate
                + "' is already registered to vehicle ID "
                + existingVehicle.getId().value());
      }
    }
  }

  private void updateVehicleFromCommand(Vehicle vehicle, UpdateVehicleCommand command) {
    vehicle.setBrand(command.brand());
    vehicle.setModel(command.model());
    vehicle.setLicensePlate(command.licensePlate());
    vehicle.setYear(command.year());
    vehicle.setVehicleType(command.vehicleType());
    vehicle.setCubicCapacity(command.cubicCapacity());
    vehicle.setMileage(command.mileage());
    vehicle.setUrlPhoto(command.urlPhoto());

    switch (command) {
      case UpdateAutomobileCommand cmd -> {
        Automobile automobile = (Automobile) vehicle;
        automobile.setType(cmd.automobileType());
        automobile.setNumberOfDoors(cmd.numberOfDoors());
        automobile.setPassengerCapacity(cmd.passengerCapacity());
        automobile.setTrunkCapacity(cmd.trunkCapacity());
      }
      case UpdateLorryCommand cmd -> {
        Lorry lorry = (Lorry) vehicle;
        lorry.setType(cmd.lorryType());
        lorry.setAxisNumber(cmd.axisNumber());
        lorry.setTonnageCapacity(cmd.tonnageCapacity());
      }
      default -> throw new IllegalArgumentException("Unknown vehicle command type");
    }
  }
}
