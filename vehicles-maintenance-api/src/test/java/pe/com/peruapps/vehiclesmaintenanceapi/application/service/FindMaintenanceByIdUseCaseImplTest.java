package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Id;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.MaintenanceNotFoundException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindMaintenanceByIdUseCase Tests")
class FindMaintenanceByIdUseCaseImplTest {

  @Mock private MaintenanceRepositoryPort maintenanceRepositoryPort;

  @InjectMocks private FindMaintenanceByIdUseCaseImpl findMaintenanceByIdUseCase;

  @Test
  void givenExistingMaintenanceIdWhenExecuteThenMaintenanceIsReturned() {
    // Given
    Long maintenanceId = 1L;
    Maintenance expectedMaintenance =
        Maintenance.builder()
            .id(new Id(maintenanceId))
            .vehicleId(new Id(10L))
            .description("Oil change")
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.SCHEDULED)
            .build();

    when(maintenanceRepositoryPort.findById(maintenanceId)).thenReturn(expectedMaintenance);

    // When
    Maintenance result = findMaintenanceByIdUseCase.execute(maintenanceId);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId().value()).isEqualTo(maintenanceId);
    assertThat(result.getDescription()).isEqualTo("Oil change");
    assertThat(result.getStatus()).isEqualTo(Maintenance.Status.SCHEDULED);

    verify(maintenanceRepositoryPort).findById(maintenanceId);
  }

  @Test
  void givenNonExistingMaintenanceIdWhenExecuteThenThrowsMaintenanceNotFoundException() {
    // Given
    Long nonExistingId = 999L;
    when(maintenanceRepositoryPort.findById(nonExistingId))
        .thenThrow(new MaintenanceNotFoundException(nonExistingId, "Maintenance not found"));

    // When & Then
    assertThatThrownBy(() -> findMaintenanceByIdUseCase.execute(nonExistingId))
        .isInstanceOf(MaintenanceNotFoundException.class)
        .hasMessageContaining("Maintenance not found");

    verify(maintenanceRepositoryPort).findById(nonExistingId);
  }
}
