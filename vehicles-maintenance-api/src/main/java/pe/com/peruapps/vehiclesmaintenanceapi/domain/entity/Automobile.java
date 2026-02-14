package pe.com.peruapps.vehiclesmaintenanceapi.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Automobile extends Vehicle {
  private Type type;
  private Long numberOfDoors;
  private Long passengerCapacity;
  private Long trunkCapacity;

  public enum Type {
    SEDAN,
    HATCHBACK,
    SUV,
    COUPE,
    CONVERTIBLE
  }
}
