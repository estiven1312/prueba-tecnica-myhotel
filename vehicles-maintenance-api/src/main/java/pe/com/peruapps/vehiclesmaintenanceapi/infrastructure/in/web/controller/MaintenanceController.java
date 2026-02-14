package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.MaintenanceQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.*;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.MaintenanceRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.UpdateMaintenanceRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.UpdateMaintenanceStatusRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response.MaintenanceResponse;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper.MaintenanceWebMapper;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

import java.time.LocalDate;

@Tag(name = "Maintenances", description = "API de gestión de mantenimientos de vehículos")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

  private final RegisterMaintenanceUseCase registerMaintenanceUseCase;
  private final FindMaintenanceByIdUseCase findMaintenanceByIdUseCase;
  private final UpdateMaintenanceUseCase updateMaintenanceUseCase;
  private final UpdateMaintenanceStatusUseCase updateMaintenanceStatusUseCase;
  private final DeleteMaintenanceUseCase deleteMaintenanceUseCase;
  private final PageMaintenanceUseCase pageMaintenanceUseCase;
  private final MaintenanceWebMapper maintenanceWebMapper;

  @Operation(
      summary = "Registrar nuevo mantenimiento",
      description =
          "Crea un nuevo mantenimiento para un vehículo. Las validaciones JSR-303 se aplican automáticamente.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Mantenimiento creado exitosamente",
            content = @Content(schema = @Schema(implementation = MaintenanceResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description =
                "Datos inválidos o validación fallida. Retorna un ProblemDetail con detalles de los errores.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
            responseCode = "404",
            description =
                "Vehículo no encontrado. Retorna un ProblemDetail con el ID del vehículo solicitado.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @PostMapping
  public ResponseEntity<MaintenanceResponse> registerMaintenance(
      @Parameter(description = "Datos del mantenimiento a crear", required = true)
          @Valid
          @RequestBody
          MaintenanceRequest request) {
    RegisterMaintenanceCommand command = maintenanceWebMapper.toCommand(request);
    var maintenance = registerMaintenanceUseCase.execute(command);
    MaintenanceResponse response = maintenanceWebMapper.toResponse(maintenance);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(
      summary = "Obtener mantenimiento por ID",
      description = "Busca y retorna un mantenimiento específico por su ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mantenimiento encontrado",
            content = @Content(schema = @Schema(implementation = MaintenanceResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description =
                "Mantenimiento no encontrado. Retorna un ProblemDetail con el ID del mantenimiento solicitado.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @GetMapping("/{id}")
  public ResponseEntity<MaintenanceResponse> getMaintenanceById(
      @Parameter(description = "ID del mantenimiento", required = true, example = "1") @PathVariable
          Long id) {
    Maintenance maintenance = findMaintenanceByIdUseCase.execute(id);
    MaintenanceResponse response = maintenanceWebMapper.toResponse(maintenance);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Actualizar mantenimiento",
      description =
          "Actualiza los datos de un mantenimiento existente. El estado NO se puede modificar mediante este endpoint.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Mantenimiento actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = MaintenanceResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos. Retorna un ProblemDetail con detalles del error.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
            responseCode = "404",
            description =
                "Mantenimiento no encontrado. Retorna un ProblemDetail con el ID del mantenimiento solicitado.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @PutMapping("/{id}")
  public ResponseEntity<MaintenanceResponse> updateMaintenance(
      @Parameter(description = "ID del mantenimiento", required = true, example = "1") @PathVariable
          Long id,
      @Parameter(description = "Nuevos datos del mantenimiento", required = true)
          @Valid
          @RequestBody
          UpdateMaintenanceRequest request) {
    var command = maintenanceWebMapper.toUpdateCommand(request);
    Maintenance maintenance = updateMaintenanceUseCase.execute(id, command);
    MaintenanceResponse response = maintenanceWebMapper.toResponse(maintenance);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Actualizar estado del mantenimiento",
      description =
          "Cambia el estado de un mantenimiento (SCHEDULED, IN_PROGRESS, COMPLETED, CANCELED)")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Estado actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = MaintenanceResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description =
                "Transición de estado inválida. Retorna un ProblemDetail indicando que la transición no está permitida.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
            responseCode = "404",
            description =
                "Mantenimiento no encontrado. Retorna un ProblemDetail con el ID del mantenimiento solicitado.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @PatchMapping("/{id}/status")
  public ResponseEntity<MaintenanceResponse> updateMaintenanceStatus(
      @Parameter(description = "ID del mantenimiento", required = true, example = "1") @PathVariable
          Long id,
      @Parameter(description = "Nuevo estado del mantenimiento", required = true)
          @Valid
          @RequestBody
          UpdateMaintenanceStatusRequest request) {
    updateMaintenanceStatusUseCase.execute(id, request.status());
    Maintenance maintenance = findMaintenanceByIdUseCase.execute(id);
    MaintenanceResponse response = maintenanceWebMapper.toResponse(maintenance);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Eliminar mantenimiento",
      description = "Elimina lógicamente (soft delete) un mantenimiento del sistema")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Mantenimiento eliminado exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description =
                "Mantenimiento no encontrado. Retorna un ProblemDetail con el ID del mantenimiento solicitado.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMaintenance(
      @Parameter(description = "ID del mantenimiento", required = true, example = "1") @PathVariable
          Long id,
      @Parameter(description = "Usuario que elimina el mantenimiento", example = "admin")
          @RequestParam(required = false, defaultValue = "system")
          String deletedBy) {
    deleteMaintenanceUseCase.execute(id, deletedBy);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Listar mantenimientos con filtros",
      description = "Obtiene una lista paginada de mantenimientos con filtros opcionales")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de mantenimientos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = PageResult.class)))
      })
  @GetMapping
  public ResponseEntity<PageResult<MaintenanceResponse>> getAllMaintenances(
      @Parameter(description = "Número de página (0-indexed)", example = "0")
          @RequestParam(defaultValue = "0")
          int page,
      @Parameter(description = "Tamaño de página", example = "10")
          @RequestParam(defaultValue = "10")
          int size,
      @Parameter(description = "Campo por el cual ordenar", example = "scheduledDate")
          @RequestParam(required = false)
          String sortBy,
      @Parameter(
              description = "Dirección de ordenamiento",
              example = "ASC",
              schema = @Schema(allowableValues = {"ASC", "DESC"}))
          @RequestParam(defaultValue = "ASC")
          PaginatedFilter.Order order,
      @Parameter(description = "Filtro por descripción (búsqueda parcial)", example = "aceite")
          @RequestParam(required = false)
          String description,
      @Parameter(description = "Fecha desde (formato: yyyy-MM-dd)", example = "2024-01-01")
          @RequestParam(required = false)
          String fromDate,
      @Parameter(description = "Fecha hasta (formato: yyyy-MM-dd)", example = "2024-12-31")
          @RequestParam(required = false)
          String toDate,
      @Parameter(description = "ID del vehículo", example = "1") @RequestParam(required = false)
          Long vehicleId,
      @Parameter(
              description = "Tipo de mantenimiento",
              example = "OIL_CHANGE",
              schema =
                  @Schema(
                      allowableValues = {
                        "OIL_CHANGE",
                        "TIRE_ROTATION",
                        "BRAKE_INSPECTION",
                        "ENGINE_TUNING",
                        "OTHER"
                      }))
          @RequestParam(required = false)
          String type,
      @Parameter(
              description = "Estado del mantenimiento",
              example = "SCHEDULED",
              schema =
                  @Schema(allowableValues = {"SCHEDULED", "IN_PROGRESS", "COMPLETED", "CANCELED"}))
          @RequestParam(required = false)
          String status) {

    MaintenanceQuery query =
        new MaintenanceQuery(
            description,
            fromDate != null ? LocalDate.parse(fromDate) : null,
            toDate != null ? LocalDate.parse(toDate) : null,
            vehicleId,
            type != null ? Maintenance.Type.valueOf(type) : null,
            status != null ? Maintenance.Status.valueOf(status) : null);

    PaginatedFilter<MaintenanceQuery> filter =
        PaginatedFilter.<MaintenanceQuery>builder()
            .page(page)
            .size(size)
            .sortBy(sortBy != null ? sortBy : "id")
            .order(order)
            .filter(query)
            .build();

    PageResult<Maintenance> maintenances = pageMaintenanceUseCase.execute(filter);
    PageResult<MaintenanceResponse> response = maintenanceWebMapper.toPageResponse(maintenances);
    return ResponseEntity.ok(response);
  }
}
