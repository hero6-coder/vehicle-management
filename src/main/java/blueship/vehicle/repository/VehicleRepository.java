package blueship.vehicle.repository;

import blueship.vehicle.entity.User;
import blueship.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
  Optional<Vehicle> findAllByEngineNumber(String engineNumber);
  List<Vehicle> findAllByUser(User user);
}


