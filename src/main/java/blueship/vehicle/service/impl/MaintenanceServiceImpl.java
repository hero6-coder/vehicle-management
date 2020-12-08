package blueship.vehicle.service.impl;

import blueship.vehicle.common.ErrorCode;
import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.entity.Maintenance;
import blueship.vehicle.entity.Vehicle;
import blueship.vehicle.exception.TcbsException;
import blueship.vehicle.repository.MaintenanceRepository;
import blueship.vehicle.repository.VehicleRepository;
import blueship.vehicle.service.MaintemanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintemanceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Override
    public List<MaintenanceDto> getMaintenancesByVehicle(Integer vehicleId) {
        List<Maintenance> maintenances;
        if (vehicleId != null) {
            Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new TcbsException(null, ErrorCode.VEHICLE_NOT_EXIST, new StringBuilder("VehicleId does not exist: ").append(vehicleId)));
            maintenances = maintenanceRepository.findAllByVehicle(vehicle);
        } else {
            maintenances = maintenanceRepository.findAll();
        }
        List<MaintenanceDto> rtv = new ArrayList<>();
        if (maintenances != null) {
            maintenances.stream().forEach(maintenance -> {
                MaintenanceDto maintenanceDto = new MaintenanceDto();
                BeanUtils.copyProperties(maintenance, maintenanceDto);
                rtv.add(maintenanceDto);
            });
        }
        logger.info("MaintenanceServiceImpl#getMaintenancesByVehicle: vehicleId: {} --- return data size:{}", vehicleId, rtv.size());
        return rtv;
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
        logger.info("MaintenanceServiceImpl#getAllMaintenances --- return data size:{}", rtv.size());
        return rtv;
    }

    @Override
    public MaintenanceDto saveMaintenance(MaintenanceDto maintenanceDto) {
        logger.info("MaintenanceServiceImpl#saveMaintenance --- Before save: MaintenanceDto: {}", maintenanceDto);
        Vehicle vehicle = vehicleRepository.findById(maintenanceDto.getVehicleId())
                .orElseThrow(() -> new TcbsException(null, ErrorCode.VEHICLE_NOT_EXIST, new StringBuilder("VehicleId does not exist: ").append(maintenanceDto.getVehicleId())));
        Maintenance maintenance = new Maintenance();
        BeanUtils.copyProperties(maintenanceDto, maintenance);
        maintenance.setVehicle(vehicle);
        maintenance = maintenanceRepository.save(maintenance);
        BeanUtils.copyProperties(maintenance, maintenanceDto);
        logger.info("MaintenanceServiceImpl#saveMaintenance --- After save: MaintenanceDto: {}", maintenanceDto);
        return maintenanceDto;
    }

    @Override
    public void deleteMaintenance(Integer maintenanceId) {
        if (maintenanceId == null)
            throw new TcbsException(null, ErrorCode.INVALID_PARAMS, new StringBuilder("Delete Maintenance failed by input Id null"));
        maintenanceRepository.deleteById(maintenanceId);
    }
}
