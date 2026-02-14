package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

public interface DeleteMaintenanceUseCase {
  void execute(Long id, String deletedBy);
}
