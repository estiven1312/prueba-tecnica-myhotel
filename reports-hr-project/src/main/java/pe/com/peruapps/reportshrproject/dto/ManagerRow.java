package pe.com.peruapps.reportshrproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ManagerRow(
    long employeeId,
    String firstName,
    String lastName,
    String jobId,
    LocalDate hireDate,
    long yearsWorked,
    BigDecimal salary,
    BigDecimal departmentId,
    BigDecimal managerId) {}
