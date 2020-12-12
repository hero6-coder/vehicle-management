package blueship.vehicle.service.impl;

import blueship.vehicle.common.Constants;
import blueship.vehicle.common.ErrorCode;
import blueship.vehicle.dto.UserDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.entity.User;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.repository.UserRepository;
import blueship.vehicle.service.UserService;
import blueship.vehicle.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  UserRepository userRepository;
  @Autowired
  VehicleService vehicleService;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public User getUserById(Integer userId) {
    logger.info("UserServiceImpl#getUserById --- userId: [{}]", userId);
    return userRepository.findById(userId)
      .orElseThrow(() -> new VmException(null, ErrorCode.USER_NOT_EXIST, new StringBuilder("UserId does not exist: ").append(userId)));
  }

  @Override
  public List<UserDto> getActiveUser() {
    List<User> users = userRepository.findAllByStatus(Constants.ACTIVE);
    List<UserDto> rtv = new ArrayList<>();
    if (users != null) {
      users.stream().forEach(user -> {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        rtv.add(userDto);
      });
    }
    logger.info("UserServiceImpl#getActiveUser --- return data size:[{}]", rtv.size());
    return rtv;
  }

  @Override
  public List<VehicleDto> getVehiclesByUser(Integer userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new VmException(null, ErrorCode.USER_NOT_EXIST, new StringBuilder("UserId does not exist: ").append(userId)));

    List<VehicleDto> vehicles = vehicleService.getVehiclesByUser(user);
    logger.info("UserServiceImpl#getVehiclesByUser: userId: [{}] --- return data size:[{}]", userId, vehicles.size());
    return vehicles;
  }

  @Override
  public UserDto saveUser(UserDto userDto) {
    try {
      logger.info("UserServiceImpl#saveUser --- Before save: UserDto: [{}]", userDto);
      User user = new User();
      userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
      BeanUtils.copyProperties(userDto, user);
      user = userRepository.save(user);
      BeanUtils.copyProperties(user, userDto);
      logger.info("UserServiceImpl#saveUser --- After save: UserDto: [{}]", userDto);
      return userDto;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new VmException(null, ErrorCode.FAILED_PERSIST_DATA, new StringBuilder("Unable to persist user: ").append(userDto.toString()));
    }
  }
}
