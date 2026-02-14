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
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateVehicleCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.VehicleQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.application.in.*;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.request.VehicleRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.dto.response.VehicleResponse;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.mapper.VehicleWebMapper;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

@Tag(name = "Vehicles", description = "API de gestión de vehículos (automóviles y camiones)")
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

  private final RegisterVehicleUseCase registerVehicleUseCase;
  private final UpdateVehicleUseCase updateVehicleUseCase;
  private final PageVehicleUseCase pageVehicleUseCase;
  private final FindVehicleByIdUseCase findVehicleByIdUseCase;
  private final DeleteVehicleUseCase deleteVehicleUseCase;
  private final VehicleWebMapper vehicleWebMapper;

  @Operation(
      summary = "Registrar nuevo vehículo",
      description =
          "Crea un nuevo vehículo (automóvil o camión) en el sistema. Las validaciones JSR-303 se aplican automáticamente.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Vehículo creado exitosamente",
            content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description =
                "Datos inválidos o validación fallida. Retorna un ProblemDetail con detalles de los errores de validación.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
            responseCode = "409",
            description =
                "Placa de licencia duplicada. Retorna un ProblemDetail indicando que ya existe un vehículo activo con esa placa.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @PostMapping
  public ResponseEntity<VehicleResponse> registerVehicle(
      @Parameter(description = "Datos del vehículo a crear", required = true) @Valid @RequestBody
          VehicleRequest request) {
    RegisterVehicleCommand command = vehicleWebMapper.toCommand(request);
    Vehicle vehicle = registerVehicleUseCase.execute(command);
    VehicleResponse response = vehicleWebMapper.toResponse(vehicle);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(
      summary = "Obtener vehículo por ID",
      description = "Busca y retorna un vehículo específico por su ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehículo encontrado",
            content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description =
                "Vehículo no encontrado. Retorna un ProblemDetail con el ID del vehículo solicitado.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @GetMapping("/{id}")
  public ResponseEntity<VehicleResponse> findById(
      @Parameter(description = "ID del vehículo", required = true, example = "1") @PathVariable
          Long id) {
    Vehicle vehicle = findVehicleByIdUseCase.execute(id);
    VehicleResponse response = vehicleWebMapper.toResponse(vehicle);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Actualizar vehículo",
      description = "Actualiza los datos de un vehículo existente")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehículo actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
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
                "Vehículo no encontrado. Retorna un ProblemDetail con el ID del vehículo solicitado.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(
            responseCode = "409",
            description =
                "Conflicto: placa duplicada o intento de cambiar tipo de vehículo. Retorna un ProblemDetail con detalles específicos.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @PutMapping("/{id}")
  public ResponseEntity<VehicleResponse> updateVehicle(
      @Parameter(description = "ID del vehículo", required = true, example = "1") @PathVariable
          Long id,
      @Parameter(description = "Nuevos datos del vehículo", required = true) @Valid @RequestBody
          VehicleRequest request) {
    UpdateVehicleCommand command = vehicleWebMapper.toUpdateCommand(request);
    Vehicle vehicle = updateVehicleUseCase.execute(id, command);
    VehicleResponse response = vehicleWebMapper.toResponse(vehicle);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Eliminar vehículo",
      description = "Elimina lógicamente (soft delete) un vehículo del sistema")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description =
                "Vehículo no encontrado. Retorna un ProblemDetail con el ID del vehículo solicitado.",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVehicle(
      @Parameter(description = "ID del vehículo", required = true, example = "1") @PathVariable
          Long id,
      @Parameter(description = "Usuario que elimina el vehículo", example = "admin")
          @RequestHeader(value = "X-User", defaultValue = "system")
          String deletedBy) {
    deleteVehicleUseCase.execute(id, deletedBy);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Listar vehículos con filtros",
      description = "Obtiene una lista paginada de vehículos con filtros opcionales")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de vehículos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = PageResult.class)))
      })
  @GetMapping
  public ResponseEntity<PageResult<VehicleResponse>> getAllVehicles(
      @Parameter(description = "Número de página (0-indexed)", example = "0")
          @RequestParam(defaultValue = "0")
          int page,
      @Parameter(description = "Tamaño de página", example = "10")
          @RequestParam(defaultValue = "10")
          int size,
      @Parameter(description = "Campo por el cual ordenar", example = "brand")
          @RequestParam(required = false)
          String sortBy,
      @Parameter(
              description = "Dirección de ordenamiento",
              example = "ASC",
              schema = @Schema(allowableValues = {"ASC", "DESC"}))
          @RequestParam(defaultValue = "ASC")
          PaginatedFilter.Order order,
      @Parameter(description = "Filtro por marca (búsqueda parcial)", example = "Toyota")
          @RequestParam(required = false)
          String brand,
      @Parameter(description = "Filtro por modelo (búsqueda parcial)", example = "Corolla")
          @RequestParam(required = false)
          String model,
      @Parameter(
              description = "Filtro por placa de licencia (búsqueda exacta)",
              example = "ABC-123")
          @RequestParam(required = false)
          String licensePlate,
      @Parameter(
              description = "Filtro por tipo de vehículo",
              example = "AUTOMOBILE",
              schema = @Schema(allowableValues = {"AUTOMOBILE", "LORRY"}))
          @RequestParam(required = false)
          String vehicleType,
      @Parameter(description = "Año desde (inclusive)", example = "2020")
          @RequestParam(required = false)
          Long fromYear,
      @Parameter(description = "Año hasta (inclusive)", example = "2024")
          @RequestParam(required = false)
          Long toYear) {

    VehicleQuery query =
        new VehicleQuery(
            brand,
            model,
            licensePlate,
            vehicleType != null ? VehicleType.valueOf(vehicleType) : null,
            fromYear,
            toYear);

    PaginatedFilter<VehicleQuery> filter =
        PaginatedFilter.<VehicleQuery>builder()
            .page(page)
            .size(size)
            .sortBy(sortBy != null ? sortBy : "id")
            .order(order)
            .filter(query)
            .build();

    PageResult<Vehicle> vehicles = pageVehicleUseCase.execute(filter);
    PageResult<VehicleResponse> response = vehicleWebMapper.toPageResponse(vehicles);
    return ResponseEntity.ok(response);
  }
}
