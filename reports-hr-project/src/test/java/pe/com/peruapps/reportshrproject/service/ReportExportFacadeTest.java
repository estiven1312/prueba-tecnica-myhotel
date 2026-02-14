package pe.com.peruapps.reportshrproject.service;

import org.junit.jupiter.api.Test;
import pe.com.peruapps.reportshrproject.dto.ReportFormat;
import pe.com.peruapps.reportshrproject.dto.SegmentCountRow;
import pe.com.peruapps.reportshrproject.service.util.CsvStrategy;
import pe.com.peruapps.reportshrproject.service.util.XlsxStrategy;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ReportExportFacadeTest {

  @Test
  void exportReturnsEmptyWhenFormatIsNullOrJson() {
    ReportExportFacade facade = new ReportExportFacade(List.of(new CsvStrategy()));
    List<SegmentCountRow> rows = List.of(new SegmentCountRow("SEGMENTO A", 2));

    assertTrue(facade.export(null, rows, "segments", (strategy, list) -> new byte[0]).isEmpty());
    assertTrue(
        facade
            .export(ReportFormat.JSON, rows, "segments", (strategy, list) -> new byte[0])
            .isEmpty());
  }

  @Test
  void exportUsesStrategyAndBuildsPayload() {
    ReportExportFacade facade = new ReportExportFacade(List.of(new CsvStrategy()));
    List<SegmentCountRow> rows = List.of(new SegmentCountRow("SEGMENTO A", 2));

    Optional<ReportExportStrategy.ExportPayload> payload =
        facade.export(ReportFormat.CSV, rows, "segments", (strategy, list) -> "ok".getBytes());

    assertTrue(payload.isPresent());
    assertArrayEquals("ok".getBytes(), payload.get().data());
    assertEquals("text/csv", payload.get().contentType());
    assertEquals("segments.csv", payload.get().filename());
  }

  @Test
  void exportThrowsWhenStrategyMissing() {
    ReportExportFacade facade = new ReportExportFacade(List.of(new CsvStrategy()));
    List<SegmentCountRow> rows = List.of(new SegmentCountRow("SEGMENTO A", 2));

    assertThrows(
        ReportFormat.InvalidReportFormatException.class,
        () -> facade.export(ReportFormat.XLSX, rows, "segments", (strategy, list) -> new byte[0]));
  }

  @Test
  void exportUsesExactStrategyForFormat() {
    ReportExportFacade facade =
        new ReportExportFacade(List.of(new CsvStrategy(), new XlsxStrategy()));
    List<SegmentCountRow> rows = List.of(new SegmentCountRow("SEGMENTO A", 2));

    Optional<ReportExportStrategy.ExportPayload> payload =
        facade.export(ReportFormat.CSV, rows, "segments", (strategy, list) -> "csv".getBytes());

    assertTrue(payload.isPresent());
    assertEquals("segments.csv", payload.get().filename());
    assertEquals("text/csv", payload.get().contentType());
  }
}
