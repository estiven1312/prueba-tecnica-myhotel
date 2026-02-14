package pe.com.peruapps.reportshrproject.service;

import org.springframework.stereotype.Service;
import pe.com.peruapps.reportshrproject.dto.ReportFormat;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReportExportFacade {

  private final Map<ReportFormat, ReportExportStrategy> strategies;

  public ReportExportFacade(List<ReportExportStrategy> strategies) {
    this.strategies = new EnumMap<>(ReportFormat.class);
    for (ReportExportStrategy strategy : strategies) {
      this.strategies.put(strategy.format(), strategy);
    }
  }

  public <T> Optional<ReportExportStrategy.ExportPayload> export(
      ReportFormat format, List<T> rows, String baseFilename, Exporter<T> exporter) {
    ReportFormat reportFormat = Optional.ofNullable(format).orElse(ReportFormat.JSON);
    if (reportFormat == ReportFormat.JSON) {
      return Optional.empty();
    }
    ReportExportStrategy strategy =
        Optional.ofNullable(strategies.get(reportFormat))
            .orElseThrow(
                () ->
                    new ReportFormat.InvalidReportFormatException(
                        "Unsupported report format: " + reportFormat));
    byte[] payload = exporter.apply(strategy, rows);
    return Optional.of(
        new ReportExportStrategy.ExportPayload(
            payload, strategy.contentType(), baseFilename + "." + strategy.extension()));
  }

  @FunctionalInterface
  public interface Exporter<T> {
    byte[] apply(ReportExportStrategy strategy, List<T> rows);
  }
}
