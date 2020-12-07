package blueship.vehicle.service;

import blueship.vehicle.dto.VehicleDto;

import java.util.List;

public interface VehicleService {
	List<VehicleDto> getVehiclesByUser(Integer userId);
	VehicleDto saveVehicle(VehicleDto vehicleDto);
	List<VehicleDto> getAllVehicles();
}
