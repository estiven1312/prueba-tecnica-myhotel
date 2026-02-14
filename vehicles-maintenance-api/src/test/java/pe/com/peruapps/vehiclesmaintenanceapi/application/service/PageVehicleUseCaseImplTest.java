package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.query.VehicleQuery;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PageResult;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PageVehicleUseCase Tests")
class PageVehicleUseCaseImplTest {

  @Mock private VehicleRepositoryPort vehicleRepositoryPort;

  @InjectMocks private PageVehicleUseCaseImpl pageVehicleUseCase;

  @Test
  void givenFilterWhenExecuteThenReturnsPageResult() {
    // Given
    PaginatedFilter<VehicleQuery> filter =
        PaginatedFilter.<VehicleQuery>builder().page(0).size(10).build();

    Vehicle vehicle = new Automobile();
    PageResult<Vehicle> expectedPage =
        PageResult.<Vehicle>builder().content(List.of(vehicle)).totalElements(1).totalPages(1).build();

    when(vehicleRepositoryPort.paginate(filter)).thenReturn(expectedPage);

    // When
    PageResult<Vehicle> result = pageVehicleUseCase.execute(filter);

    // Then
    assertThat(result).isSameAs(expectedPage);
    assertThat(result.getContent()).hasSize(1);

    verify(vehicleRepositoryPort).paginate(filter);
  }
}
