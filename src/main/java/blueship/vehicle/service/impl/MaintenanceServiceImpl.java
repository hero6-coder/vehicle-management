package blueship.vehicle.service.impl;

import blueship.vehicle.common.ErrorCode;
import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.entity.Maintenance;
import blueship.vehicle.entity.Vehicle;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.repository.MaintenanceRepository;
import blueship.vehicle.service.MaintemanceService;
import blueship.vehicle.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintemanceService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  MaintenanceRepository maintenanceRepository;
  @Autowired
  VehicleService vehicleService;

  @Override
  public List<MaintenanceDto> getMaintenancesByVehicle(Vehicle vehicle) {
    List<Maintenance> maintenances = maintenanceRepository.findAllByVehicle(vehicle);
    List<MaintenanceDto> rtv = new ArrayList<>();
    if (maintenances != null) {
      maintenances.stream().forEach(maintenance -> {
        MaintenanceDto maintenanceDto = new MaintenanceDto();
        BeanUtils.copyProperties(maintenance, maintenanceDto);
        rtv.add(maintenanceDto);
      });
    }
    logger.info("MaintenanceServiceImpl#getMaintenancesByVehicle: vehicleId: [{}] --- return data size:[{}]", vehicle.getId(), rtv.size());
    return rtv;
  }

  @Override
  public MaintenanceDto getMaintenancesByIdAndVehicle(Integer maintenanceId, Vehicle vehicle) {
    Maintenance maintenance = maintenanceRepository.findByIdAndVehicle(maintenanceId, vehicle)
      .orElseThrow(() -> new VmException(null, ErrorCode.MAINTENANCE_NOT_EXIST,
        new StringBuilder("Maintenance does not exist --- Id: ").append(maintenanceId).append(" --- vehicleId: ").append(vehicle.getId())));
    // Copy to dto
    MaintenanceDto maintenanceDto = new MaintenanceDto();
    BeanUtils.copyProperties(maintenance, maintenanceDto);
    return maintenanceDto;
  }

  @Override
  public List<MaintenanceDto> getAllMaintenances() {
    List<Maintenance> maintenances = maintenanceRepository.findAll();
    List<MaintenanceDto> rtv = new ArrayList<>();
    if (maintenances != null) {
      maintenances.stream().forEach(maintenance -> {
        MaintenanceDto maintenanceDto = new MaintenanceDto();
        BeanUtils.copyProperties(maintenance, maintenanceDto);
        rtv.add(maintenanceDto);
      });
    }
    logger.info("MaintenanceServiceImpl#getAllMaintenances --- return data size:[{}]", rtv.size());
    return rtv;
  }

  @Override
  public MaintenanceDto saveMaintenance(MaintenanceDto maintenanceDto) {
    logger.info("MaintenanceServiceImpl#saveMaintenance --- Before save: MaintenanceDto: [{}]", maintenanceDto);
    Vehicle vehicle = vehicleService.getVehicleById(maintenanceDto.getVehicleId());
    try {
      Maintenance maintenance = new Maintenance();
      BeanUtils.copyProperties(maintenanceDto, maintenance);
      maintenance.setVehicle(vehicle);
      maintenance = maintenanceRepository.save(maintenance);
      BeanUtils.copyProperties(maintenance, maintenanceDto);
      logger.info("MaintenanceServiceImpl#saveMaintenance --- After save: MaintenanceDto: [{}]", maintenanceDto);
      return maintenanceDto;
    } catch (BeansException e) {
      logger.error(e.getMessage(), e);
      throw new VmException(null, ErrorCode.FAILED_PERSIST_DATA, new StringBuilder("Unable to persist Vehicle: ").append(maintenanceDto.toString()));
    }
  }

  @Override
  public void deleteMaintenance(Integer maintenanceId) {
    if (maintenanceId == null)
      throw new VmException(null, ErrorCode.INVALID_PARAMS, new StringBuilder("Delete Maintenance failed by input Id null"));
    maintenanceRepository.deleteById(maintenanceId);
  }
}
