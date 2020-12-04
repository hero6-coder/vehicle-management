package blueship.vehicle.repository;

import blueship.vehicle.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
    List<Holiday> findAllByActive(boolean active);
    List<Holiday> getHolidayByActiveAndHolidayDateAfter(boolean active, LocalDate date);
}


