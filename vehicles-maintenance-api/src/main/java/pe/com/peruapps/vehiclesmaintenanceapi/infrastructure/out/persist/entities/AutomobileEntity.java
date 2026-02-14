package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;

@Getter
@Setter
@Entity
@Table(name = "automobiles")
public class AutomobileEntity extends VehicleEntity {

  @Column(name = "automobile_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private Automobile.Type automobileType;

  @Column(name = "number_of_doors", nullable = false)
  private Long numberOfDoors;

  @Column(name = "passenger_capacity", nullable = false)
  private Long passengerCapacity;

  @Column(name = "trunk_capacity", nullable = false)
  private Long trunkCapacity;
}
