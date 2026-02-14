package pe.com.peruapps.reportshrproject.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import pe.com.peruapps.reportshrproject.dto.ManagerRow;
import pe.com.peruapps.reportshrproject.dto.SegmentCountRow;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class XlsxExportServiceTest {

  private final XlsxExportService service = new XlsxExportService();

  @Test
  void exportSegmentsWritesHeadersAndRows() throws Exception {
    byte[] bytes = service.exportSegments(List.of(new SegmentCountRow("SEGMENTO A", 2)));

    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
      Sheet sheet = workbook.getSheet("segments");
      assertNotNull(sheet);

      Row header = sheet.getRow(0);
      assertEquals("SEGMENT", header.getCell(0).getStringCellValue());
      assertEquals("TOTAL_EMPLOYEES", header.getCell(1).getStringCellValue());

      Row data = sheet.getRow(1);
      assertEquals("SEGMENTO A", data.getCell(0).getStringCellValue());
      assertEquals(2, data.getCell(1).getNumericCellValue());
    }
  }

  @Test
  void exportManagersWritesBlankCellsForNulls() throws Exception {
    ManagerRow row =
        new ManagerRow(
            100L,
            "Ana",
            "Diaz",
            "MG_01",
            LocalDate.of(2010, 1, 10),
            15L,
            new BigDecimal("5500.00"),
            new BigDecimal("90"),
            null);

    byte[] bytes = service.exportManagers(List.of(row));

    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
      Sheet sheet = workbook.getSheet("managers");
      Row data = sheet.getRow(1);
      Cell cell = data.getCell(8);
      assertNotNull(cell);

      assertEquals(CellType.STRING, cell.getCellType());
      Cell cell2 = data.getCell(7);
      assertNotNull(cell2);
      assertEquals(CellType.NUMERIC, cell2.getCellType());
    }
  }

  @Test
  void exportDepartmentSegmentsWritesHeadersAndRows() throws Exception {
    var row =
        new pe.com.peruapps.reportshrproject.dto.SegmentByDepartmentCountRow(
            10L, "IT", "SEGMENTO B", 5);

    byte[] bytes = service.exportDepartmentSegments(List.of(row));

    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
      Sheet sheet = workbook.getSheet("department_segments");
      assertNotNull(sheet);

      Row header = sheet.getRow(0);
      assertEquals("DEPARTMENT_ID", header.getCell(0).getStringCellValue());
      assertEquals("DEPARTMENT_NAME", header.getCell(1).getStringCellValue());
      assertEquals("SEGMENT", header.getCell(2).getStringCellValue());
      assertEquals("TOTAL_EMPLOYEES", header.getCell(3).getStringCellValue());

      Row data = sheet.getRow(1);
      assertEquals(10, (int) data.getCell(0).getNumericCellValue());
      assertEquals("IT", data.getCell(1).getStringCellValue());
      assertEquals("SEGMENTO B", data.getCell(2).getStringCellValue());
      assertEquals(5, (int) data.getCell(3).getNumericCellValue());
    }
  }

  @Test
  void exportTopEarnersByDepartmentWritesHeadersAndRows() throws Exception {
    var row =
        new pe.com.peruapps.reportshrproject.dto.DepartmentTopPaidRow(
            20L, "Sales", 100L, "Ana", "Diaz", "SA_MAN", new java.math.BigDecimal("9000"));

    byte[] bytes = service.exportTopEarnersByDepartment(List.of(row));

    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
      Sheet sheet = workbook.getSheet("top_earners");
      assertNotNull(sheet);

      Row header = sheet.getRow(0);
      assertEquals("DEPARTMENT_ID", header.getCell(0).getStringCellValue());
      assertEquals("DEPARTMENT_NAME", header.getCell(1).getStringCellValue());
      assertEquals("EMPLOYEE_ID", header.getCell(2).getStringCellValue());
      assertEquals("FIRST_NAME", header.getCell(3).getStringCellValue());
      assertEquals("LAST_NAME", header.getCell(4).getStringCellValue());
      assertEquals("JOB_ID", header.getCell(5).getStringCellValue());
      assertEquals("SALARY", header.getCell(6).getStringCellValue());

      Row data = sheet.getRow(1);
      assertEquals(20, (int) data.getCell(0).getNumericCellValue());
      assertEquals("Sales", data.getCell(1).getStringCellValue());
      assertEquals(100, (int) data.getCell(2).getNumericCellValue());
      assertEquals("Ana", data.getCell(3).getStringCellValue());
      assertEquals("Diaz", data.getCell(4).getStringCellValue());
      assertEquals("SA_MAN", data.getCell(5).getStringCellValue());
      assertEquals(9000, (int) data.getCell(6).getNumericCellValue());
    }
  }

  @Test
  void exportDepartmentSalarySummaryWritesHeadersAndRows() throws Exception {
    var row =
        new pe.com.peruapps.reportshrproject.dto.DepartmentSalarySummaryRow(
            30L, "HR", 12L, new java.math.BigDecimal("4300.50"));

    byte[] bytes = service.exportDepartmentSalarySummary(List.of(row));

    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
      Sheet sheet = workbook.getSheet("department_salary_summary");
      assertNotNull(sheet);

      Row header = sheet.getRow(0);
      assertEquals("DEPARTMENT_ID", header.getCell(0).getStringCellValue());
      assertEquals("DEPARTMENT_NAME", header.getCell(1).getStringCellValue());
      assertEquals("TOTAL_EMPLOYEES", header.getCell(2).getStringCellValue());
      assertEquals("AVG_SALARY", header.getCell(3).getStringCellValue());

      Row data = sheet.getRow(1);
      assertEquals(30, (int) data.getCell(0).getNumericCellValue());
      assertEquals("HR", data.getCell(1).getStringCellValue());
      assertEquals(12, (int) data.getCell(2).getNumericCellValue());
      assertEquals(4300.50, data.getCell(3).getNumericCellValue(), 0.001);
    }
  }

  @Test
  void exportCountrySalarySummaryWritesHeadersAndRows() throws Exception {
    var row =
        new pe.com.peruapps.reportshrproject.dto.CountrySalarySummaryRow(
            "US",
            "United States",
            50L,
            new java.math.BigDecimal("6200.00"),
            new java.math.BigDecimal("12000.00"),
            new java.math.BigDecimal("2500.00"),
            new java.math.BigDecimal("6.5"));

    byte[] bytes = service.exportCountrySalarySummary(List.of(row));

    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
      Sheet sheet = workbook.getSheet("country_salary_summary");
      assertNotNull(sheet);

      Row header = sheet.getRow(0);
      assertEquals("COUNTRY_ID", header.getCell(0).getStringCellValue());
      assertEquals("COUNTRY_NAME", header.getCell(1).getStringCellValue());
      assertEquals("TOTAL_EMPLOYEES", header.getCell(2).getStringCellValue());
      assertEquals("AVG_SALARY", header.getCell(3).getStringCellValue());
      assertEquals("UPPER_SALARY", header.getCell(4).getStringCellValue());
      assertEquals("LOWER_SALARY", header.getCell(5).getStringCellValue());
      assertEquals("AVG_YEARS_OF_ACTIVITY", header.getCell(6).getStringCellValue());

      Row data = sheet.getRow(1);
      assertEquals("US", data.getCell(0).getStringCellValue());
      assertEquals("United States", data.getCell(1).getStringCellValue());
      assertEquals(50, (int) data.getCell(2).getNumericCellValue());
      assertEquals(6200.00, data.getCell(3).getNumericCellValue(), 0.001);
      assertEquals(12000.00, data.getCell(4).getNumericCellValue(), 0.001);
      assertEquals(2500.00, data.getCell(5).getNumericCellValue(), 0.001);
      assertEquals(6.5, data.getCell(6).getNumericCellValue());
    }
  }
}
