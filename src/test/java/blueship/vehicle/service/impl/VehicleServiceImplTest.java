package blueship.vehicle.service.impl;

import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.entity.User;
import blueship.vehicle.entity.Vehicle;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.repository.VehicleRepository;
import blueship.vehicle.service.MaintemanceService;
import blueship.vehicle.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {
  @InjectMocks
  private VehicleServiceImpl vehicleService;
  @Mock
  private VehicleRepository vehicleRepository;
  @Mock
  private UserService userService;
  @Mock
  private MaintemanceService maintemanceService;

  @Test
  void getVehicleById_NotFoundVehicle() {
    when(vehicleRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(VmException.class,() -> vehicleService.getVehicleById(1));
    verify(vehicleRepository, times(1)).findById(any(Integer.class));
  }

  @Test
  void getVehicleById_Success() {
    Vehicle vehicle = new Vehicle();
    vehicle.setId(10);
    when(vehicleRepository.findById(any(Integer.class))).thenReturn(Optional.of(vehicle));
    vehicle = vehicleService.getVehicleById(10);
    verify(vehicleRepository, times(1)).findById(any(Integer.class));
    Assertions.assertEquals(10, vehicle.getId());
  }

  @Test
  void getVehiclesByUser_ReturnEmpty() {
    User user = new User();
    when(vehicleRepository.findAllByUser(any(User.class))).thenReturn(notNull());
    List<VehicleDto> vehicleDtos = vehicleService.getVehiclesByUser(user);
    verify(vehicleRepository, times(1)).findAllByUser(any(User.class));
    Assertions.assertEquals(vehicleDtos.size(), 0);
  }

  @Test
  void getVehiclesByUser_ReturnList() {
    User user = new User();
    List<Vehicle> vehicles = new ArrayList<>();
    Vehicle vehicle1 = new Vehicle();
    Vehicle vehicle2 = new Vehicle();
    vehicles.add(vehicle1);
    vehicles.add(vehicle2);

    when(vehicleRepository.findAllByUser(any(User.class))).thenReturn(vehicles);
    List<VehicleDto> vehicleDtos = vehicleService.getVehiclesByUser(user);
    verify(vehicleRepository, times(1)).findAllByUser(any(User.class));
    Assertions.assertEquals(vehicleDtos.size(), 2);
  }

  @Test
  void getMaintenancesByVehicle_NotFoundVehicle() {
    when(vehicleRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(VmException.class,() -> vehicleService.getMaintenancesByVehicle(1));
    verify(vehicleRepository, times(1)).findById(any(Integer.class));
  }

  @Test
  void getMaintenancesByVehicle_Success() {
    Vehicle vehicle = new Vehicle();
    vehicle.setId(10);
    when(vehicleRepository.findById(any(Integer.class))).thenReturn(Optional.of(vehicle));
    when(maintemanceService.getMaintenancesByVehicle(any(Vehicle.class))).thenReturn(Collections.emptyList());
    List<MaintenanceDto> maintenanceDtos = vehicleService.getMaintenancesByVehicle(10);
    verify(vehicleRepository, times(1)).findById(any(Integer.class));
    verify(maintemanceService, times(1)).getMaintenancesByVehicle(any(Vehicle.class));
    Assertions.assertEquals(0, maintenanceDtos.size());
  }

  @Test
  void getMaintenancesByVehicle_ReturnList() {
    Vehicle vehicle = new Vehicle();
    vehicle.setId(10);
    List<MaintenanceDto> maintenances = new ArrayList<>();
    MaintenanceDto maintenance1 = new MaintenanceDto();
    MaintenanceDto maintenance2 = new MaintenanceDto();
    maintenances.add(maintenance1);
    maintenances.add(maintenance2);

    when(vehicleRepository.findById(any(Integer.class))).thenReturn(Optional.of(vehicle));
    when(maintemanceService.getMaintenancesByVehicle(any(Vehicle.class))).thenReturn(maintenances);
    List<MaintenanceDto> maintenanceDtos = vehicleService.getMaintenancesByVehicle(10);
    verify(vehicleRepository, times(1)).findById(any(Integer.class));
    verify(maintemanceService, times(1)).getMaintenancesByVehicle(any(Vehicle.class));
    Assertions.assertEquals(2, maintenanceDtos.size());
  }

  @Test
  void getMaintenancesByVehicleAndId_NotFoundVehicle() {
    when(vehicleRepository.findById(1)).thenReturn(Optional.empty());
    Assertions.assertThrows(VmException.class,() -> vehicleService.getMaintenancesByVehicleAndId(1, 2));
    verify(vehicleRepository, times(1)).findById(any(Integer.class));
  }

  @Test
  void saveVehicle() {
  }
}