package blueship.vehicle.service.impl;

import blueship.vehicle.common.ErrorCode;
import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.entity.User;
import blueship.vehicle.entity.Vehicle;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.repository.VehicleRepository;
import blueship.vehicle.service.MaintemanceService;
import blueship.vehicle.service.UserService;
import blueship.vehicle.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  VehicleRepository vehicleRepository;
  @Autowired
  UserService userService;
  @Autowired
  MaintemanceService maintemanceService;

  @Override
  public Vehicle getVehicleById(Integer vehicleId) {
    logger.info("VehicleServiceImpl#getVehicleById --- vehicleId: [{}]", vehicleId);
    return vehicleRepository.findById(vehicleId)
      .orElseThrow(() -> new VmException(null, ErrorCode.VEHICLE_NOT_EXIST, new StringBuilder("VehicleId does not exist: ").append(vehicleId)));
  }

  @Override
  public List<VehicleDto> getVehiclesByUser(User user) {
    List<Vehicle> vehicles = vehicleRepository.findAllByUser(user);
    List<VehicleDto> rtv = new ArrayList<>();
    if (vehicles != null) {
      vehicles.stream().forEach(vehicle -> {
        VehicleDto vehicleDto = new VehicleDto();
        BeanUtils.copyProperties(vehicle, vehicleDto);
        rtv.add(vehicleDto);
      });
    }
    logger.info("VehicleServiceImpl#getVehiclesByUser: userId: [{}] --- return data size:[{}]", user.getId(), rtv.size());
    return rtv;
  }

  @Override
  public List<MaintenanceDto> getMaintenancesByVehicle(Integer vehicleId) {
    Vehicle vehicle = vehicleRepository.findById(vehicleId)
      .orElseThrow(() -> new VmException(null, ErrorCode.VEHICLE_NOT_EXIST, new StringBuilder("VehicleId does not exist: ").append(vehicleId)));

    List<MaintenanceDto> maintenances = maintemanceService.getMaintenancesByVehicle(vehicle);
    logger.info("VehicleServiceImpl#getMaintenancesByVehicle: vehicleId: [{}] --- return data size:[{}]", vehicleId, maintenances.size());
    return maintenances;
  }

  @Override
  public MaintenanceDto getMaintenancesByVehicleAndId(Integer vehicleId, Integer maintenanceId) {
    Vehicle vehicle = vehicleRepository.findById(vehicleId)
      .orElseThrow(() -> new VmException(null, ErrorCode.VEHICLE_NOT_EXIST, new StringBuilder("VehicleId does not exist: ").append(vehicleId)));

    MaintenanceDto maintenance = maintemanceService.getMaintenancesByIdAndVehicle(maintenanceId, vehicle);
    logger.info("VehicleServiceImpl#getMaintenancesByVehicle: vehicleId: [{}] --- Maintenance:[{}]", vehicleId, maintenance);
    return maintenance;
  }

  @Override
  public List<VehicleDto> getAllVehicles() {
    List<Vehicle> vehicles = vehicleRepository.findAll();
    List<VehicleDto> rtv = new ArrayList<>();
    if (vehicles != null) {
      vehicles.stream().forEach(vehicle -> {
        VehicleDto vehicleDto = new VehicleDto();
        BeanUtils.copyProperties(vehicle, vehicleDto);
        rtv.add(vehicleDto);
      });
    }
    logger.info("VehicleServiceImpl#getAllVehicles --- return data size:[{}]", rtv.size());
    return rtv;
  }

  @Override
  public VehicleDto saveVehicle(VehicleDto vehicleDto) {
    logger.info("VehicleServiceImpl#saveVehicle --- Before save: VehicleDto: [{}]", vehicleDto);
    User user = userService.getUserById(vehicleDto.getUserId());
    try {
      Vehicle vehicle = new Vehicle();
      BeanUtils.copyProperties(vehicleDto, vehicle);
      vehicle.setUser(user);
      vehicle = vehicleRepository.save(vehicle);
      BeanUtils.copyProperties(vehicle, vehicleDto);
      logger.info("VehicleServiceImpl#saveVehicle --- After save: VehicleDto: [{}]", vehicleDto);
      return vehicleDto;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new VmException(null, ErrorCode.FAILED_PERSIST_DATA, new StringBuilder("Unable to persist Vehicle: ").append(vehicleDto.toString()));
    }
  }
}
