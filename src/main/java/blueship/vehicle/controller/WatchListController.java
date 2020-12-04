package blueship.vehicle.controller;

import blueship.vehicle.dto.WatchListDto;
import blueship.vehicle.service.WatchListService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/wls")
@Api(value = "watch list", description = "")
public class WatchListController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    WatchListService watchListService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    public List<WatchListDto> getWatchLists() {
        return watchListService.getAllWatchList();
    }
}
