package pe.com.peruapps.reportshrproject.dto;

public enum ReportFormat {
  JSON("application/json", "json"),
  XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx"),
  CSV("text/csv", "csv");

  private final String contentType;
  private final String extension;

  ReportFormat(String contentType, String extension) {
    this.contentType = contentType;
    this.extension = extension;
  }

  public String contentType() {
    return contentType;
  }

  public String extension() {
    return extension;
  }

  public static class InvalidReportFormatException extends IllegalArgumentException {
    public InvalidReportFormatException(String message) {
      super(message);
    }
  }
}
