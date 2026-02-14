package pe.com.peruapps.vehiclesmaintenanceapi.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Automobile Entity Tests")
class AutomobileTest {

  @Test
  void givenValidAutomobileDataWhenCreateAutomobileThenAutomobileIsCreatedSuccessfully() {
    // Given
    Automobile automobile = new Automobile();
    automobile.setId(new Id(1L));
    automobile.setBrand("Toyota");
    automobile.setModel("Corolla");
    automobile.setLicensePlate("ABC-123");
    automobile.setYear(2023L);
    automobile.setVehicleType(VehicleType.AUTOMOBILE);
    automobile.setCubicCapacity(new BigDecimal("1800"));
    automobile.setMileage(new BigDecimal("15000"));
    automobile.setUrlPhoto("http://example.com/photo.jpg");
    automobile.setType(Automobile.Type.SEDAN);
    automobile.setNumberOfDoors(4L);
    automobile.setPassengerCapacity(5L);
    automobile.setTrunkCapacity(450L);

    // When & Then
    assertThat(automobile).isNotNull();
    assertThat(automobile.getId().value()).isEqualTo(1L);
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
  void givenTwoAutomobilesWithSameIdWhenCompareThenTheyAreEqual() {
    // Given
    Automobile automobile1 = new Automobile();
    automobile1.setId(new Id(1L));
    automobile1.setBrand("Toyota");

    Automobile automobile2 = new Automobile();
    automobile2.setId(new Id(1L));
    automobile2.setBrand("Honda");

    // When & Then
    assertThat(automobile1).isEqualTo(automobile2);
    assertThat(automobile1.hashCode()).isEqualTo(automobile2.hashCode());
  }
}
