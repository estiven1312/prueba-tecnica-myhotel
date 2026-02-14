package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.VehicleNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteVehicleUseCase Tests")
class DeleteVehicleUseCaseImplTest {

  @Mock private VehicleRepositoryPort vehicleRepositoryPort;

  @InjectMocks private DeleteVehicleUseCaseImpl deleteVehicleUseCase;

  @Test
  void givenIdAndDeletedByWhenExecuteThenDeletesVehicle() {
    // Given
    Long vehicleId = 10L;
    String deletedBy = "tester";

    // When
    deleteVehicleUseCase.execute(vehicleId, deletedBy);

    // Then
    verify(vehicleRepositoryPort).deleteById(vehicleId, deletedBy);
  }

  @Test
  void givenIdNonExistentAndDeletedByWhenExecuteThenThrowsVehicleNotFound() {
    // Given
    Long vehicleId = 120L;
    String deletedBy = "tester";
    VehicleNotFoundException exception =
        new VehicleNotFoundException(vehicleId, "Vehicle not found");

    doThrow(exception).when(vehicleRepositoryPort).deleteById(vehicleId, deletedBy);

    // When & Then
    assertThatThrownBy(() -> deleteVehicleUseCase.execute(vehicleId, deletedBy))
        .isInstanceOf(VehicleNotFoundException.class)
        .hasMessageContaining("Vehicle not found");

    verify(vehicleRepositoryPort).deleteById(vehicleId, deletedBy);
  }
}
