package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.in.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.IllegalChangeStatusForMaintenanceException;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.MaintenanceNotFoundException;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.VehicleNotFoundException;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.DuplicatedLicensePlateInActiveVehicleException;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.ForbiddenChangeVehicleTypeException;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String TIMESTAMP_KEY = "timestamp";
  private static final String ERRORS_KEY = "errors";

  @ExceptionHandler(VehicleNotFoundException.class)
  public ProblemDetail handleVehicleNotFoundException(
      VehicleNotFoundException ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

    problemDetail.setTitle("Vehicle Not Found");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
    problemDetail.setProperty("vehicleId", ex.getVehicleId());

    return problemDetail;
  }

  @ExceptionHandler(DuplicatedLicensePlateInActiveVehicleException.class)
  public ProblemDetail handleDuplicatedLicensePlateException(
      DuplicatedLicensePlateInActiveVehicleException ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

    problemDetail.setTitle("Duplicated License Plate");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
    problemDetail.setProperty("licensePlate", ex.getLicensePlate());
    problemDetail.setProperty("existingVehicleId", ex.getVehicleId());

    return problemDetail;
  }

  @ExceptionHandler(ForbiddenChangeVehicleTypeException.class)
  public ProblemDetail handleForbiddenChangeVehicleTypeException(
      ForbiddenChangeVehicleTypeException ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

    problemDetail.setTitle("Forbidden Vehicle Type Change");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
    problemDetail.setProperty("vehicleId", ex.getVehicleId());
    problemDetail.setProperty("currentVehicleType", ex.getType().name());

    return problemDetail;
  }

  @ExceptionHandler(MaintenanceNotFoundException.class)
  public ProblemDetail handleMaintenanceNotFoundException(
      MaintenanceNotFoundException ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

    problemDetail.setTitle("Maintenance Not Found");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
    problemDetail.setProperty("maintenanceId", ex.getMaintenanceId());

    return problemDetail;
  }

  @ExceptionHandler(IllegalChangeStatusForMaintenanceException.class)
  public ProblemDetail handleIllegalChangeStatusForMaintenanceException(
      IllegalChangeStatusForMaintenanceException ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

    problemDetail.setTitle("Invalid Status Transition");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());

    return problemDetail;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationException(
      MethodArgumentNotValidException ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Validation failed for one or more fields");

    problemDetail.setTitle("Validation Error");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());

    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    problemDetail.setProperty(ERRORS_KEY, errors);

    return problemDetail;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ProblemDetail handleIllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

    problemDetail.setTitle("Invalid Argument");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());

    return problemDetail;
  }

  @ExceptionHandler(IllegalStateException.class)
  public ProblemDetail handleIllegalStateException(IllegalStateException ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

    problemDetail.setTitle("Invalid State");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());

    return problemDetail;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGenericException(Exception ex, WebRequest request) {

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred. Please try again later.");

    problemDetail.setTitle("Internal Server Error");
    problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
    problemDetail.setProperty("exceptionType", ex.getClass().getSimpleName());

    return problemDetail;
  }
}
