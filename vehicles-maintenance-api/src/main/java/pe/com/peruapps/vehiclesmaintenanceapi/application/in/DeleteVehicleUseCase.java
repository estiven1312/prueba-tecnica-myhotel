package pe.com.peruapps.vehiclesmaintenanceapi.application.in;

public interface DeleteVehicleUseCase {
  void execute(Long id, String deletedBy);
}
