package pe.com.peruapps.reportshrproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pe.com.peruapps.reportshrproject.dto.*;
import pe.com.peruapps.reportshrproject.service.ReportExportStrategy;
import pe.com.peruapps.reportshrproject.service.ReportsService;
import pe.com.peruapps.reportshrproject.service.ReportExportFacade;

import java.util.List;
import java.util.Optional;

@Tag(name = "Reportes", description = "Consultas y exportacion de reportes de RRHH")
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsController {

  private static final String FORMAT_HEADER = "X-Report-Format";

  private final ReportsService reportsService;
  private final ReportExportFacade reportExportFacade;

  @Operation(
      summary = "Segmentos por salario",
      description = "Devuelve el total de empleados por segmento salarial.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Listado generado", content = @Content),
    @ApiResponse(responseCode = "400", description = "Solicitud invalida", content = @Content)
  })
  @GetMapping("/segments")
  public ResponseEntity<?> employeeSegments(
      @Parameter(
              name = FORMAT_HEADER,
              description = "Formato de salida: JSON (por defecto), XLSX o CSV",
              example = "XLSX")
          @RequestHeader(name = FORMAT_HEADER, required = false)
      ReportFormat format) {
    List<SegmentCountRow> rows = reportsService.getEmployeeSegments();
    return buildResponse(
        reportExportFacade.export(format, rows, "segments", ReportExportStrategy::exportSegments),
        rows);
  }

  @Operation(
      summary = "Segmentos por departamento",
      description = "Devuelve el total de empleados por segmento y departamento.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Listado generado", content = @Content),
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
  })
  @GetMapping("/departments/segments")
  public ResponseEntity<?> departmentSegments(
      @Parameter(
              name = FORMAT_HEADER,
              description = "Formato de salida: JSON (por defecto), XLSX o CSV",
              example = "CSV")
          @RequestHeader(name = FORMAT_HEADER, required = false)
          ReportFormat format) {
    List<SegmentByDepartmentCountRow> rows = reportsService.getDepartmentSegments();
    return buildResponse(
        reportExportFacade.export(
            format, rows, "department_segments", ReportExportStrategy::exportDepartmentSegments),
        rows);
  }

  @Operation(
      summary = "Top salarios por departamento",
      description = "Devuelve el/los empleados con mayor salario por departamento.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Listado generado", content = @Content),
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
  })
  @GetMapping("/departments/top-earners")
  public ResponseEntity<?> topEarnersByDepartment(
      @Parameter(
              name = FORMAT_HEADER,
              description = "Formato de salida: JSON (por defecto), XLSX o CSV",
              example = "JSON")
          @RequestHeader(name = FORMAT_HEADER, required = false)
          ReportFormat format) {
    List<DepartmentTopPaidRow> rows = reportsService.getTopEarnersByDepartment();
    return buildResponse(
        reportExportFacade.export(
            format, rows, "top_earners", ReportExportStrategy::exportTopEarnersByDepartment),
        rows);
  }

  @Operation(
      summary = "Gerentes con más de N años",
      description = "Devuelve gerentes con antiguedad mayor al mínimo indicado.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Listado generado", content = @Content),
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
  })
  @GetMapping("/managers")
  public ResponseEntity<?> managersWithTenure(
      @Parameter(description = "Años mínimos de antigüedad", example = "15")
          @RequestParam(name = "minYears", defaultValue = "15")
          int minYears,
      @Parameter(
              name = FORMAT_HEADER,
              description = "Formato de salida: JSON (por defecto), XLSX o CSV",
              example = "XLSX")
          @RequestHeader(name = FORMAT_HEADER, required = false)
          ReportFormat format) {
    List<ManagerRow> rows = reportsService.getManagersWithTenureGreaterThan(minYears);
    return buildResponse(
        reportExportFacade.export(format, rows, "managers", ReportExportStrategy::exportManagers),
        rows);
  }

  @Operation(
      summary = "Resumen salarial por departamento",
      description = "Devuelve cantidad de empleados y salario promedio por departamento.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Listado generado", content = @Content),
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
  })
  @GetMapping("/departments/salary-summary")
  public ResponseEntity<?> departmentSalarySummary(
      @Parameter(description = "Minimo de empleados para incluir el departamento", example = "10")
          @RequestParam(name = "minEmployees", defaultValue = "10")
          int minEmployees,
      @Parameter(
              name = FORMAT_HEADER,
              description = "Formato de salida: JSON (por defecto), XLSX o CSV",
              example = "CSV")
          @RequestHeader(name = FORMAT_HEADER, required = false)
          ReportFormat format) {
    List<DepartmentSalarySummaryRow> rows = reportsService.getDepartmentSalarySummary(minEmployees);
    return buildResponse(
        reportExportFacade.export(
            format,
            rows,
            "department_salary_summary",
            ReportExportStrategy::exportDepartmentSalarySummary),
        rows);
  }

  @Operation(
      summary = "Resumen salarial por pais",
      description = "Devuelve estadisticas salariales por pais.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Listado generado", content = @Content),
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
  })
  @GetMapping("/countries/salary-summary")
  public ResponseEntity<?> countrySalarySummary(
      @Parameter(
              name = FORMAT_HEADER,
              description = "Formato de salida: JSON (por defecto), XLSX o CSV",
              example = "JSON")
          @RequestHeader(name = FORMAT_HEADER, required = false)
          ReportFormat format) {
    List<CountrySalarySummaryRow> rows = reportsService.getCountrySalarySummary();
    return buildResponse(
        reportExportFacade.export(
            format,
            rows,
            "country_salary_summary",
            ReportExportStrategy::exportCountrySalarySummary),
        rows);
  }

  private ResponseEntity<?> buildResponse(
      Optional<ReportExportStrategy.ExportPayload> exportPayload, Object jsonBody) {
    if (exportPayload.isPresent()) {
      ReportExportStrategy.ExportPayload payload = exportPayload.get();
      return ResponseEntity.ok()
          .header(
              HttpHeaders.CONTENT_DISPOSITION,
              "attachment; filename=\"" + payload.filename() + "\"")
          .contentType(MediaType.parseMediaType(payload.contentType()))
          .body(payload.data());
    }
    return ResponseEntity.ok(jsonBody);
  }
}
