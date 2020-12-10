package blueship.vehicle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class VehicleManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(VehicleManagementApplication.class, args);
    log.info("Application started!!!");
  }

}
