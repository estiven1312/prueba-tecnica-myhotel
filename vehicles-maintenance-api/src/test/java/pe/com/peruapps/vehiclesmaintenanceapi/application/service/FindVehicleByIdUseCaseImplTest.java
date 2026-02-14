package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Automobile;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Id;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Vehicle;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.VehicleNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindVehicleByIdUseCase Tests")
class FindVehicleByIdUseCaseImplTest {

  @Mock private VehicleRepositoryPort vehicleRepositoryPort;

  @InjectMocks private FindVehicleByIdUseCaseImpl findVehicleByIdUseCase;

  @Test
  void givenExistingVehicleIdWhenExecuteThenVehicleIsReturned() {
    // Given
    Long vehicleId = 1L;
    Automobile expectedVehicle = new Automobile();
    expectedVehicle.setId(new Id(vehicleId));
    expectedVehicle.setBrand("Toyota");
    expectedVehicle.setModel("Corolla");
    expectedVehicle.setVehicleType(VehicleType.AUTOMOBILE);

    when(vehicleRepositoryPort.findById(vehicleId)).thenReturn(expectedVehicle);

    // When
    Vehicle result = findVehicleByIdUseCase.execute(vehicleId);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId().value()).isEqualTo(vehicleId);
    assertThat(result.getBrand()).isEqualTo("Toyota");
    assertThat(result.getModel()).isEqualTo("Corolla");

    verify(vehicleRepositoryPort).findById(vehicleId);
  }

  @Test
  void givenNonExistingVehicleIdWhenExecuteThenThrowsVehicleNotFoundException() {
    // Given
    Long nonExistingId = 999L;
    when(vehicleRepositoryPort.findById(nonExistingId))
        .thenThrow(new VehicleNotFoundException(nonExistingId, "Vehicle not found"));

    // When & Then
    assertThatThrownBy(() -> findVehicleByIdUseCase.execute(nonExistingId))
        .isInstanceOf(VehicleNotFoundException.class)
        .hasMessageContaining("Vehicle not found");

    verify(vehicleRepositoryPort).findById(nonExistingId);
  }
}
