package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Id;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.IllegalChangeStatusForMaintenanceException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateMaintenanceStatusUseCase Tests")
class UpdateMaintenanceStatusUseCaseImplTest {

  @Mock private MaintenanceRepositoryPort maintenanceRepositoryPort;

  @InjectMocks private UpdateMaintenanceStatusUseCaseImpl updateMaintenanceStatusUseCase;

  private Maintenance scheduledMaintenance;
  private Maintenance inProgressMaintenance;
  private Maintenance completedMaintenance;

  @BeforeEach
  void setUp() {
    scheduledMaintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.SCHEDULED)
            .build();

    inProgressMaintenance =
        Maintenance.builder()
            .id(new Id(2L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.IN_PROGRESS)
            .build();

    completedMaintenance =
        Maintenance.builder()
            .id(new Id(3L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.COMPLETED)
            .build();
  }

  @Test
  void givenScheduledMaintenanceWhenUpdateToInProgressThenStatusIsUpdated() {
    // Given
    when(maintenanceRepositoryPort.findById(1L)).thenReturn(scheduledMaintenance);
    when(maintenanceRepositoryPort.save(any(Maintenance.class))).thenReturn(scheduledMaintenance);

    // When
    updateMaintenanceStatusUseCase.execute(1L, Maintenance.Status.IN_PROGRESS);

    // Then
    assertThat(scheduledMaintenance.getStatus()).isEqualTo(Maintenance.Status.IN_PROGRESS);
    assertThat(scheduledMaintenance.getStartDateTime()).isNotNull();

    verify(maintenanceRepositoryPort).findById(1L);
    verify(maintenanceRepositoryPort).save(scheduledMaintenance);
  }

  @Test
  void givenInProgressMaintenanceWhenUpdateToCompletedThenStatusIsUpdated() {
    // Given
    when(maintenanceRepositoryPort.findById(2L)).thenReturn(inProgressMaintenance);
    when(maintenanceRepositoryPort.save(any(Maintenance.class))).thenReturn(inProgressMaintenance);

    // When
    updateMaintenanceStatusUseCase.execute(2L, Maintenance.Status.COMPLETED);

    // Then
    assertThat(inProgressMaintenance.getStatus()).isEqualTo(Maintenance.Status.COMPLETED);
    assertThat(inProgressMaintenance.getEndDateTime()).isNotNull();

    verify(maintenanceRepositoryPort).findById(2L);
    verify(maintenanceRepositoryPort).save(inProgressMaintenance);
  }

  @Test
  void givenScheduledMaintenanceWhenUpdateToCanceledThenStatusIsUpdated() {
    // Given
    when(maintenanceRepositoryPort.findById(1L)).thenReturn(scheduledMaintenance);
    when(maintenanceRepositoryPort.save(any(Maintenance.class))).thenReturn(scheduledMaintenance);

    // When
    updateMaintenanceStatusUseCase.execute(1L, Maintenance.Status.CANCELED);

    // Then
    assertThat(scheduledMaintenance.getStatus()).isEqualTo(Maintenance.Status.CANCELED);
    assertThat(scheduledMaintenance.getEndDateTime()).isNotNull();

    verify(maintenanceRepositoryPort).findById(1L);
    verify(maintenanceRepositoryPort).save(scheduledMaintenance);
  }

  @Test
  void givenAnyMaintenanceWhenUpdateToScheduledThenThrowsIllegalStateException() {
    // Given
    when(maintenanceRepositoryPort.findById(1L)).thenReturn(scheduledMaintenance);

    // When & Then
    assertThatThrownBy(
            () -> updateMaintenanceStatusUseCase.execute(1L, Maintenance.Status.SCHEDULED))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Cannot manually set status to SCHEDULED");

    verify(maintenanceRepositoryPort).findById(1L);
    verify(maintenanceRepositoryPort, never()).save(any());
  }

  @Test
  void givenInProgressMaintenanceWhenUpdateToInProgressThenThrowsIllegalChangeStatusException() {
    // Given
    when(maintenanceRepositoryPort.findById(2L)).thenReturn(inProgressMaintenance);

    // When & Then
    assertThatThrownBy(
            () -> updateMaintenanceStatusUseCase.execute(2L, Maintenance.Status.IN_PROGRESS))
        .isInstanceOf(IllegalChangeStatusForMaintenanceException.class)
        .hasMessageContaining("Maintenance can only be started if it is scheduled");

    verify(maintenanceRepositoryPort).findById(2L);
    verify(maintenanceRepositoryPort, never()).save(any());
  }

  @Test
  void givenScheduledMaintenanceWhenUpdateToCompletedThenThrowsIllegalChangeStatusException() {
    // Given
    when(maintenanceRepositoryPort.findById(1L)).thenReturn(scheduledMaintenance);

    // When & Then
    assertThatThrownBy(
            () -> updateMaintenanceStatusUseCase.execute(1L, Maintenance.Status.COMPLETED))
        .isInstanceOf(IllegalChangeStatusForMaintenanceException.class)
        .hasMessageContaining("Maintenance can only be completed if it is in progress");

    verify(maintenanceRepositoryPort).findById(1L);
    verify(maintenanceRepositoryPort, never()).save(any());
  }

  @Test
  @DisplayName(
      "givenCompletedMaintenance_whenUpdateToCanceled_thenThrowsIllegalChangeStatusException")
  void givenCompletedMaintenanceWhenUpdateToCanceledThenThrowsIllegalChangeStatusException() {
    // Given
    when(maintenanceRepositoryPort.findById(3L)).thenReturn(completedMaintenance);

    // When & Then
    assertThatThrownBy(
            () -> updateMaintenanceStatusUseCase.execute(3L, Maintenance.Status.CANCELED))
        .isInstanceOf(IllegalChangeStatusForMaintenanceException.class)
        .hasMessageContaining("Completed maintenance cannot be canceled");

    verify(maintenanceRepositoryPort).findById(3L);
    verify(maintenanceRepositoryPort, never()).save(any());
  }
}
