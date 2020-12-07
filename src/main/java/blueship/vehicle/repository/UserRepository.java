package blueship.vehicle.repository;

import blueship.vehicle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByStatus(Integer status);
//    List<User> getUsersByActiveAndHolidayDateAfter(boolean active, LocalDate date);
}


