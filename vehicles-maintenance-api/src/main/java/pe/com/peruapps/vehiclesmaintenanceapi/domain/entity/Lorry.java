package pe.com.peruapps.vehiclesmaintenanceapi.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lorry extends Vehicle {
  private Type type;
  private Long axisNumber;
  private Long tonnageCapacity;

  public enum Type {
    TYPE_THREE_QUARTER,
    TRAILER,
    SEMI_TRAILER,
    TANKER,
    DUMP,
    REFRIGERATED
  }
}
