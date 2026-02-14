package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper;

import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response.VehicleResponse;

@Component
public class AutomobileResponseMapper implements VehicleResponseMapper<Automobile> {

  @Override
  public VehicleResponse.Details createSpecificData(Automobile automobile) {
    return new VehicleResponse.AutomobileData(
        automobile.getType() != null ? automobile.getType().name() : null,
        automobile.getNumberOfDoors(),
        automobile.getPassengerCapacity(),
        automobile.getTrunkCapacity());
  }
}
