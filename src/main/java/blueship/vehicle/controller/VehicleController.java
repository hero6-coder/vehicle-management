package blueship.vehicle.controller;

import blueship.vehicle.dto.UserDto;
import blueship.vehicle.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/vehicles")
@Api(value = "vehicles", description = "")
public class VehicleController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public List<UserDto> getActiveUsers() {
        logger.info("UserController#getActiveUsers");
        return userService.getActiveUser();
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public UserDto saveUser(@RequestBody UserDto userDto) {
        logger.info("UserController#saveUser --- user: {}", userDto.toString());
        return userService.saveUser(userDto);
    }
}
