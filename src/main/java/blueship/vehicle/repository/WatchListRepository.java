package blueship.vehicle.repository;

import blueship.vehicle.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Integer> {
    List<WatchList> findAllByTicker(String ticker);
}


