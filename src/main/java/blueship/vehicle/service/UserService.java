package blueship.vehicle.service;

import blueship.vehicle.dto.UserDto;

import java.util.List;

public interface UserService {
  List<UserDto> getActiveUser();

  UserDto saveUser(UserDto user);
}
