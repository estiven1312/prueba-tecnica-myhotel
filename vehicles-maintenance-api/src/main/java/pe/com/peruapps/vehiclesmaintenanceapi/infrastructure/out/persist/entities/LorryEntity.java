package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Lorry;

@Getter
@Setter
@Entity
@Table(name = "lorries")
public class LorryEntity extends VehicleEntity {

  @Column(name = "lorry_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private Lorry.Type lorryType;

  @Column(name = "axis_number", nullable = false)
  private Long axisNumber;

  @Column(name = "tonnage_capacity", nullable = false)
  private Long tonnageCapacity;
}
