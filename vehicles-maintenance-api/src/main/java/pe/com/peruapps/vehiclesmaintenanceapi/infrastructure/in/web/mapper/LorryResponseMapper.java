package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper;

import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response.VehicleResponse;

@Component
public class LorryResponseMapper implements VehicleResponseMapper<Lorry> {

  @Override
  public VehicleResponse.Details createSpecificData(Lorry lorry) {
    return new VehicleResponse.LorryData(
        lorry.getType() != null ? lorry.getType().name() : null,
        lorry.getAxisNumber(),
        lorry.getTonnageCapacity());
  }
}
