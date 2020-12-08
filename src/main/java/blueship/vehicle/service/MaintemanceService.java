package blueship.vehicle.service;

import blueship.vehicle.dto.MaintenanceDto;

import java.util.List;

public interface MaintemanceService {
	List<MaintenanceDto> getMaintenancesByVehicle(Integer vehicleId);
	List<MaintenanceDto> getAllMaintenances();
	MaintenanceDto saveMaintenance(MaintenanceDto maintenanceDto);
	void deleteMaintenance(Integer maintenanceId);
}
