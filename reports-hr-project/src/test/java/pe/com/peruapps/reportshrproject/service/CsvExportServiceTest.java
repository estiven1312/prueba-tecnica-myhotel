package pe.com.peruapps.reportshrproject.service;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import pe.com.peruapps.reportshrproject.dto.*;

import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsvExportServiceTest {

  private final CsvExportService service = new CsvExportService();

  @Test
  void exportManagersWritesHeadersAndHandlesNulls() throws Exception {
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
    assertNotNull(bytes);

    try (CSVReader reader =
        new CSVReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)))) {
      List<String[]> rows = reader.readAll();
      assertEquals(2, rows.size());
      assertArrayEquals(
          ReportExportStrategy.ReportHeaders.MANAGERS.toArray(new String[0]), rows.get(0));
      assertEquals("", rows.get(1)[8]);
    }
  }

  @Test
  void exportSegmentsWritesHeadersAndRows() throws Exception {
    byte[] bytes = service.exportSegments(List.of(new SegmentCountRow("SEGMENTO A", 2)));

    try (CSVReader reader =
        new CSVReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)))) {
      List<String[]> rows = reader.readAll();
      assertEquals(2, rows.size());
      assertArrayEquals(
          ReportExportStrategy.ReportHeaders.SEGMENTS.toArray(new String[0]), rows.get(0));
      assertEquals("SEGMENTO A", rows.get(1)[0]);
      assertEquals("2", rows.get(1)[1]);
    }
  }

  @Test
  void exportDepartmentSegmentsWritesHeadersAndRows() throws Exception {
    var row = new SegmentByDepartmentCountRow(10L, "IT", "SEGMENTO B", 5);

    byte[] bytes = service.exportDepartmentSegments(List.of(row));

    try (CSVReader reader =
        new CSVReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)))) {
      List<String[]> rows = reader.readAll();
      assertEquals(2, rows.size());
      assertArrayEquals(
          ReportExportStrategy.ReportHeaders.DEPARTMENT_SEGMENTS.toArray(new String[0]),
          rows.get(0));
      assertEquals("10", rows.get(1)[0]);
      assertEquals("IT", rows.get(1)[1]);
      assertEquals("SEGMENTO B", rows.get(1)[2]);
      assertEquals("5", rows.get(1)[3]);
    }
  }

  @Test
  void exportTopEarnersByDepartmentWritesHeadersAndRows() throws Exception {
    var row =
        new DepartmentTopPaidRow(
            20L, "Sales", 100L, "Ana", "Diaz", "SA_MAN", new BigDecimal("9000"));

    byte[] bytes = service.exportTopEarnersByDepartment(List.of(row));

    try (CSVReader reader =
        new CSVReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)))) {
      List<String[]> rows = reader.readAll();
      assertEquals(2, rows.size());
      assertArrayEquals(
          ReportExportStrategy.ReportHeaders.TOP_EARNERS.toArray(new String[0]), rows.get(0));
      assertEquals("20", rows.get(1)[0]);
      assertEquals("Sales", rows.get(1)[1]);
      assertEquals("100", rows.get(1)[2]);
      assertEquals("Ana", rows.get(1)[3]);
      assertEquals("Diaz", rows.get(1)[4]);
      assertEquals("SA_MAN", rows.get(1)[5]);
      assertEquals("9000", rows.get(1)[6]);
    }
  }

  @Test
  void exportDepartmentSalarySummaryWritesHeadersAndRows() throws Exception {
    var row = new DepartmentSalarySummaryRow(30L, "HR", 12L, new BigDecimal("4300.50"));

    byte[] bytes = service.exportDepartmentSalarySummary(List.of(row));

    try (CSVReader reader =
        new CSVReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)))) {
      List<String[]> rows = reader.readAll();
      assertEquals(2, rows.size());
      assertArrayEquals(
          ReportExportStrategy.ReportHeaders.DEPARTMENT_SALARY_SUMMARY.toArray(new String[0]),
          rows.get(0));
      assertEquals("30", rows.get(1)[0]);
      assertEquals("HR", rows.get(1)[1]);
      assertEquals("12", rows.get(1)[2]);
      assertEquals("4300.50", rows.get(1)[3]);
    }
  }

  @Test
  void exportCountrySalarySummaryWritesHeadersAndRows() throws Exception {
    var row =
        new CountrySalarySummaryRow(
            "US",
            "United States",
            50L,
            new BigDecimal("6200.00"),
            new BigDecimal("12000.00"),
            new BigDecimal("2500.00"),
            new BigDecimal("6.5"));

    byte[] bytes = service.exportCountrySalarySummary(List.of(row));

    try (CSVReader reader =
        new CSVReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)))) {
      List<String[]> rows = reader.readAll();
      assertEquals(2, rows.size());
      assertArrayEquals(
          ReportExportStrategy.ReportHeaders.COUNTRY_SALARY_SUMMARY.toArray(new String[0]),
          rows.get(0));
      assertEquals("US", rows.get(1)[0]);
      assertEquals("United States", rows.get(1)[1]);
      assertEquals("50", rows.get(1)[2]);
      assertEquals("6200.00", rows.get(1)[3]);
      assertEquals("12000.00", rows.get(1)[4]);
      assertEquals("2500.00", rows.get(1)[5]);
      assertEquals("6.5", rows.get(1)[6]);
    }
  }
}
