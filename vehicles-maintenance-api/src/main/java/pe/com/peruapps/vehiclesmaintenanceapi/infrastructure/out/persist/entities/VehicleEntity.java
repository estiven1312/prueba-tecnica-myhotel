package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class VehicleEntity extends BaseEntity {
  @Column(name = "brand", nullable = false)
  private String brand;

  @Column(name = "model", nullable = false)
  private String model;

  @Column(name = "license_plate", nullable = false, unique = true)
  private String licensePlate;

  @Column(name = "year", nullable = false)
  private Long year;

  @Column(name = "vehicle_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private VehicleType vehicleType;

  @Column(name = "cubic_capacity", nullable = false)
  private BigDecimal cubicCapacity;

  @Column(name = "mileage", nullable = false)
  private BigDecimal mileage;

  @Column(name = "url_photo")
  private String urlPhoto;
}
