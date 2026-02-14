package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.VehicleNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteMaintenanceUseCase Tests")
class DeleteMaintenanceUseCaseImplTest {

  @Mock private MaintenanceRepositoryPort maintenanceRepositoryPort;

  @InjectMocks private DeleteMaintenanceUseCaseImpl deleteMaintenanceUseCase;

  @Test
  void givenIdAndDeletedBy_whenExecute_thenDeletesMaintenance() {
    // Given
    Long maintenanceId = 20L;
    String deletedBy = "tester";

    // When
    deleteMaintenanceUseCase.execute(maintenanceId, deletedBy);

    // Then
    verify(maintenanceRepositoryPort).deleteById(maintenanceId, deletedBy);
  }

  @Test
  void givenIdNonExistentAndDeletedByWhenExecuteThenThrowsVehicleNotFound() {
    // Given
    Long vehicleId = 120L;
    String deletedBy = "tester";
    VehicleNotFoundException exception =
            new VehicleNotFoundException(vehicleId, "Vehicle not found");

    doThrow(exception).when(maintenanceRepositoryPort).deleteById(vehicleId, deletedBy);

    // When & Then
    assertThatThrownBy(() -> deleteMaintenanceUseCase.execute(vehicleId, deletedBy))
            .isInstanceOf(VehicleNotFoundException.class)
            .hasMessageContaining("Vehicle not found");

    verify(maintenanceRepositoryPort).deleteById(vehicleId, deletedBy);
  }
}
