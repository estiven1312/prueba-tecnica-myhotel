package pe.com.peruapps.vehiclesmaintenanceapi.domain.entity;

import lombok.NonNull;

public record Id(@NonNull Long value) {
  public Id {
    if (value <= 0) {
      throw new IllegalArgumentException("Id must be a positive number");
    }
  }
}
