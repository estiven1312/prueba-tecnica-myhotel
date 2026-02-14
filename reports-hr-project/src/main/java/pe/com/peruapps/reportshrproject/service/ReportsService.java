package pe.com.peruapps.reportshrproject.service;

import org.springframework.stereotype.Service;
import pe.com.peruapps.reportshrproject.dto.CountrySalarySummaryRow;
import pe.com.peruapps.reportshrproject.dto.DepartmentSalarySummaryRow;
import pe.com.peruapps.reportshrproject.dto.SegmentByDepartmentCountRow;
import pe.com.peruapps.reportshrproject.dto.DepartmentTopPaidRow;
import pe.com.peruapps.reportshrproject.dto.ManagerRow;
import pe.com.peruapps.reportshrproject.dto.SegmentCountRow;
import pe.com.peruapps.reportshrproject.repository.ReportsRepository;

import java.util.List;

@Service
public class ReportsService {

  private final ReportsRepository reportsRepository;

  public ReportsService(ReportsRepository reportsRepository) {
    this.reportsRepository = reportsRepository;
  }

  public List<SegmentCountRow> getEmployeeSegments() {
    return reportsRepository.getSegmentsBySalary();
  }

  public List<SegmentByDepartmentCountRow> getDepartmentSegments() {
    return reportsRepository.getSegmentsBySalaryGrouppedByDepartment();
  }

  public List<DepartmentTopPaidRow> getTopEarnersByDepartment() {
    return reportsRepository.getBestPayedEmployeesByDepartment();
  }

  public List<ManagerRow> getManagersWithTenureGreaterThan(int minYears) {
    return reportsRepository.getManagersGreaterThanYearsOfWork(minYears);
  }

  public List<DepartmentSalarySummaryRow> getDepartmentSalarySummary(int minEmployees) {
    return reportsRepository.getDepartmentSalarySummary(minEmployees);
  }

  public List<CountrySalarySummaryRow> getCountrySalarySummary() {
    return reportsRepository.getCountrySummary();
  }
}
