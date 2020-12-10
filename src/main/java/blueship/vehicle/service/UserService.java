package blueship.vehicle.service;

import blueship.vehicle.dto.UserDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.entity.User;

import java.util.List;

public interface UserService {
  User getUserById(Integer userId);

  List<UserDto> getActiveUser();

  List<VehicleDto> getVehiclesByUser(Integer userId);

  UserDto saveUser(UserDto user);
}
