package blueship.vehicle.service;

import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.entity.Vehicle;

import java.util.List;

public interface MaintemanceService {
  List<MaintenanceDto> getMaintenancesByVehicle(Vehicle vehicle);

  MaintenanceDto getMaintenancesByIdAndVehicle(Integer maintenanceId, Vehicle vehicle);

  List<MaintenanceDto> getAllMaintenances();

  MaintenanceDto saveMaintenance(MaintenanceDto maintenanceDto);

  void deleteMaintenance(Integer maintenanceId);
}
