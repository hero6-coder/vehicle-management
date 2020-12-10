package blueship.vehicle.controller;

import blueship.vehicle.dto.MaintenanceDto;
import blueship.vehicle.service.MaintemanceService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/maintenances")
@Api(value = "maintenances", description = "")
public class MaintenanceController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  MaintemanceService maintemanceService;

  @RequestMapping(value = "", method = RequestMethod.GET, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public List<MaintenanceDto> getAllMaintenances() {
    logger.info("VehicleController#getAllMaintenances");
    return maintemanceService.getAllMaintenances();
  }

  @RequestMapping(value = "", method = RequestMethod.POST, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public MaintenanceDto createMaintenance(@RequestBody @Validated MaintenanceDto maintenanceDto) {
    logger.info("MaintenanceController#createMaintenance --- maintenance: [{}]", maintenanceDto);
    return maintemanceService.saveMaintenance(maintenanceDto);
  }

  @RequestMapping(value = "/{maintenanceId}", method = RequestMethod.DELETE, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public void deleteMaintenance(@PathVariable Integer maintenanceId) {
    logger.info("MaintenanceController#deleteMaintenance --- maintenanceId: [{}]", maintenanceId);
    maintemanceService.deleteMaintenance(maintenanceId);
  }
}
