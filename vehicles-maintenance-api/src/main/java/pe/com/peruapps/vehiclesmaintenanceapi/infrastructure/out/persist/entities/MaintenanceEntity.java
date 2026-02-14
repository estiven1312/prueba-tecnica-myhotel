package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "maintenances")
public class MaintenanceEntity extends BaseEntity {

  @Column(name = "vehicle_id", nullable = false)
  private Long vehicleId;

  @Column(name = "description", nullable = false, length = 500)
  private String description;

  @Column(name = "scheduled_date", nullable = false)
  private LocalDate scheduledDate;

  @Column(name = "start_date_time")
  private LocalDateTime startDateTime;

  @Column(name = "end_date_time")
  private LocalDateTime endDateTime;

  @Column(name = "kilometers_at_maintenance", precision = 10, scale = 2)
  private BigDecimal kilometersAtMaintenance;

  @Column(name = "engine_status", length = 50)
  @Enumerated(EnumType.STRING)
  private Maintenance.RevisionStatus engineStatus;

  @Column(name = "brakes_status", length = 50)
  @Enumerated(EnumType.STRING)
  private Maintenance.RevisionStatus brakesStatus;

  @Column(name = "tires_status", length = 50)
  @Enumerated(EnumType.STRING)
  private Maintenance.RevisionStatus tiresStatus;

  @Column(name = "transmission_status", length = 50)
  @Enumerated(EnumType.STRING)
  private Maintenance.RevisionStatus transmissionStatus;

  @Column(name = "electrical_status", length = 50)
  @Enumerated(EnumType.STRING)
  private Maintenance.RevisionStatus electricalStatus;

  @Column(name = "type", nullable = false, length = 50)
  @Enumerated(EnumType.STRING)
  private Maintenance.Type type;

  @Column(name = "comments", length = 1000)
  private String comments;

  @Column(name = "status", nullable = false, length = 50)
  @Enumerated(EnumType.STRING)
  private Maintenance.Status status;
}
