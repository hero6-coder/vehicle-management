package blueship.vehicle.controller;

import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.service.VehicleService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/vehicles")
@Api(value = "vehicles", description = "")
public class VehicleController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  VehicleService vehicleService;

  @RequestMapping(value = "", method = RequestMethod.GET, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public List<VehicleDto> getVehiclesByUser(@RequestParam(name = "userId", required = false) Integer userId) {
    logger.info("VehicleController#getVehiclesByUser: {}", userId);
    return vehicleService.getVehiclesByUser(userId);
  }

  @RequestMapping(value = "", method = RequestMethod.POST, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public VehicleDto createVehicle(@RequestBody @Validated VehicleDto vehicleDto) {
    logger.info("VehicleController#createVehicle --- vehicle: {}", vehicleDto.toString());
    return vehicleService.saveVehicle(vehicleDto);
  }
}
