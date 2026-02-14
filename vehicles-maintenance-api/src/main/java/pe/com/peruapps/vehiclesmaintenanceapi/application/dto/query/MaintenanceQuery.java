package pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query;

import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

import java.time.LocalDate;

public record MaintenanceQuery(
    String description,
    LocalDate fromDate,
    LocalDate toDate,
    Long vehicleId,
    Maintenance.Type type,
    Maintenance.Status status) {}
