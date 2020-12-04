package blueship.vehicle.controller;

import blueship.vehicle.dto.HolidayDto;
import blueship.vehicle.service.HolidayService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/holidays")
@Api(value = "holidays", description = "")
public class HolidayController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    HolidayService holidayService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    public List<HolidayDto> getActiveHoldays() {
        logger.info("HolidayController#getActiveHoldays");
        return holidayService.getActiveHoliday();
    }

    @RequestMapping(value = "/after", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    public List<HolidayDto> getActiveHolidaysAfter(LocalDate date) {
        if (date == null)
            date = LocalDate.now();
        logger.info("HolidayController#getActiveHolidaysAfter --- date: {}", date.toString());
        return holidayService.getActiveHolidayAfter(date);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE })
    public HolidayDto saveHoliday(@RequestBody HolidayDto holidayDto) {
        logger.info("HolidayController#saveHoliday --- date: {}", holidayDto.toString());
        return holidayService.saveHoliday(holidayDto);
    }
}
