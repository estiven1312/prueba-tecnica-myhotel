package pe.com.peruapps.reportshrproject.dto;

import java.math.BigDecimal;

public record CountrySalarySummaryRow(
    String countryId,
    String countryName,
    long totalEmployees,
    BigDecimal avgSalary,
    BigDecimal upperSalary,
    BigDecimal lowerSalary,
    BigDecimal avgYearsOfActivity) {}
