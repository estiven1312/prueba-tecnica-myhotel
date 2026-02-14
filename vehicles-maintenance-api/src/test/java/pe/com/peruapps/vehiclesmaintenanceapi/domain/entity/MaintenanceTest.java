package pe.com.peruapps.vehiclesmaintenanceapi.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.error.IllegalChangeStatusForMaintenanceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Maintenance Entity Tests")
class MaintenanceTest {

  @Test
  void givenValidMaintenanceDataWhenBuildMaintenanceThenMaintenanceIsCreatedSuccessfully() {
    // Given
    Id id = new Id(1L);
    Id vehicleId = new Id(10L);
    String description = "Oil change and filter replacement";
    LocalDate scheduledDate = LocalDate.now().plusDays(5);

    // When
    Maintenance maintenance =
        Maintenance.builder()
            .id(id)
            .vehicleId(vehicleId)
            .description(description)
            .scheduledDate(scheduledDate)
            .type(Maintenance.Type.OIL_CHANGE)
            .status(Maintenance.Status.SCHEDULED)
            .kilometersAtMaintenance(new BigDecimal("15000"))
            .comments("Regular maintenance")
            .build();

    // Then
    assertThat(maintenance).isNotNull();
    assertThat(maintenance.getId()).isEqualTo(id);
    assertThat(maintenance.getVehicleId()).isEqualTo(vehicleId);
    assertThat(maintenance.getDescription()).isEqualTo(description);
    assertThat(maintenance.getScheduledDate()).isEqualTo(scheduledDate);
    assertThat(maintenance.getType()).isEqualTo(Maintenance.Type.OIL_CHANGE);
    assertThat(maintenance.getStatus()).isEqualTo(Maintenance.Status.SCHEDULED);
    assertThat(maintenance.getKilometersAtMaintenance())
        .isEqualByComparingTo(new BigDecimal("15000"));
    assertThat(maintenance.getComments()).isEqualTo("Regular maintenance");
  }

  @Test
  void givenNullVehicleIdWhenBuildMaintenanceThenThrowsNullPointerException() {
    // Given
    LocalDate scheduledDate = LocalDate.now().plusDays(5);

    // When & Then
    assertThatThrownBy(
            () -> Maintenance.builder().vehicleId(null).scheduledDate(scheduledDate).build())
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void givenPastScheduledDateWhenBuildMaintenanceThenThrowsIllegalArgumentException() {
    // Given
    Id vehicleId = new Id(10L);
    LocalDate pastDate = LocalDate.now().minusDays(1);

    // When & Then
    assertThatThrownBy(
            () -> Maintenance.builder().vehicleId(vehicleId).scheduledDate(pastDate).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Scheduled date cannot be in the past");
  }

  @Test
  void givenScheduledMaintenanceWhenStartMaintenanceThenStatusChangesToInProgress() {
    // Given
    Maintenance maintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.SCHEDULED)
            .build();

    // When
    maintenance.startMaintenance();

    // Then
    assertThat(maintenance.getStatus()).isEqualTo(Maintenance.Status.IN_PROGRESS);
    assertThat(maintenance.getStartDateTime()).isNotNull();
    assertThat(maintenance.getStartDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
  }

  @Test
  void givenInProgressMaintenanceWhenStartMaintenanceThenThrowsIllegalChangeStatusException() {
    // Given
    Maintenance maintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.IN_PROGRESS)
            .build();

    // When & Then
    assertThatThrownBy(maintenance::startMaintenance)
        .isInstanceOf(IllegalChangeStatusForMaintenanceException.class)
        .hasMessageContaining("Maintenance can only be started if it is scheduled");
  }

  @Test
  void givenInProgressMaintenanceWhenCompleteMaintenanceThenStatusChangesToCompleted() {
    // Given
    Maintenance maintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.IN_PROGRESS)
            .startDateTime(LocalDateTime.now().minusHours(2))
            .build();

    // When
    maintenance.completeMaintenance();

    // Then
    assertThat(maintenance.getStatus()).isEqualTo(Maintenance.Status.COMPLETED);
    assertThat(maintenance.getEndDateTime()).isNotNull();
    assertThat(maintenance.getEndDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
  }

  @Test
  void givenScheduledMaintenanceWhenCompleteMaintenanceThenThrowsIllegalChangeStatusException() {
    // Given
    Maintenance maintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.SCHEDULED)
            .build();

    // When & Then
    assertThatThrownBy(maintenance::completeMaintenance)
        .isInstanceOf(IllegalChangeStatusForMaintenanceException.class)
        .hasMessageContaining("Maintenance can only be completed if it is in progress");
  }

  @Test
  void givenScheduledMaintenanceWhenCancelMaintenanceThenStatusChangesToCanceled() {
    // Given
    Maintenance maintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.SCHEDULED)
            .build();

    // When
    maintenance.cancelMaintenance();

    // Then
    assertThat(maintenance.getStatus()).isEqualTo(Maintenance.Status.CANCELED);
    assertThat(maintenance.getEndDateTime()).isNotNull();
  }

  @Test
  void givenCompletedMaintenanceWhenCancelMaintenanceThenThrowsIllegalChangeStatusException() {
    // Given
    Maintenance maintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.COMPLETED)
            .build();

    // When & Then
    assertThatThrownBy(maintenance::cancelMaintenance)
        .isInstanceOf(IllegalChangeStatusForMaintenanceException.class)
        .hasMessageContaining("Completed maintenance cannot be canceled");
  }

  @Test
  void givenScheduledMaintenanceWhenChangeScheduledDateThenDateIsUpdated() {
    // Given
    Maintenance maintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.SCHEDULED)
            .build();

    LocalDate newDate = LocalDate.now().plusDays(10);

    // When
    maintenance.changeScheduledDate(newDate);

    // Then
    assertThat(maintenance.getScheduledDate()).isEqualTo(newDate);
  }

  @Test
  void givenInProgressMaintenanceWhenChangeScheduledDateThenThrowsIllegalChangeStatusException() {
    // Given
    Maintenance maintenance =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .status(Maintenance.Status.IN_PROGRESS)
            .build();

    LocalDate newDate = LocalDate.now().plusDays(10);

    // When & Then
    assertThatThrownBy(() -> maintenance.changeScheduledDate(newDate))
        .isInstanceOf(IllegalChangeStatusForMaintenanceException.class)
        .hasMessageContaining("Scheduled date can only be changed if maintenance is scheduled");
  }

  @Test
  void givenTwoMaintenancesWithSameIdWhenCompareThenTheyAreEqual() {
    // Given
    Maintenance maintenance1 =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(10L))
            .scheduledDate(LocalDate.now().plusDays(1))
            .build();

    Maintenance maintenance2 =
        Maintenance.builder()
            .id(new Id(1L))
            .vehicleId(new Id(20L))
            .scheduledDate(LocalDate.now().plusDays(2))
            .build();

    // When & Then
    assertThat(maintenance1).isEqualTo(maintenance2);
    assertThat(maintenance1.hashCode()).isEqualTo(maintenance2.hashCode());
  }

  @Test
  void givenAllMaintenanceTypesWhenCreatedThenAllTypesAreAvailable() {
    // Given & When & Then
    assertThat(Maintenance.Type.values())
        .contains(
            Maintenance.Type.OIL_CHANGE,
            Maintenance.Type.TIRE_ROTATION,
            Maintenance.Type.BRAKE_INSPECTION,
            Maintenance.Type.ENGINE_TUNING,
            Maintenance.Type.OTHER);
  }

  @Test
  void givenAllMaintenanceStatusesWhenCreatedThenAllStatusesAreAvailable() {
    // Given & When & Then
    assertThat(Maintenance.Status.values())
        .contains(
            Maintenance.Status.SCHEDULED,
            Maintenance.Status.IN_PROGRESS,
            Maintenance.Status.COMPLETED,
            Maintenance.Status.CANCELED);
  }

  @Test
  void givenAllRevisionStatusesWhenCreatedThenAllStatusesAreAvailable() {
    // Given & When & Then
    assertThat(Maintenance.RevisionStatus.values())
        .contains(
            Maintenance.RevisionStatus.GOOD,
            Maintenance.RevisionStatus.NEEDS_ATTENTION,
            Maintenance.RevisionStatus.REPLACE);
  }
}
