package pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateMaintenanceCommand(
    String description,
    LocalDate scheduledDate,
    BigDecimal kilometersAtMaintenance,
    Maintenance.RevisionStatus engineStatus,
    Maintenance.RevisionStatus brakesStatus,
    Maintenance.RevisionStatus tiresStatus,
    Maintenance.RevisionStatus transmissionStatus,
    Maintenance.RevisionStatus electricalStatus,
    Maintenance.Type type,
    String comments) {}
