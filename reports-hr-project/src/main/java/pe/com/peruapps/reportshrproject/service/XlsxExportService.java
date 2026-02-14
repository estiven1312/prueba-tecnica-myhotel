package pe.com.peruapps.reportshrproject.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class XlsxExportService implements ReportExportStrategy {

  @Override
  public ReportFormat format() {
    return ReportFormat.XLSX;
  }

  @Override
  public byte[] exportSegments(List<SegmentCountRow> rows) {
    return toWorkbook(
        "segments",
        ReportHeaders.SEGMENTS,
        rows.stream().map(row -> rowList(row.segment(), row.totalEmployees())).toList());
  }

  @Override
  public byte[] exportDepartmentSegments(List<SegmentByDepartmentCountRow> rows) {
    return toWorkbook(
        "department_segments",
        ReportHeaders.DEPARTMENT_SEGMENTS,
        rows.stream()
            .map(
                row ->
                    rowList(
                        row.departmentId(),
                        row.departmentName(),
                        row.segment(),
                        row.totalEmployees()))
            .toList());
  }

  @Override
  public byte[] exportTopEarnersByDepartment(List<DepartmentTopPaidRow> rows) {
    return toWorkbook(
        "top_earners",
        ReportHeaders.TOP_EARNERS,
        rows.stream()
            .map(
                row ->
                    rowList(
                        row.departmentId(),
                        row.departmentName(),
                        row.employeeId(),
                        row.firstName(),
                        row.lastName(),
                        row.jobId(),
                        row.salary()))
            .toList());
  }

  @Override
  public byte[] exportManagers(List<ManagerRow> rows) {
    return toWorkbook(
        "managers",
        ReportHeaders.MANAGERS,
        rows.stream()
            .map(
                row ->
                    rowList(
                        row.employeeId(),
                        row.firstName(),
                        row.lastName(),
                        row.jobId(),
                        row.hireDate(),
                        row.yearsWorked(),
                        row.salary(),
                        row.departmentId(),
                        row.managerId()))
            .toList());
  }

  @Override
  public byte[] exportDepartmentSalarySummary(List<DepartmentSalarySummaryRow> rows) {
    return toWorkbook(
        "department_salary_summary",
        ReportHeaders.DEPARTMENT_SALARY_SUMMARY,
        rows.stream()
            .map(
                row ->
                    rowList(
                        row.departmentId(),
                        row.departmentName(),
                        row.totalEmployees(),
                        row.avgSalary()))
            .toList());
  }

  @Override
  public byte[] exportCountrySalarySummary(List<CountrySalarySummaryRow> rows) {
    return toWorkbook(
        "country_salary_summary",
        ReportHeaders.COUNTRY_SALARY_SUMMARY,
        rows.stream()
            .map(
                row ->
                    rowList(
                        row.countryId(),
                        row.countryName(),
                        row.totalEmployees(),
                        row.avgSalary(),
                        row.upperSalary(),
                        row.lowerSalary(),
                        row.avgYearsOfActivity()))
            .toList());
  }

  private List<Object> rowList(Object... values) {
    List<Object> row = new ArrayList<>(values == null ? 0 : values.length);
    if (values == null) {
      return row;
    }
    for (Object value : values) {
      row.add(Optional.ofNullable(value).orElse(""));
    }
    return row;
  }

  private byte[] toWorkbook(String sheetName, List<String> headers, List<List<Object>> rows) {
    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet(sheetName);
      Row headerRow = sheet.createRow(0);
      for (int i = 0; i < headers.size(); i++) {
        headerRow.createCell(i).setCellValue(headers.get(i));
      }

      int rowIndex = 1;
      for (List<Object> values : rows) {
        Row row = sheet.createRow(rowIndex++);
        for (int i = 0; i < values.size(); i++) {
          Cell cell = row.createCell(i);
          setCellValue(cell, values.get(i));
        }
      }

      for (int i = 0; i < headers.size(); i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(outputStream);
      return outputStream.toByteArray();
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to generate XLSX report", ex);
    }
  }

  private void setCellValue(Cell cell, Object value) {
    switch (value) {
      case null -> {
        return;
      }
      case Number number -> {
        cell.setCellValue(number.doubleValue());
        return;
      }
      case Boolean bool -> {
        cell.setCellValue(bool);
        return;
      }
      default -> {}
    }
    cell.setCellValue(value.toString());
  }
}
