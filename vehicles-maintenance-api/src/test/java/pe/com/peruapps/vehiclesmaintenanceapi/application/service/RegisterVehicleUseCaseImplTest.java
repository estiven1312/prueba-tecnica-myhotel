package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterAutomobileCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterLorryCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.*;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.DuplicatedLicensePlateInActiveVehicleException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegisterVehicleUseCase Tests")
class RegisterVehicleUseCaseImplTest {

  @Mock private VehicleRepositoryPort vehicleRepositoryPort;

  @InjectMocks private RegisterVehicleUseCaseImpl registerVehicleUseCase;

  private RegisterAutomobileCommand automobileCommand;
  private RegisterLorryCommand lorryCommand;

  @BeforeEach
  void setUp() {
    automobileCommand =
        new RegisterAutomobileCommand(
            "Toyota",
            "Corolla",
            "ABC-123",
            2023L,
            VehicleType.AUTOMOBILE,
            new BigDecimal("1800"),
            new BigDecimal("15000"),
            "http://example.com/photo.jpg",
            Automobile.Type.SEDAN,
            4L,
            5L,
            450L);

    lorryCommand =
        new RegisterLorryCommand(
            "Volvo",
            "FH16",
            "XYZ-789",
            2022L,
            VehicleType.LORRY,
            new BigDecimal("13000"),
            new BigDecimal("50000"),
            "http://example.com/lorry.jpg",
            Lorry.Type.TRAILER,
            6L,
            25L);
  }

  @Test
  void givenValidAutomobileCommand_whenExecute_thenAutomobileIsRegistered() {
    // Given
    Automobile savedAutomobile = new Automobile();
    savedAutomobile.setId(new Id(1L));
    savedAutomobile.setBrand("Toyota");
    savedAutomobile.setLicensePlate("ABC-123");

    when(vehicleRepositoryPort.findByLicensePlate(anyString())).thenReturn(Optional.empty());
    when(vehicleRepositoryPort.save(any(Automobile.class))).thenReturn(savedAutomobile);

    // When
    Vehicle result = registerVehicleUseCase.execute(automobileCommand);

    // Then
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(Automobile.class);
    assertThat(result.getBrand()).isEqualTo("Toyota");
    assertThat(result.getLicensePlate()).isEqualTo("ABC-123");

    verify(vehicleRepositoryPort).findByLicensePlate("ABC-123");
    verify(vehicleRepositoryPort).save(any(Automobile.class));
  }

  @Test
  void givenValidLorryCommand_whenExecute_thenLorryIsRegistered() {
    // Given
    Lorry savedLorry = new Lorry();
    savedLorry.setId(new Id(1L));
    savedLorry.setBrand("Volvo");
    savedLorry.setLicensePlate("XYZ-789");

    when(vehicleRepositoryPort.findByLicensePlate(anyString())).thenReturn(Optional.empty());
    when(vehicleRepositoryPort.save(any(Lorry.class))).thenReturn(savedLorry);

    // When
    Vehicle result = registerVehicleUseCase.execute(lorryCommand);

    // Then
    assertThat(result).isNotNull();
    assertThat(result).isInstanceOf(Lorry.class);
    assertThat(result.getBrand()).isEqualTo("Volvo");
    assertThat(result.getLicensePlate()).isEqualTo("XYZ-789");

    verify(vehicleRepositoryPort).findByLicensePlate("XYZ-789");
    verify(vehicleRepositoryPort).save(any(Lorry.class));
  }

  @Test
  void givenDuplicatedLicensePlate_whenExecute_thenThrowsDuplicatedLicensePlateException() {
    // Given
    Automobile existingVehicle = new Automobile();
    existingVehicle.setId(new Id(99L));
    existingVehicle.setLicensePlate("ABC-123");

    when(vehicleRepositoryPort.findByLicensePlate("ABC-123"))
        .thenReturn(Optional.of(existingVehicle));

    // When & Then
    assertThatThrownBy(() -> registerVehicleUseCase.execute(automobileCommand))
        .isInstanceOf(DuplicatedLicensePlateInActiveVehicleException.class)
        .hasMessageContaining("A vehicle with the same license plate already exists");

    verify(vehicleRepositoryPort).findByLicensePlate("ABC-123");
    verify(vehicleRepositoryPort, never()).save(any());
  }

  @Test
  void givenAutomobileCommand_whenExecute_thenAllFieldsAreMappedCorrectly() {
    // Given
    when(vehicleRepositoryPort.findByLicensePlate(anyString())).thenReturn(Optional.empty());
    when(vehicleRepositoryPort.save(any(Automobile.class)))
        .thenAnswer(
            invocation -> {
              Automobile automobile = invocation.getArgument(0);
              automobile.setId(new Id(1L));
              return automobile;
            });

    // When
    Vehicle result = registerVehicleUseCase.execute(automobileCommand);

    // Then
    assertThat(result).isInstanceOf(Automobile.class);
    Automobile automobile = (Automobile) result;

    assertThat(automobile.getBrand()).isEqualTo("Toyota");
    assertThat(automobile.getModel()).isEqualTo("Corolla");
    assertThat(automobile.getLicensePlate()).isEqualTo("ABC-123");
    assertThat(automobile.getYear()).isEqualTo(2023L);
    assertThat(automobile.getVehicleType()).isEqualTo(VehicleType.AUTOMOBILE);
    assertThat(automobile.getCubicCapacity()).isEqualByComparingTo(new BigDecimal("1800"));
    assertThat(automobile.getMileage()).isEqualByComparingTo(new BigDecimal("15000"));
    assertThat(automobile.getUrlPhoto()).isEqualTo("http://example.com/photo.jpg");
    assertThat(automobile.getType()).isEqualTo(Automobile.Type.SEDAN);
    assertThat(automobile.getNumberOfDoors()).isEqualTo(4L);
    assertThat(automobile.getPassengerCapacity()).isEqualTo(5L);
    assertThat(automobile.getTrunkCapacity()).isEqualTo(450L);
  }

  @Test
  void givenLorryCommand_whenExecute_thenAllFieldsAreMappedCorrectly() {
    // Given
    when(vehicleRepositoryPort.findByLicensePlate(anyString())).thenReturn(Optional.empty());
    when(vehicleRepositoryPort.save(any(Lorry.class)))
        .thenAnswer(
            invocation -> {
              Lorry lorry = invocation.getArgument(0);
              lorry.setId(new Id(1L));
              return lorry;
            });

    // When
    Vehicle result = registerVehicleUseCase.execute(lorryCommand);

    // Then
    assertThat(result).isInstanceOf(Lorry.class);
    Lorry lorry = (Lorry) result;

    assertThat(lorry.getBrand()).isEqualTo("Volvo");
    assertThat(lorry.getModel()).isEqualTo("FH16");
    assertThat(lorry.getLicensePlate()).isEqualTo("XYZ-789");
    assertThat(lorry.getYear()).isEqualTo(2022L);
    assertThat(lorry.getVehicleType()).isEqualTo(VehicleType.LORRY);
    assertThat(lorry.getCubicCapacity()).isEqualByComparingTo(new BigDecimal("13000"));
    assertThat(lorry.getMileage()).isEqualByComparingTo(new BigDecimal("50000"));
    assertThat(lorry.getUrlPhoto()).isEqualTo("http://example.com/lorry.jpg");
    assertThat(lorry.getType()).isEqualTo(Lorry.Type.TRAILER);
    assertThat(lorry.getAxisNumber()).isEqualTo(6L);
    assertThat(lorry.getTonnageCapacity()).isEqualTo(25L);
  }
}
