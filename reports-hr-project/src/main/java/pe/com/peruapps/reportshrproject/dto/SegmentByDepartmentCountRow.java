package pe.com.peruapps.reportshrproject.dto;

public record SegmentByDepartmentCountRow(
    long departmentId, String departmentName, String segment, long totalEmployees) {}
