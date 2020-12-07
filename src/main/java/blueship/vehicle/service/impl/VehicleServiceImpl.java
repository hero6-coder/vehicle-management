package blueship.vehicle.service.impl;

import blueship.vehicle.common.ErrorCode;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.entity.User;
import blueship.vehicle.entity.Vehicle;
import blueship.vehicle.exception.TcbsException;
import blueship.vehicle.repository.UserRepository;
import blueship.vehicle.repository.VehicleRepository;
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
    UserRepository userRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;

//    @Override
//    public List<UserDto> getActiveUser() {
//        List<User> users = vehicleRepository.findAllByStatus(1);
//        List<UserDto> rtv = new ArrayList<>();
//        if (users != null) {
//            users.stream().forEach(user -> {
//                UserDto userDto = new UserDto();
//                BeanUtils.copyProperties(user, userDto);
//                rtv.add(userDto);
//            });
//        }
//        logger.info("UserServiceImpl#getActiveUser --- return data size:{}", rtv.size());
//        return rtv;
//    }

    @Override
    public List<VehicleDto> getVehiclesByUser(Integer userId) {

        return null;
    }

    @Override
    public VehicleDto saveVehicle(VehicleDto vehicleDto) {
        logger.info("VehicleServiceImpl#saveVehicle --- Before save: VehicleDto: {}", vehicleDto);
        User user = userRepository.findById(vehicleDto.getUserId())
                .orElseThrow(() -> new TcbsException(null, ErrorCode.USER_NOT_EXIST, new StringBuilder("UserId does not exist: ").append(vehicleDto.getUserId())));
        Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleDto, vehicle);
        vehicle.setUser(user);
        vehicle = vehicleRepository.save(vehicle);
        BeanUtils.copyProperties(vehicle, vehicleDto);
        logger.info("VehicleServiceImpl#saveVehicle --- After save: VehicleDto: {}", vehicleDto);
        return vehicleDto;
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
        logger.info("VehicleServiceImpl#getAllVehicles --- return data size:{}", rtv.size());
        return rtv;
    }
}
