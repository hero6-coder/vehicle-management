package blueship.vehicle.repository;

import blueship.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
//    List<Vehicle> findAllByActive(boolean active);
//    List<Vehicle> getVehiclesByActiveAndHolidayDateAfter(boolean active, LocalDate date);
}


