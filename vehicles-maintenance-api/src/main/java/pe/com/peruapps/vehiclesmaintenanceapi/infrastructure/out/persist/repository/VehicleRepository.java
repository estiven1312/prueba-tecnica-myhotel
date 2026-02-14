package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.VehicleType;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.VehicleEntity;

import java.util.Optional;

@Repository
public interface VehicleRepository extends GenericRepository<VehicleEntity> {

  Optional<VehicleEntity> findByIdAndDeletedAtIsNull(Long id);

  @Query(
      "SELECT v FROM VehicleEntity v WHERE v.licensePlate = :licensePlate AND v.deletedAt IS NULL")
  Optional<VehicleEntity> findByLicensePlate(@Param("licensePlate") String licensePlate);

  @Query(
      """
        SELECT v FROM VehicleEntity v
        WHERE v.deletedAt IS NULL
        AND (:brand IS NULL OR LOWER(v.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
        AND (:model IS NULL OR LOWER(v.model) LIKE LOWER(CONCAT('%', :model, '%')))
        AND (:licensePlate IS NULL OR LOWER(v.licensePlate) = LOWER(:licensePlate))
        AND (:vehicleType IS NULL OR v.vehicleType = :vehicleType)
        AND (:fromYear IS NULL OR v.year >= :fromYear)
        AND (:toYear IS NULL OR v.year <= :toYear)
        """)
  Page<VehicleEntity> findByFilters(
      @Param("brand") String brand,
      @Param("model") String model,
      @Param("licensePlate") String licensePlate,
      @Param("vehicleType") VehicleType vehicleType,
      @Param("fromYear") Long fromYear,
      @Param("toYear") Long toYear,
      Pageable pageable);
}
