package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(
    description =
        "Request para actualizar un mantenimiento existente. El estado no se puede modificar mediante este endpoint.")
public record UpdateMaintenanceRequest(
    @Schema(
            description = "Descripción detallada del mantenimiento",
            example = "Cambio de aceite y filtro de aire",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Description is required")
        String description,
    @Schema(
            description = "Fecha programada para el mantenimiento",
            example = "2026-03-15",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Scheduled date is required")
        @Future(message = "Scheduled date must be in the future")
        LocalDate scheduledDate,
    @Schema(description = "Kilometraje al momento del mantenimiento", example = "15000.50")
        @Positive(message = "Kilometers must be positive")
        BigDecimal kilometersAtMaintenance,
    @Schema(
            description = "Estado de revisión del motor",
            example = "GOOD",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        Maintenance.RevisionStatus engineStatus,
    @Schema(
            description = "Estado de revisión de los frenos",
            example = "GOOD",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        Maintenance.RevisionStatus brakesStatus,
    @Schema(
            description = "Estado de revisión de las llantas",
            example = "NEEDS_ATTENTION",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        Maintenance.RevisionStatus tiresStatus,
    @Schema(
            description = "Estado de revisión de la transmisión",
            example = "GOOD",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        Maintenance.RevisionStatus transmissionStatus,
    @Schema(
            description = "Estado de revisión del sistema eléctrico",
            example = "GOOD",
            allowableValues = {"GOOD", "NEEDS_ATTENTION", "REPLACE"})
        Maintenance.RevisionStatus electricalStatus,
    @Schema(
            description = "Tipo de mantenimiento",
            example = "OIL_CHANGE",
            allowableValues = {
              "OIL_CHANGE",
              "TIRE_ROTATION",
              "BRAKE_INSPECTION",
              "ENGINE_TUNING",
              "OTHER"
            },
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Maintenance type is required")
        Maintenance.Type type,
    @Schema(
            description = "Comentarios adicionales sobre el mantenimiento",
            example = "Se recomienda revisar el nivel de líquido de frenos")
        String comments) {}
