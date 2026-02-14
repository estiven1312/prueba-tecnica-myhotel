package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.AutomobileRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.LorryRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.VehicleRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response.VehicleResponse;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VehicleWebMapper {

  private final AutomobileRequestMapper automobileRequestMapper;
  private final LorryRequestMapper lorryRequestMapper;
  private final AutomobileResponseMapper automobileResponseMapper;
  private final LorryResponseMapper lorryResponseMapper;

  public RegisterVehicleCommand toCommand(VehicleRequest request) {
    return switch (request) {
      case AutomobileRequest r -> automobileRequestMapper.toCommand(r);
      case LorryRequest r -> lorryRequestMapper.toCommand(r);
    };
  }

  public UpdateVehicleCommand toUpdateCommand(VehicleRequest request) {
    return switch (request) {
      case AutomobileRequest r -> automobileRequestMapper.toUpdateCommand(r);
      case LorryRequest r -> lorryRequestMapper.toUpdateCommand(r);
    };
  }

  public VehicleResponse toResponse(Vehicle vehicle) {
    VehicleResponse.Details specificData =
        switch (vehicle) {
          case Automobile a -> automobileResponseMapper.createSpecificData(a);
          case Lorry l -> lorryResponseMapper.createSpecificData(l);
          default ->
              throw new IllegalArgumentException(
                  "Unknown vehicle type: " + vehicle.getClass().getName());
        };

    return new VehicleResponse(
        vehicle.getId() != null ? vehicle.getId().value() : null,
        vehicle.getBrand(),
        vehicle.getModel(),
        vehicle.getLicensePlate(),
        vehicle.getYear(),
        vehicle.getVehicleType(),
        vehicle.getCubicCapacity(),
        vehicle.getMileage(),
        vehicle.getUrlPhoto(),
        specificData);
  }

  public PageResult<VehicleResponse> toPageResponse(PageResult<Vehicle> pageResult) {
    return PageResult.<VehicleResponse>builder()
        .content(
            pageResult.getContent().stream().map(this::toResponse).collect(Collectors.toList()))
        .totalPages(pageResult.getTotalPages())
        .totalElements(pageResult.getTotalElements())
        .build();
  }
}
