package pe.com.peruapps.vehiclesmaintenanceapi.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.peruapps.vehiclesmaintenanceapi.application.dto.command.UpdateMaintenanceCommand;
import pe.com.peruapps.vehiclesmaintenanceapi.application.out.MaintenanceRepositoryPort;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Id;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateMaintenanceUseCase Tests")
class UpdateMaintenanceUseCaseImplTest {

  @Mock private MaintenanceRepositoryPort maintenanceRepositoryPort;

  @InjectMocks private UpdateMaintenanceUseCaseImpl updateMaintenanceUseCase;

  @Test
  void givenValidCommandWhenExecuteThenMaintenanceIsUpdated() {
    // Given
    Long maintenanceId = 1L;
    UpdateMaintenanceCommand command =
        new UpdateMaintenanceCommand(
            "Replace brakes",
            LocalDate.now().plusDays(3),
            new BigDecimal("20000"),
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.NEEDS_ATTENTION,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.GOOD,
            Maintenance.Type.BRAKE_INSPECTION,
            "Replace front pads");

    Maintenance existing =
        Maintenance.builder()
            .id(new Id(maintenanceId))
            .vehicleId(new Id(1L))
            .scheduledDate(LocalDate.now())
            .build();
    when(maintenanceRepositoryPort.findById(maintenanceId)).thenReturn(existing);
    when(maintenanceRepositoryPort.update(any(Long.class), any(Maintenance.class)))
        .thenAnswer(invocation -> invocation.getArgument(1));

    // When
    Maintenance result = updateMaintenanceUseCase.execute(maintenanceId, command);

    // Then
    assertThat(result.getDescription()).isEqualTo("Replace brakes");
    assertThat(result.getScheduledDate()).isEqualTo(command.scheduledDate());
    assertThat(result.getKilometersAtMaintenance()).isEqualByComparingTo(new BigDecimal("20000"));
    assertThat(result.getEngineStatus()).isEqualTo(Maintenance.RevisionStatus.GOOD);
    assertThat(result.getBrakesStatus()).isEqualTo(Maintenance.RevisionStatus.NEEDS_ATTENTION);
    assertThat(result.getTiresStatus()).isEqualTo(Maintenance.RevisionStatus.GOOD);
    assertThat(result.getTransmissionStatus()).isEqualTo(Maintenance.RevisionStatus.GOOD);
    assertThat(result.getElectricalStatus()).isEqualTo(Maintenance.RevisionStatus.GOOD);
    assertThat(result.getType()).isEqualTo(Maintenance.Type.BRAKE_INSPECTION);
    assertThat(result.getComments()).isEqualTo("Replace front pads");

    verify(maintenanceRepositoryPort).findById(maintenanceId);
    verify(maintenanceRepositoryPort).update(maintenanceId, existing);
  }
}
