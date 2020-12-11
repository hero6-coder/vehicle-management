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
import org.springframework.dao.DataIntegrityViolationException;

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
    when(vehicleRepository.findById(10)).thenReturn(Optional.of(vehicle));
    vehicle = vehicleService.getVehicleById(10);
    verify(vehicleRepository, times(1)).findById(any(Integer.class));
    Assertions.assertEquals(10, vehicle.getId());
  }

  @Test
  void getVehiclesByUser_EmptyList() {
    User user = new User();
    when(vehicleRepository.findAllByUser(any(User.class))).thenReturn(Collections.emptyList());
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

    when(vehicleRepository.findAllByUser(user)).thenReturn(vehicles);
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
    when(vehicleRepository.findById(10)).thenReturn(Optional.of(vehicle));
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

    when(vehicleRepository.findById(10)).thenReturn(Optional.of(vehicle));
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
  void getMaintenancesByVehicleAndId_Success() {
    MaintenanceDto maintenanceDto = new MaintenanceDto();
    maintenanceDto.setId(100);
    maintenanceDto.setVehicleId(10);
    Vehicle vehicle = new Vehicle();
    vehicle.setId(10);
    when(vehicleRepository.findById(10)).thenReturn(Optional.of(vehicle));
    when(maintemanceService.getMaintenancesByIdAndVehicle(100, vehicle)).thenReturn(maintenanceDto);
    MaintenanceDto maintenanceRet = vehicleService.getMaintenancesByVehicleAndId(10, 100);
    verify(vehicleRepository, times(1)).findById(any(Integer.class));
    verify(maintemanceService, times(1)).getMaintenancesByIdAndVehicle(any(Integer.class), any(Vehicle.class));
    Assertions.assertEquals(100, maintenanceRet.getId());
  }

  @Test
  void getAllVehicles_EmptyList() {
    when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());
    List<VehicleDto> vehicleDtos = vehicleService.getAllVehicles();
    verify(vehicleRepository, times(1)).findAll();
    Assertions.assertEquals(0, vehicleDtos.size());
  }

  @Test
  void getAllVehicles_ReturnList() {
    User user = new User();
    List<Vehicle> vehicles = new ArrayList<>();
    Vehicle vehicle1 = new Vehicle();
    Vehicle vehicle2 = new Vehicle();
    vehicles.add(vehicle1);
    vehicles.add(vehicle2);

    when(vehicleRepository.findAll()).thenReturn(vehicles);
    List<VehicleDto> vehicleDtos = vehicleService.getAllVehicles();
    verify(vehicleRepository, times(1)).findAll();
    Assertions.assertEquals(2, vehicleDtos.size());
  }

  @Test
  void saveVehicle_Success() {
    User user = new User();
    user.setId(5);
    VehicleDto vehicleDto = new VehicleDto();
    vehicleDto.setUserId(5);
    when(userService.getUserById(5)).thenReturn(user);
    when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArguments()[0]);
    vehicleDto = vehicleService.saveVehicle(vehicleDto);
    verify(userService, times(1)).getUserById(5);
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
  }

  @Test
  void saveVehicle_InvalidConstraint() {
    User user = new User();
    user.setId(5);
    VehicleDto vehicleDto = new VehicleDto();
    vehicleDto.setUserId(5);
    when(userService.getUserById(5)).thenReturn(user);
    when(vehicleRepository.save(any(Vehicle.class))).thenThrow(DataIntegrityViolationException.class);
    Assertions.assertThrows(VmException.class,() -> vehicleService.saveVehicle(vehicleDto));
    verify(userService, times(1)).getUserById(5);
    verify(vehicleRepository, times(1)).save(any(Vehicle.class));
  }
}