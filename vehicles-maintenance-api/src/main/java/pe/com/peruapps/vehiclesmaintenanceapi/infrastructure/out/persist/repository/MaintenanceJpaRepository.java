package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.peruapps.vehiclesmaintenanceapi.domain.entity.Maintenance;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.MaintenanceEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceJpaRepository
    extends JpaRepository<MaintenanceEntity, Long>, JpaSpecificationExecutor<MaintenanceEntity> {

  Optional<MaintenanceEntity> findByIdAndDeletedAtIsNull(Long id);

  @Query(
      """
        SELECT m FROM MaintenanceEntity m
        WHERE m.deletedAt IS NULL
        AND (:description IS NULL OR LOWER(m.description) LIKE LOWER(CONCAT('%', :description, '%')))
        AND (:fromDate IS NULL OR m.scheduledDate >= :fromDate)
        AND (:toDate IS NULL OR m.scheduledDate <= :toDate)
        AND (:vehicleId IS NULL OR m.vehicleId = :vehicleId)
        AND (:type IS NULL OR m.type = :type)
        AND (:status IS NULL OR m.status = :status)
        """)
  Page<MaintenanceEntity> findByFilters(
      @Param("description") String description,
      @Param("fromDate") LocalDate fromDate,
      @Param("toDate") LocalDate toDate,
      @Param("vehicleId") Long vehicleId,
      @Param("type") Maintenance.Type type,
      @Param("status") Maintenance.Status status,
      Pageable pageable);
}
