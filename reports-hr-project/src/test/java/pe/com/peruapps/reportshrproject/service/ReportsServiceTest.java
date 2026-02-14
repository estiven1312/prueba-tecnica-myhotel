package pe.com.peruapps.reportshrproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.reportshrproject.dto.CountrySalarySummaryRow;
import pe.com.peruapps.reportshrproject.dto.SegmentCountRow;
import pe.com.peruapps.reportshrproject.repository.ReportsRepository;
import pe.com.peruapps.reportshrproject.dto.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportsServiceTest {

  @Mock private ReportsRepository reportsRepository;

  @InjectMocks private ReportsService reportsService;

  @Test
  void getEmployeeSegmentsDelegatesToRepository() {
    List<SegmentCountRow> expected = List.of(new SegmentCountRow("SEGMENTO A", 1));
    when(reportsRepository.getSegmentsBySalary()).thenReturn(expected);

    List<SegmentCountRow> result = reportsService.getEmployeeSegments();

    assertEquals(expected, result);
    verify(reportsRepository).getSegmentsBySalary();
  }

  @Test
  void getDepartmentSegmentsDelegatesToRepository() {
    var expected = List.of(new SegmentByDepartmentCountRow(10L, "IT", "SEGMENTO A", 3));
    when(reportsRepository.getSegmentsBySalaryGrouppedByDepartment()).thenReturn(expected);

    var result = reportsService.getDepartmentSegments();

    assertEquals(expected, result);
    verify(reportsRepository).getSegmentsBySalaryGrouppedByDepartment();
  }

  @Test
  void getTopEarnersByDepartmentDelegatesToRepository() {
    var expected =
        List.of(
            new DepartmentTopPaidRow(
                20L, "Sales", 100L, "Ana", "Diaz", "SA_MAN", new java.math.BigDecimal("9000")));
    when(reportsRepository.getBestPayedEmployeesByDepartment()).thenReturn(expected);

    var result = reportsService.getTopEarnersByDepartment();

    assertEquals(expected, result);
    verify(reportsRepository).getBestPayedEmployeesByDepartment();
  }

  @Test
  void getManagersWithTenureGreaterThanDelegatesToRepository() {
    var expected =
        List.of(
            new ManagerRow(
                100L,
                "Ana",
                "Diaz",
                "MG_01",
                java.time.LocalDate.of(2010, 1, 10),
                15L,
                new java.math.BigDecimal("5500.00"),
                new java.math.BigDecimal("90"),
                new java.math.BigDecimal("10")));
    when(reportsRepository.getManagersGreaterThanYearsOfWork(12)).thenReturn(expected);

    var result = reportsService.getManagersWithTenureGreaterThan(12);

    assertEquals(expected, result);
    verify(reportsRepository).getManagersGreaterThanYearsOfWork(12);
  }

  @Test
  void getDepartmentSalarySummaryDelegatesToRepository() {
    var expected =
        List.of(
            new DepartmentSalarySummaryRow(30L, "HR", 12L, new java.math.BigDecimal("4300.50")));
    when(reportsRepository.getDepartmentSalarySummary(10)).thenReturn(expected);

    var result = reportsService.getDepartmentSalarySummary(10);

    assertEquals(expected, result);
    verify(reportsRepository).getDepartmentSalarySummary(10);
  }

  @Test
  void getCountrySalarySummaryDelegatesToRepository() {
    var expected =
        List.of(
            new CountrySalarySummaryRow(
                "US",
                "United States",
                50L,
                new java.math.BigDecimal("6200.00"),
                new java.math.BigDecimal("12000.00"),
                new java.math.BigDecimal("2500.00"),
                new java.math.BigDecimal("6.5")));
    when(reportsRepository.getCountrySummary()).thenReturn(expected);

    var result = reportsService.getCountrySalarySummary();

    assertEquals(expected, result);
    verify(reportsRepository).getCountrySummary();
  }
}
