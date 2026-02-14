package pe.com.peruapps.vehiclesmaintenanceapi.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Lorry Entity Tests")
class LorryTest {

  @Test
  void givenValidLorryData_whenCreateLorry_thenLorryIsCreatedSuccessfully() {
    // Given
    Lorry lorry = new Lorry();
    lorry.setId(new Id(1L));
    lorry.setBrand("Volvo");
    lorry.setModel("FH16");
    lorry.setLicensePlate("XYZ-789");
    lorry.setYear(2022L);
    lorry.setVehicleType(VehicleType.LORRY);
    lorry.setCubicCapacity(new BigDecimal("13000"));
    lorry.setMileage(new BigDecimal("50000"));
    lorry.setUrlPhoto("http://example.com/lorry.jpg");
    lorry.setType(Lorry.Type.TRAILER);
    lorry.setAxisNumber(6L);
    lorry.setTonnageCapacity(25L);

    // When & Then
    assertThat(lorry).isNotNull();
    assertThat(lorry.getId().value()).isEqualTo(1L);
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

  @Test
  void givenTwoLorriesWithSameId_whenCompare_thenTheyAreEqual() {
    // Given
    Lorry lorry1 = new Lorry();
    lorry1.setId(new Id(1L));
    lorry1.setBrand("Volvo");

    Lorry lorry2 = new Lorry();
    lorry2.setId(new Id(1L));
    lorry2.setBrand("Scania");

    // When & Then
    assertThat(lorry1).isEqualTo(lorry2);
    assertThat(lorry1.hashCode()).isEqualTo(lorry2.hashCode());
  }
}
