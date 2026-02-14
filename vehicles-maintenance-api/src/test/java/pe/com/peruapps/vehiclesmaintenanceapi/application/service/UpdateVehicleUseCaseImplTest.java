package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateAutomobileCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateLorryCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.*;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.DuplicatedLicensePlateInActiveVehicleException;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.ForbiddenChangeVehicleTypeException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateVehicleUseCase Tests")
class UpdateVehicleUseCaseImplTest {

  @Mock private VehicleRepositoryPort vehicleRepositoryPort;

  @InjectMocks private UpdateVehicleUseCaseImpl updateVehicleUseCase;

  private Automobile existingAutomobile;
  private Lorry existingLorry;

  @BeforeEach
  void setUp() {
    existingAutomobile = new Automobile();
    existingAutomobile.setId(new Id(1L));
    existingAutomobile.setBrand("Toyota");
    existingAutomobile.setModel("Corolla");
    existingAutomobile.setLicensePlate("ABC-123");
    existingAutomobile.setYear(2023L);
    existingAutomobile.setVehicleType(VehicleType.AUTOMOBILE);
    existingAutomobile.setCubicCapacity(new BigDecimal("1800"));
    existingAutomobile.setMileage(new BigDecimal("15000"));
    existingAutomobile.setType(Automobile.Type.SEDAN);
    existingAutomobile.setNumberOfDoors(4L);
    existingAutomobile.setPassengerCapacity(5L);
    existingAutomobile.setTrunkCapacity(450L);

    existingLorry = new Lorry();
    existingLorry.setId(new Id(2L));
    existingLorry.setBrand("Volvo");
    existingLorry.setModel("FH16");
    existingLorry.setLicensePlate("XYZ-789");
    existingLorry.setYear(2022L);
    existingLorry.setVehicleType(VehicleType.LORRY);
    existingLorry.setCubicCapacity(new BigDecimal("13000"));
    existingLorry.setMileage(new BigDecimal("50000"));
    existingLorry.setType(Lorry.Type.TRAILER);
    existingLorry.setAxisNumber(6L);
    existingLorry.setTonnageCapacity(25L);
  }

  @Test
  void givenValidAutomobileUpdate_whenExecute_thenAutomobileIsUpdated() {
    // Given
    UpdateAutomobileCommand command =
        new UpdateAutomobileCommand(
            "Honda",
            "Civic",
            "ABC-123",
            2024L,
            VehicleType.AUTOMOBILE,
            new BigDecimal("2000"),
            new BigDecimal("20000"),
            "http://example.com/new-photo.jpg",
            Automobile.Type.SEDAN,
            4L,
            5L,
            500L);

    when(vehicleRepositoryPort.findById(1L)).thenReturn(existingAutomobile);
    when(vehicleRepositoryPort.update(any(Automobile.class))).thenReturn(existingAutomobile);

    // When
    Vehicle result = updateVehicleUseCase.execute(1L, command);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getBrand()).isEqualTo("Honda");
    assertThat(result.getModel()).isEqualTo("Civic");
    assertThat(result.getYear()).isEqualTo(2024L);

    verify(vehicleRepositoryPort).findById(1L);
    verify(vehicleRepositoryPort).update(any(Automobile.class));
  }

  @Test
  void givenValidLorryUpdate_whenExecute_thenLorryIsUpdated() {
    // Given
    UpdateLorryCommand command =
        new UpdateLorryCommand(
            "Scania",
            "R500",
            "XYZ-789",
            2023L,
            VehicleType.LORRY,
            new BigDecimal("14000"),
            new BigDecimal("60000"),
            "http://example.com/new-lorry.jpg",
            Lorry.Type.SEMI_TRAILER,
            8L,
            30L);

    when(vehicleRepositoryPort.findById(2L)).thenReturn(existingLorry);
    when(vehicleRepositoryPort.update(any(Lorry.class))).thenReturn(existingLorry);

    // When
    Vehicle result = updateVehicleUseCase.execute(2L, command);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getBrand()).isEqualTo("Scania");
    assertThat(result.getModel()).isEqualTo("R500");
    assertThat(result.getYear()).isEqualTo(2023L);

    verify(vehicleRepositoryPort).findById(2L);
    verify(vehicleRepositoryPort).update(any(Lorry.class));
  }

  @Test
  void givenDifferentVehicleType_whenExecute_thenThrowsForbiddenChangeVehicleTypeException() {
    // Given
    UpdateLorryCommand command =
        new UpdateLorryCommand(
            "Volvo",
            "FH16",
            "ABC-123",
            2023L,
            VehicleType.LORRY,
            new BigDecimal("13000"),
            new BigDecimal("50000"),
            "http://example.com/lorry.jpg",
            Lorry.Type.TRAILER,
            6L,
            25L);

    when(vehicleRepositoryPort.findById(1L)).thenReturn(existingAutomobile);

    // When & Then
    assertThatThrownBy(() -> updateVehicleUseCase.execute(1L, command))
        .isInstanceOf(ForbiddenChangeVehicleTypeException.class)
        .hasMessageContaining("Cannot change vehicle type");

    verify(vehicleRepositoryPort).findById(1L);
    verify(vehicleRepositoryPort, never()).update(any());
  }

  @Test
  void givenDuplicatedLicensePlate_whenExecute_thenThrowsDuplicatedLicensePlateException() {
    // Given
    UpdateAutomobileCommand command =
        new UpdateAutomobileCommand(
            "Toyota",
            "Corolla",
            "DEF-456", // Different license plate
            2023L,
            VehicleType.AUTOMOBILE,
            new BigDecimal("1800"),
            new BigDecimal("15000"),
            "http://example.com/photo.jpg",
            Automobile.Type.SEDAN,
            4L,
            5L,
            450L);

    Automobile anotherVehicle = new Automobile();
    anotherVehicle.setId(new Id(99L));
    anotherVehicle.setLicensePlate("DEF-456");

    when(vehicleRepositoryPort.findById(1L)).thenReturn(existingAutomobile);
    when(vehicleRepositoryPort.findByLicensePlate("DEF-456"))
        .thenReturn(Optional.of(anotherVehicle));

    // When & Then
    assertThatThrownBy(() -> updateVehicleUseCase.execute(1L, command))
        .isInstanceOf(DuplicatedLicensePlateInActiveVehicleException.class)
        .hasMessageContaining("License plate 'DEF-456' is already registered");

    verify(vehicleRepositoryPort).findById(1L);
    verify(vehicleRepositoryPort).findByLicensePlate("DEF-456");
    verify(vehicleRepositoryPort, never()).update(any());
  }

  @Test
  void givenSameLicensePlate_whenExecute_thenVehicleIsUpdatedWithoutValidation() {
    // Given
    UpdateAutomobileCommand command =
        new UpdateAutomobileCommand(
            "Toyota",
            "Corolla Updated",
            "ABC-123", // Same license plate
            2023L,
            VehicleType.AUTOMOBILE,
            new BigDecimal("1800"),
            new BigDecimal("20000"),
            "http://example.com/photo.jpg",
            Automobile.Type.SEDAN,
            4L,
            5L,
            450L);

    when(vehicleRepositoryPort.findById(1L)).thenReturn(existingAutomobile);
    when(vehicleRepositoryPort.update(any(Automobile.class))).thenReturn(existingAutomobile);

    // When
    Vehicle result = updateVehicleUseCase.execute(1L, command);

    // Then
    assertThat(result).isNotNull();
    verify(vehicleRepositoryPort).findById(1L);
    verify(vehicleRepositoryPort, never()).findByLicensePlate(anyString());
    verify(vehicleRepositoryPort).update(any(Automobile.class));
  }
}
