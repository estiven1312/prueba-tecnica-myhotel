package pe.com.peruapps.reportshrproject.service;

import pe.com.peruapps.reportshrproject.dto.ReportFormat;
import pe.com.peruapps.reportshrproject.dto.CountrySalarySummaryRow;
import pe.com.peruapps.reportshrproject.dto.DepartmentSalarySummaryRow;
import pe.com.peruapps.reportshrproject.dto.DepartmentTopPaidRow;
import pe.com.peruapps.reportshrproject.dto.ManagerRow;
import pe.com.peruapps.reportshrproject.dto.SegmentByDepartmentCountRow;
import pe.com.peruapps.reportshrproject.dto.SegmentCountRow;

import java.util.List;

public interface ReportExportStrategy {

  ReportFormat format();

  default String contentType() {
    return format().contentType();
  }

  default String extension() {
    return format().extension();
  }

  byte[] exportSegments(List<SegmentCountRow> rows);

  byte[] exportDepartmentSegments(List<SegmentByDepartmentCountRow> rows);

  byte[] exportTopEarnersByDepartment(List<DepartmentTopPaidRow> rows);

  byte[] exportManagers(List<ManagerRow> rows);

  byte[] exportDepartmentSalarySummary(List<DepartmentSalarySummaryRow> rows);

  byte[] exportCountrySalarySummary(List<CountrySalarySummaryRow> rows);

  record ExportPayload(byte[] data, String contentType, String filename) {}

  class ReportHeaders {

    public static final List<String> SEGMENTS = List.of("SEGMENT", "TOTAL_EMPLOYEES");
    public static final List<String> DEPARTMENT_SEGMENTS =
        List.of("DEPARTMENT_ID", "DEPARTMENT_NAME", "SEGMENT", "TOTAL_EMPLOYEES");
    public static final List<String> TOP_EARNERS =
        List.of(
            "DEPARTMENT_ID",
            "DEPARTMENT_NAME",
            "EMPLOYEE_ID",
            "FIRST_NAME",
            "LAST_NAME",
            "JOB_ID",
            "SALARY");
    public static final List<String> MANAGERS =
        List.of(
            "EMPLOYEE_ID",
            "FIRST_NAME",
            "LAST_NAME",
            "JOB_ID",
            "HIRE_DATE",
            "YEARS_WORKED",
            "SALARY",
            "DEPARTMENT_ID",
            "MANAGER_ID");
    public static final List<String> DEPARTMENT_SALARY_SUMMARY =
        List.of("DEPARTMENT_ID", "DEPARTMENT_NAME", "TOTAL_EMPLOYEES", "AVG_SALARY");
    public static final List<String> COUNTRY_SALARY_SUMMARY =
        List.of(
            "COUNTRY_ID",
            "COUNTRY_NAME",
            "TOTAL_EMPLOYEES",
            "AVG_SALARY",
            "UPPER_SALARY",
            "LOWER_SALARY",
            "AVG_YEARS_OF_ACTIVITY");
  }
}
