package pe.com.peruapps.reportshrproject.service;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import pe.com.peruapps.reportshrproject.dto.ReportFormat;
import pe.com.peruapps.reportshrproject.dto.CountrySalarySummaryRow;
import pe.com.peruapps.reportshrproject.dto.DepartmentSalarySummaryRow;
import pe.com.peruapps.reportshrproject.dto.DepartmentTopPaidRow;
import pe.com.peruapps.reportshrproject.dto.ManagerRow;
import pe.com.peruapps.reportshrproject.dto.SegmentByDepartmentCountRow;
import pe.com.peruapps.reportshrproject.dto.SegmentCountRow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CsvExportService implements ReportExportStrategy {

  @Override
  public ReportFormat format() {
    return ReportFormat.CSV;
  }

  @Override
  public byte[] exportSegments(List<SegmentCountRow> rows) {
    List<String[]> matrix = new ArrayList<>();
    matrix.add(headerRow(ReportExportStrategy.ReportHeaders.SEGMENTS));
    for (SegmentCountRow row : rows) {
      matrix.add(row(row.segment(), row.totalEmployees()));
    }
    return toCsv(matrix);
  }

  @Override
  public byte[] exportDepartmentSegments(List<SegmentByDepartmentCountRow> rows) {
    List<String[]> matrix = new ArrayList<>();
    matrix.add(headerRow(ReportExportStrategy.ReportHeaders.DEPARTMENT_SEGMENTS));
    for (SegmentByDepartmentCountRow row : rows) {
      matrix.add(
          row(row.departmentId(), row.departmentName(), row.segment(), row.totalEmployees()));
    }
    return toCsv(matrix);
  }

  @Override
  public byte[] exportTopEarnersByDepartment(List<DepartmentTopPaidRow> rows) {
    List<String[]> matrix = new ArrayList<>();
    matrix.add(headerRow(ReportExportStrategy.ReportHeaders.TOP_EARNERS));
    for (DepartmentTopPaidRow row : rows) {
      matrix.add(
          row(
              row.departmentId(),
              row.departmentName(),
              row.employeeId(),
              row.firstName(),
              row.lastName(),
              row.jobId(),
              row.salary()));
    }
    return toCsv(matrix);
  }

  @Override
  public byte[] exportManagers(List<ManagerRow> rows) {
    List<String[]> matrix = new ArrayList<>();
    matrix.add(headerRow(ReportExportStrategy.ReportHeaders.MANAGERS));
    for (ManagerRow row : rows) {
      matrix.add(
          row(
              row.employeeId(),
              row.firstName(),
              row.lastName(),
              row.jobId(),
              row.hireDate(),
              row.yearsWorked(),
              row.salary(),
              row.departmentId(),
              row.managerId()));
    }
    return toCsv(matrix);
  }

  @Override
  public byte[] exportDepartmentSalarySummary(List<DepartmentSalarySummaryRow> rows) {
    List<String[]> matrix = new ArrayList<>();
    matrix.add(headerRow(ReportExportStrategy.ReportHeaders.DEPARTMENT_SALARY_SUMMARY));
    for (DepartmentSalarySummaryRow row : rows) {
      matrix.add(
          row(row.departmentId(), row.departmentName(), row.totalEmployees(), row.avgSalary()));
    }
    return toCsv(matrix);
  }

  @Override
  public byte[] exportCountrySalarySummary(List<CountrySalarySummaryRow> rows) {
    List<String[]> matrix = new ArrayList<>();
    matrix.add(headerRow(ReportExportStrategy.ReportHeaders.COUNTRY_SALARY_SUMMARY));
    for (CountrySalarySummaryRow row : rows) {
      matrix.add(
          row(
              row.countryId(),
              row.countryName(),
              row.totalEmployees(),
              row.avgSalary(),
              row.upperSalary(),
              row.lowerSalary(),
              row.avgYearsOfActivity()));
    }
    return toCsv(matrix);
  }

  private String[] headerRow(List<String> headers) {
    return headers.toArray(new String[0]);
  }

  private String[] row(Object... values) {
    String[] row = new String[values == null ? 0 : values.length];
    if (values == null) {
      return row;
    }
    for (int i = 0; i < values.length; i++) {
      row[i] = Optional.ofNullable(values[i]).map(Object::toString).orElse("");
    }
    return row;
  }

  private byte[] toCsv(List<String[]> matrix) {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(writer)) {

      csvWriter.writeAll(matrix, false);
      csvWriter.flush();
      return out.toByteArray();

    } catch (IOException ex) {
      throw new IllegalStateException("Failed to generate CSV report", ex);
    }
  }
}
