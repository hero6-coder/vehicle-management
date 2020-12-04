package blueship.vehicle.service.impl;

import blueship.vehicle.dto.WatchListDto;
import blueship.vehicle.entity.WatchList;
import blueship.vehicle.repository.WatchListRepository;
import blueship.vehicle.service.WatchListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WatchListServiceImpl implements WatchListService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    WatchListRepository watchListRepository;

    @Override
    public List<WatchListDto> getAllWatchList() {
        List<WatchList> wls = watchListRepository.findAll();
        List<WatchListDto> rtv = new ArrayList<>();
        if (wls != null) {
            wls.stream().forEach(watchList -> {
                WatchListDto watchListDto = new WatchListDto();
                BeanUtils.copyProperties(watchList, watchListDto);
                rtv.add(watchListDto);
            });
        }
        logger.info("WatchListServiceImpl#getAllWatchList --- return data size:{}", rtv.size());
        return rtv;
    }
}
