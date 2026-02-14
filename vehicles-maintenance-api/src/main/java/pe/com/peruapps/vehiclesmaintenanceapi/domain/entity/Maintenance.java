package pe.com.peruapps.vehiclesmaintenanceapi.domain.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.IllegalChangeStatusForMaintenanceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Maintenance {
  @EqualsAndHashCode.Include private Id id;
  private Id vehicleId;
  private String description;
  private LocalDate scheduledDate;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private BigDecimal kilometersAtMaintenance;
  private RevisionStatus engineStatus;
  private RevisionStatus brakesStatus;
  private RevisionStatus tiresStatus;
  private RevisionStatus transmissionStatus;
  private RevisionStatus electricalStatus;
  private Type type;
  private String comments;
  private Status status;

  @Builder
  private Maintenance(
      Id id,
      Id vehicleId,
      String description,
      LocalDate scheduledDate,
      LocalDateTime startDateTime,
      LocalDateTime endDateTime,
      BigDecimal kilometersAtMaintenance,
      RevisionStatus engineStatus,
      RevisionStatus brakesStatus,
      RevisionStatus tiresStatus,
      RevisionStatus transmissionStatus,
      RevisionStatus electricalStatus,
      Type type,
      String comments,
      Status status) {
    this.id = id;
    this.vehicleId = Objects.requireNonNull(vehicleId, "Vehicle ID cannot be null");
    this.description = description;
    setScheduledDate(Objects.requireNonNull(scheduledDate, "Scheduled date cannot be null"));
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.kilometersAtMaintenance = kilometersAtMaintenance;
    this.engineStatus = engineStatus;
    this.brakesStatus = brakesStatus;
    this.tiresStatus = tiresStatus;
    this.transmissionStatus = transmissionStatus;
    this.electricalStatus = electricalStatus;
    this.type = type;
    this.comments = comments;
    this.status = status;
  }

  public void setScheduledDate(LocalDate scheduledDate) {
    if (scheduledDate.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Scheduled date cannot be in the past");
    }
    this.scheduledDate = scheduledDate;
  }

  public void changeScheduledDate(LocalDate newDate) {
    if (status != Status.SCHEDULED) {
      throw new IllegalChangeStatusForMaintenanceException(
          id.value(), status, "Scheduled date can only be changed if maintenance is scheduled");
    }
    this.setScheduledDate(newDate);
  }

  public void startMaintenance() {
    if (status != Status.SCHEDULED) {
      throw new IllegalChangeStatusForMaintenanceException(
          id.value(), status, "Maintenance can only be started if it is scheduled");
    }
    setStartDateTime(LocalDateTime.now());
    this.setStatus(Status.IN_PROGRESS);
  }

  public void completeMaintenance() {
    if (status != Status.IN_PROGRESS) {
      throw new IllegalChangeStatusForMaintenanceException(
          id.value(), status, "Maintenance can only be completed if it is in progress");
    }
    setEndDateTime(LocalDateTime.now());
    this.setStatus(Status.COMPLETED);
  }

  public void cancelMaintenance() {
    if (status == Status.COMPLETED) {
      throw new IllegalChangeStatusForMaintenanceException(
          id.value(), status, "Completed maintenance cannot be canceled");
    }
    setEndDateTime(LocalDateTime.now());
    this.setStatus(Status.CANCELED);
  }

  public enum Type {
    OIL_CHANGE,
    TIRE_ROTATION,
    BRAKE_INSPECTION,
    ENGINE_TUNING,
    OTHER
  }

  public enum Status {
    SCHEDULED,
    IN_PROGRESS,
    COMPLETED,
    CANCELED
  }

  public enum RevisionStatus {
    GOOD,
    NEEDS_ATTENTION,
    REPLACE
  }
}
