package pe.com.peruapps.reportshrproject.dto;

import java.math.BigDecimal;

public record DepartmentSalarySummaryRow(
    long departmentId, String departmentName, long totalEmployees, BigDecimal avgSalary) {}
