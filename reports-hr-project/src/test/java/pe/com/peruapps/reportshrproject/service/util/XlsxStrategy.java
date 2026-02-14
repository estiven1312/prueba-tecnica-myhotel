package pe.com.peruapps.reportshrproject.service.util;

import pe.com.peruapps.reportshrproject.dto.ReportFormat;
import pe.com.peruapps.reportshrproject.dto.SegmentCountRow;
import pe.com.peruapps.reportshrproject.service.ReportExportStrategy;

import java.util.List;

public class XlsxStrategy implements ReportExportStrategy {
  @Override
  public ReportFormat format() {
    return ReportFormat.XLSX;
  }

  @Override
  public byte[] exportSegments(List<SegmentCountRow> rows) {
    return new byte[0];
  }

  @Override
  public byte[] exportDepartmentSegments(
      List<pe.com.peruapps.reportshrproject.dto.SegmentByDepartmentCountRow> rows) {
    return new byte[0];
  }

  @Override
  public byte[] exportTopEarnersByDepartment(
      List<pe.com.peruapps.reportshrproject.dto.DepartmentTopPaidRow> rows) {
    return new byte[0];
  }

  @Override
  public byte[] exportManagers(List<pe.com.peruapps.reportshrproject.dto.ManagerRow> rows) {
    return new byte[0];
  }

  @Override
  public byte[] exportDepartmentSalarySummary(
      List<pe.com.peruapps.reportshrproject.dto.DepartmentSalarySummaryRow> rows) {
    return new byte[0];
  }

  @Override
  public byte[] exportCountrySalarySummary(
      List<pe.com.peruapps.reportshrproject.dto.CountrySalarySummaryRow> rows) {
    return new byte[0];
  }
}
