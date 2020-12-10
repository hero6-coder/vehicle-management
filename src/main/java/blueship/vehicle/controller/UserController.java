package blueship.vehicle.controller;

import blueship.vehicle.dto.UserDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  UserService userService;

  @RequestMapping(value = "", method = RequestMethod.GET, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public List<UserDto> getActiveUsers() {
    logger.info("UserController#getActiveUsers");
    return userService.getActiveUser();
  }

  @RequestMapping(value = "/{userId}/vehicles", method = RequestMethod.GET, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public List<VehicleDto> getVehiclesByUser(@PathVariable(name = "userId") Integer userId) {
    logger.info("UserController#getVehiclesByUser: [{}]", userId);
    return userService.getVehiclesByUser(userId);
  }

  @RequestMapping(value = "", method = RequestMethod.POST, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public UserDto saveUser(@RequestBody UserDto userDto) {
    logger.info("UserController#saveUser --- user: [{}]", userDto);
    return userService.saveUser(userDto);
  }
}
