package blueship.vehicle.service;

import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.entity.User;
import blueship.vehicle.entity.Vehicle;

import java.util.List;

public interface VehicleService {
  Vehicle getVehicleById(Integer vehicleId);

  List<VehicleDto> getVehiclesByUser(User user);

  List<MaintenanceDto> getMaintenancesByVehicle(Integer vehicleId);

  MaintenanceDto getMaintenancesByVehicleAndId(Integer vehicleId, Integer maintenanceId);

  List<VehicleDto> getAllVehicles();

  VehicleDto saveVehicle(VehicleDto vehicleDto);
}
