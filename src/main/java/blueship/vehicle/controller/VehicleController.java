package blueship.vehicle.controller;

import blueship.vehicle.dto.MaintenanceDto;
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
  public List<VehicleDto> getAllVehicles() {
    logger.info("VehicleController#getAllVehicles");
    return vehicleService.getAllVehicles();
  }

  @RequestMapping(value = "/{vehicleId}/maintenances", method = RequestMethod.GET, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public List<MaintenanceDto> getMaintenancesByVehicle(@PathVariable(name = "vehicleId") Integer vehicleId) {
    logger.info("VehicleController#getMaintenancesByVehicle: [{}]", vehicleId);
    return vehicleService.getMaintenancesByVehicle(vehicleId);
  }

  @RequestMapping(value = "/{vehicleId}/maintenances/{maintenanceId}", method = RequestMethod.GET, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public MaintenanceDto getMaintenanceByVehicleAndMaintenanceId(
    @PathVariable(name = "vehicleId") Integer vehicleId, @PathVariable(name = "maintenanceId") Integer maintenanceId) {
    logger.info("VehicleController#getMaintenanceByVehicleAndMaintenanceId: vehicleId: [{}] --- maintenanceId: [{}]", vehicleId, maintenanceId);
    return vehicleService.getMaintenancesByVehicleAndId(vehicleId, maintenanceId);
  }

  @RequestMapping(value = "", method = RequestMethod.POST, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public VehicleDto createVehicle(@RequestBody @Validated VehicleDto vehicleDto) {
    logger.info("VehicleController#createVehicle --- vehicle: [{}]", vehicleDto);
    return vehicleService.saveVehicle(vehicleDto);
  }
}
