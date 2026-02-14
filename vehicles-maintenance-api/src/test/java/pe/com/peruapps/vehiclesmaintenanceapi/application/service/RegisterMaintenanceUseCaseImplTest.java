package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.RegisterMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.VehicleRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.*;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.VehicleNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegisterMaintenanceUseCase Tests")
class RegisterMaintenanceUseCaseImplTest {

  @Mock private MaintenanceRepositoryPort maintenanceRepositoryPort;

  @Mock private VehicleRepositoryPort vehicleRepositoryPort;

  @InjectMocks private RegisterMaintenanceUseCaseImpl registerMaintenanceUseCase;

  private RegisterMaintenanceCommand command;
  private Automobile vehicle;

  @BeforeEach
  void setUp() {
    vehicle = new Automobile();
    vehicle.setId(new Id(1L));
    vehicle.setBrand("Toyota");
    vehicle.setLicensePlate("ABC-123");

    command =
        new RegisterMaintenanceCommand(
            1L,
            "Oil change and filter replacement",
            LocalDate.now().plusDays(5),
            new BigDecimal("15000"),
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.NEEDS_ATTENTION,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.Type.OIL_CHANGE,
            "Regular maintenance");
  }

  @Test
  void givenValidCommandWhenExecuteThenMaintenanceIsRegistered() {
    // Given
    Maintenance savedMaintenance =
        Maintenance.builder()
            .id(new Id(100L))
            .vehicleId(new Id(1L))
            .description("Oil change and filter replacement")
            .scheduledDate(command.scheduledDate())
            .status(Maintenance.Status.SCHEDULED)
            .build();

    when(vehicleRepositoryPort.findById(1L)).thenReturn(vehicle);
    when(maintenanceRepositoryPort.save(any(Maintenance.class))).thenReturn(savedMaintenance);

    // When
    Maintenance result = registerMaintenanceUseCase.execute(command);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId().value()).isEqualTo(100L);
    assertThat(result.getVehicleId().value()).isEqualTo(1L);
    assertThat(result.getStatus()).isEqualTo(Maintenance.Status.SCHEDULED);

    verify(vehicleRepositoryPort).findById(1L);
    verify(maintenanceRepositoryPort).save(any(Maintenance.class));
  }

  @Test
  void givenNonExistingVehicleWhenExecuteThenThrowsVehicleNotFoundException() {
    // Given
    when(vehicleRepositoryPort.findById(1L))
        .thenThrow(new VehicleNotFoundException(1L, "Vehicle not found"));

    // When & Then
    assertThatThrownBy(() -> registerMaintenanceUseCase.execute(command))
        .isInstanceOf(VehicleNotFoundException.class)
        .hasMessageContaining("Vehicle not found");

    verify(vehicleRepositoryPort).findById(1L);
    verify(maintenanceRepositoryPort, never()).save(any());
  }

  @Test
  void givenValidCommandWhenExecuteThenAllFieldsAreMappedCorrectly() {
    // Given
    when(vehicleRepositoryPort.findById(1L)).thenReturn(vehicle);
    when(maintenanceRepositoryPort.save(any(Maintenance.class)))
        .thenAnswer(
            invocation -> {
              Maintenance maintenance = invocation.getArgument(0);
              maintenance.setId(new Id(100L));
              return maintenance;
            });

    // When
    Maintenance result = registerMaintenanceUseCase.execute(command);

    // Then
    assertThat(result.getVehicleId().value()).isEqualTo(1L);
    assertThat(result.getDescription()).isEqualTo("Oil change and filter replacement");
    assertThat(result.getScheduledDate()).isEqualTo(command.scheduledDate());
    assertThat(result.getKilometersAtMaintenance()).isEqualByComparingTo(new BigDecimal("15000"));
    assertThat(result.getEngineStatus()).isEqualTo(Maintenance.RevisionStatus.GOOD);
    assertThat(result.getBrakesStatus()).isEqualTo(Maintenance.RevisionStatus.GOOD);
    assertThat(result.getTiresStatus()).isEqualTo(Maintenance.RevisionStatus.NEEDS_ATTENTION);
    assertThat(result.getTransmissionStatus()).isEqualTo(Maintenance.RevisionStatus.GOOD);
    assertThat(result.getElectricalStatus()).isEqualTo(Maintenance.RevisionStatus.GOOD);
    assertThat(result.getType()).isEqualTo(Maintenance.Type.OIL_CHANGE);
    assertThat(result.getComments()).isEqualTo("Regular maintenance");
    assertThat(result.getStatus()).isEqualTo(Maintenance.Status.SCHEDULED);
  }

  @Test
  void givenPastScheduledDateWhenExecuteThenThrowsIllegalArgumentException() {
    // Given
    RegisterMaintenanceCommand invalidCommand =
        new RegisterMaintenanceCommand(
            1L,
            "Oil change",
            LocalDate.now().minusDays(1), // Past date
            new BigDecimal("15000"),
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.Type.OIL_CHANGE,
            "Regular maintenance");

    when(vehicleRepositoryPort.findById(1L)).thenReturn(vehicle);

    // When & Then
    assertThatThrownBy(() -> registerMaintenanceUseCase.execute(invalidCommand))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Scheduled date cannot be in the past");

    verify(vehicleRepositoryPort).findById(1L);
    verify(maintenanceRepositoryPort, never()).save(any());
  }
}
