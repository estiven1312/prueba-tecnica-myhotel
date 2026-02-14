package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Respuesta con información de un mantenimiento")
public record MaintenanceResponse(
    @Schema(description = "ID del mantenimiento", example = "1") Long id,
    @Schema(description = "ID del vehículo asociado", example = "1") Long vehicleId,
    @Schema(description = "Descripción del mantenimiento", example = "Cambio de aceite y filtro")
        String description,
    @Schema(description = "Fecha programada", example = "2026-03-15") LocalDate scheduledDate,
    @Schema(
            description = "Fecha y hora de inicio del mantenimiento",
            example = "2026-03-15T08:30:00")
        LocalDateTime startDateTime,
    @Schema(
            description = "Fecha y hora de finalización del mantenimiento",
            example = "2026-03-15T11:30:00")
        LocalDateTime endDateTime,
    @Schema(description = "Kilometraje al momento del mantenimiento", example = "15000.50")
        BigDecimal kilometersAtMaintenance,
    @Schema(
            description = "Estado de revisión del motor",
            example = "GOOD",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        String engineStatus,
    @Schema(
            description = "Estado de revisión de los frenos",
            example = "GOOD",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        String brakesStatus,
    @Schema(
            description = "Estado de revisión de las llantas",
            example = "NEEDS_ATTENTION",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        String tiresStatus,
    @Schema(
            description = "Estado de revisión de la transmisión",
            example = "GOOD",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        String transmissionStatus,
    @Schema(
            description = "Estado de revisión del sistema eléctrico",
            example = "GOOD",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        String electricalStatus,
    @Schema(
            description = "Tipo de mantenimiento",
            example = "OIL_CHANGE",
            allowableValues = {
              "OIL_CHANGE",
              "TIRE_ROTATION",
              "BRAKE_INSPECTION",
              "ENGINE_TUNING",
              "OTHER"
            })
        String type,
    @Schema(
            description = "Comentarios adicionales",
            example = "Se recomienda revisar el nivel de líquido de frenos")
        String comments,
    @Schema(
            description = "Estado del mantenimiento",
            example = "SCHEDULED",
            allowableValues = {"SCHEDULED", "IN_PROGRESS", "COMPLETED", "CANCELED"})
        String status) {}
