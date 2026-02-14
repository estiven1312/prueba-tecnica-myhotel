package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

@Schema(description = "Request para actualizar el estado de un mantenimiento")
public record UpdateMaintenanceStatusRequest(
    @Schema(
            description = "Nuevo estado del mantenimiento",
            example = "IN_PROGRESS",
            allowableValues = {"SCHEDULED", "IN_PROGRESS", "COMPLETED", "CANCELED"},
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Status is required")
        Maintenance.Status status) {}
