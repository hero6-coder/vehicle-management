package blueship.vehicle;

import blueship.vehicle.controller.MaintenanceController;
import blueship.vehicle.controller.UserController;
import blueship.vehicle.controller.VehicleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VehicleManagementApplicationTests {
  @Autowired
  private UserController userController;
  @Autowired
  private VehicleController vehicleController;
  @Autowired
  private MaintenanceController maintenanceController;
  @Test
  void contextLoads() {
    assertThat(userController).isNotNull();
    assertThat(vehicleController).isNotNull();
    assertThat(maintenanceController).isNotNull();
  }

}
