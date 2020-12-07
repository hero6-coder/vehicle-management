package blueship.vehicle.service;

import blueship.vehicle.dto.MaintenanceDto;

import java.util.List;

public interface MaintemanceService {
	List<MaintenanceDto> getMaintenancesByVehicle(Integer vehicleId);
	MaintenanceDto saveMaintenance(MaintenanceDto maintenanceDto);
	List<MaintenanceDto> getAllMaintenances();
}
