package blueship.vehicle.repository;

import blueship.vehicle.entity.Maintenance;
import blueship.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {
  List<Maintenance> findAllByVehicle(Vehicle vehicle);

  Optional<Maintenance> findByIdAndVehicle(Integer maintenanceId, Vehicle vehicle);
}


