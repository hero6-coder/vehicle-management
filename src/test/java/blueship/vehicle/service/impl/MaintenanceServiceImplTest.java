package blueship.vehicle.service.impl;

import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.entity.Maintenance;
import blueship.vehicle.entity.Vehicle;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.repository.MaintenanceRepository;
import blueship.vehicle.service.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaintenanceServiceImplTest {
  @InjectMocks
  private MaintenanceServiceImpl maintenanceService;
  @Mock
  private MaintenanceRepository maintenanceRepository;
  @Mock
  private VehicleService vehicleService;

  @Test
  void getMaintenancesByVehicle_ReturnEmpty() {
    Vehicle vehicle = new Vehicle();
    when(maintenanceRepository.findAllByVehicle(any(Vehicle.class))).thenReturn(Collections.emptyList());
    List<MaintenanceDto> maintenanceDtos = maintenanceService.getMaintenancesByVehicle(vehicle);
    verify(maintenanceRepository, times(1)).findAllByVehicle(any(Vehicle.class));
    Assertions.assertEquals(0, maintenanceDtos.size());
  }

  @Test
  void getMaintenancesByVehicle_ReturnList() {
    Vehicle vehicle = new Vehicle();
    vehicle.setId(10);
    List<Maintenance> maintenances = new ArrayList<>();
    Maintenance maintenance1 = new Maintenance();
    Maintenance maintenance2 = new Maintenance();
    maintenances.add(maintenance1);
    maintenances.add(maintenance2);

    when(maintenanceRepository.findAllByVehicle(vehicle)).thenReturn(maintenances);
    List<MaintenanceDto> maintenanceDtos = maintenanceService.getMaintenancesByVehicle(vehicle);
    verify(maintenanceRepository, times(1)).findAllByVehicle(any(Vehicle.class));
    Assertions.assertEquals(2, maintenanceDtos.size());
  }

  @Test
  void getMaintenancesByIdAndVehicle_NotFoundMaintenance() {
    Vehicle vehicle = new Vehicle();
    when(maintenanceRepository.findByIdAndVehicle(any(Integer.class), any(Vehicle.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(VmException.class,() -> maintenanceService.getMaintenancesByIdAndVehicle(100, vehicle));
    verify(maintenanceRepository, times(1)).findByIdAndVehicle(any(Integer.class), any(Vehicle.class));
  }

  @Test
  void getMaintenancesByIdAndVehicle_Success() {
    Vehicle vehicle = new Vehicle();
    Maintenance maintenance = new Maintenance();
    maintenance.setId(100);
    when(maintenanceRepository.findByIdAndVehicle(100, vehicle)).thenReturn(Optional.of(maintenance));
    MaintenanceDto maintenanceDto = maintenanceService.getMaintenancesByIdAndVehicle(100, vehicle);
    verify(maintenanceRepository, times(1)).findByIdAndVehicle(any(Integer.class), any(Vehicle.class));
    Assertions.assertEquals(100, maintenanceDto.getId());
  }
  
  @Test
  void getAllMaintenances_EmptyList() {
    when(maintenanceRepository.findAll()).thenReturn(Collections.emptyList());
    List<MaintenanceDto> maintenanceDtos = maintenanceService.getAllMaintenances();
    verify(maintenanceRepository, times(1)).findAll();
    Assertions.assertEquals(0, maintenanceDtos.size());
  }

  @Test
  void getAllMaintenances_ReturnList() {
    List<Maintenance> maintenances = new ArrayList<>();
    Maintenance maintenance1 = new Maintenance();
    Maintenance maintenance2 = new Maintenance();
    maintenances.add(maintenance1);
    maintenances.add(maintenance2);

    when(maintenanceRepository.findAll()).thenReturn(maintenances);
    List<MaintenanceDto> maintenanceDtos = maintenanceService.getAllMaintenances();
    verify(maintenanceRepository, times(1)).findAll();
    Assertions.assertEquals(2, maintenanceDtos.size());
  }


  @Test
  void saveMaintenance_Success() {
    Vehicle vehicle = new Vehicle();
    vehicle.setId(10);
    MaintenanceDto maintenanceDto = new MaintenanceDto();
    maintenanceDto.setId(100);
    maintenanceDto.setVehicleId(10);
    when(vehicleService.getVehicleById(10)).thenReturn(vehicle);
    when(maintenanceRepository.save(any(Maintenance.class))).thenAnswer(i -> i.getArguments()[0]);
    maintenanceDto = maintenanceService.saveMaintenance(maintenanceDto);
    verify(vehicleService, times(1)).getVehicleById(10);
    verify(maintenanceRepository, times(1)).save(any(Maintenance.class));
    Assertions.assertEquals(100, maintenanceDto.getId());
  }

  @Test
  void saveMaintenance_InvalidConstraint() {
    Vehicle vehicle = new Vehicle();
    vehicle.setId(10);
    MaintenanceDto maintenanceDto = new MaintenanceDto();
    maintenanceDto.setId(100);
    maintenanceDto.setVehicleId(10);
    when(vehicleService.getVehicleById(10)).thenReturn(vehicle);
    when(maintenanceRepository.save(any(Maintenance.class))).thenThrow(DataIntegrityViolationException.class);
    Assertions.assertThrows(VmException.class,() -> maintenanceService.saveMaintenance(maintenanceDto));
    verify(vehicleService, times(1)).getVehicleById(10);
    verify(maintenanceRepository, times(1)).save(any(Maintenance.class));
  }

  @Test
  void deleteMaintenance_IdNull() {
    Assertions.assertThrows(VmException.class,() -> maintenanceService.deleteMaintenance(null));
  }

  @Test
  void deleteMaintenance_Success() {
    doNothing().when(maintenanceRepository).deleteById(any(Integer.class));
    maintenanceService.deleteMaintenance(100);
    verify(maintenanceRepository, times(1)).deleteById(any(Integer.class));
  }
}