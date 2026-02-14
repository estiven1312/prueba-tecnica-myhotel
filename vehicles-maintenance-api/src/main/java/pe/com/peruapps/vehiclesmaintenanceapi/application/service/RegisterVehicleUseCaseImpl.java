package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterAutomobileCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterLorryCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.RegisterVehicleUseCase;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.DuplicatedLicensePlateInActiveVehicleException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterVehicleUseCaseImpl implements RegisterVehicleUseCase {

  private final VehicleRepositoryPort vehicleRepositoryPort;

  @Override
  @Transactional
  public Vehicle execute(RegisterVehicleCommand command) {
    Vehicle vehicle = commandToVehicle(command);
    Optional<Vehicle> existingVehicle =
        vehicleRepositoryPort.findByLicensePlate(vehicle.getLicensePlate());
    if (existingVehicle.isPresent()) {
      Vehicle existing = existingVehicle.get();
      throw new DuplicatedLicensePlateInActiveVehicleException(
          existing.getId().value(),
          existing.getLicensePlate(),
          "A vehicle with the same license plate already exists");
    }
    return vehicleRepositoryPort.save(vehicle);
  }

  private Vehicle commandToVehicle(RegisterVehicleCommand command) {
    return switch (command) {
      case RegisterAutomobileCommand cmd -> {
        Automobile automobile = new Automobile();
        mapCommonFields(cmd, automobile);
        automobile.setType(cmd.automobileType());
        automobile.setNumberOfDoors(cmd.numberOfDoors());
        automobile.setPassengerCapacity(cmd.passengerCapacity());
        automobile.setTrunkCapacity(cmd.trunkCapacity());
        yield automobile;
      }
      case RegisterLorryCommand cmd -> {
        Lorry lorry = new Lorry();
        mapCommonFields(cmd, lorry);
        lorry.setType(cmd.lorryType());
        lorry.setAxisNumber(cmd.axisNumber());
        lorry.setTonnageCapacity(cmd.tonnageCapacity());
        yield lorry;
      }
      default -> throw new IllegalArgumentException("Not supported vehicle type");
    };
  }

  private void mapCommonFields(RegisterVehicleCommand command, Vehicle vehicle) {
    vehicle.setBrand(command.brand());
    vehicle.setModel(command.model());
    vehicle.setLicensePlate(command.licensePlate());
    vehicle.setYear(command.year());
    vehicle.setVehicleType(command.vehicleType());
    vehicle.setCubicCapacity(command.cubicCapacity());
    vehicle.setMileage(command.mileage());
    vehicle.setUrlPhoto(command.urlPhoto());
  }
}
