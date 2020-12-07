package blueship.vehicle.repository;

import blueship.vehicle.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {
//    List<Maintenance> findAllByActive(boolean active);
//    List<Maintenance> getMaintenancesByActiveAndHolidayDateAfter(boolean active, LocalDate date);
}


