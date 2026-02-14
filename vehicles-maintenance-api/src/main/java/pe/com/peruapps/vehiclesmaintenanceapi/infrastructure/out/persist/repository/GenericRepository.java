package pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import pe.com.peruapps.vehiclesmaintenanceapi.infrastructure.out.persist.entities.BaseEntity;

import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

  default T softDelete(T entity, String deletedBy) {
    entity.softDelete(deletedBy);
    return save(entity);
  }

  default Optional<T> softDeleteById(Long id, String deletedBy) {
    return findById(id)
        .filter(entity -> !entity.isDeleted())
        .map(entity -> softDelete(entity, deletedBy));
  }

  default T restore(T entity) {
    entity.restore();
    return save(entity);
  }

  default Optional<T> restoreById(Long id) {
    return findById(id).filter(BaseEntity::isDeleted).map(this::restore);
  }

  default Optional<T> findByIdAndNotDeleted(Long id) {
    return findById(id).filter(entity -> !entity.isDeleted());
  }

  default boolean existsByIdAndNotDeleted(Long id) {
    return findByIdAndNotDeleted(id).isPresent();
  }
}
