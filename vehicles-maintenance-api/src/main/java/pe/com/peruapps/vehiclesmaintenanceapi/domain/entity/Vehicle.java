package pe.com.peruapps.vehiclesmaintenanceapi.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Vehicle {
  @EqualsAndHashCode.Include private Id id;
  private String brand;
  private String model;
  private String licensePlate;
  private Long year;
  private VehicleType vehicleType;
  private BigDecimal cubicCapacity;
  private BigDecimal mileage;
  private String urlPhoto;
}
