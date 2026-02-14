package pe.com.peruapps.reportshrproject.dto;

import java.math.BigDecimal;

public record DepartmentTopPaidRow(
    long departmentId,
    String departmentName,
    long employeeId,
    String firstName,
    String lastName,
    String jobId,
    BigDecimal salary) {}
