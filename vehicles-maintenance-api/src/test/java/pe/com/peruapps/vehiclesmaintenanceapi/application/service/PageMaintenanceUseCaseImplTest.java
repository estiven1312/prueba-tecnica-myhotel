package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.MaintenanceQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Id;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PageMaintenanceUseCase Tests")
class PageMaintenanceUseCaseImplTest {

  @Mock private MaintenanceRepositoryPort maintenanceRepositoryPort;

  @InjectMocks private PageMaintenanceUseCaseImpl pageMaintenanceUseCase;

  @Test
  void givenFilterWhenExecuteThenReturnsPageResult() {
    // Given
    PaginatedFilter<MaintenanceQuery> filter =
        PaginatedFilter.<MaintenanceQuery>builder().page(0).size(5).build();

    Maintenance maintenance =
        Maintenance.builder()
            .description("Oil change")
            .scheduledDate(LocalDate.now())
            .vehicleId(new Id(1L))
            .build();
    PageResult<Maintenance> expectedPage =
        PageResult.<Maintenance>builder()
            .content(List.of(maintenance))
            .totalElements(1)
            .totalPages(1)
            .build();

    when(maintenanceRepositoryPort.filter(filter)).thenReturn(expectedPage);

    // When
    PageResult<Maintenance> result = pageMaintenanceUseCase.execute(filter);

    // Then
    assertThat(result).isSameAs(expectedPage);
    assertThat(result.getContent()).hasSize(1);

    verify(maintenanceRepositoryPort).filter(filter);
  }
}
