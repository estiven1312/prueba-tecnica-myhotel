package pe.com.peruapps.reportshrproject.controller;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pe.com.peruapps.reportshrproject.dto.ReportFormat;

@RestControllerAdvice
public class GlobalControllerAdvice {
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ProblemDetail handleException(MethodArgumentTypeMismatchException ex, WebRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(400);
    problemDetail.setTitle("Bad Request");
    problemDetail.setDetail("The request is invalid. Please check the parameters and try again.");
    String parameterName = ex.getName();
    if (!parameterName.isBlank()) {
      problemDetail.setProperty("parameter", parameterName);
    }
    Object value = ex.getValue();
    if (value != null) {
      problemDetail.setProperty("invalidValue", value);
    }
    if ("X-Report-Format".equalsIgnoreCase(parameterName)) {
      problemDetail.setDetail("Invalid X-Report-Format value. Allowed values: JSON, XLSX, CSV.");
    }
    return problemDetail;
  }

  @ExceptionHandler(ReportFormat.InvalidReportFormatException.class)
  public ProblemDetail handleException(
      ReportFormat.InvalidReportFormatException ex, WebRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(400);
    problemDetail.setTitle("Bad Request");
    problemDetail.setDetail(
        "Only JSON, XLSX and CSV formats are supported. Please check the X-Report-Format header and try again.");
    return problemDetail;
  }
}
