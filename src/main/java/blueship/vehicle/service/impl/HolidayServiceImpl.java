package blueship.vehicle.service.impl;

import blueship.vehicle.dto.HolidayDto;
import blueship.vehicle.entity.Holiday;
import blueship.vehicle.repository.HolidayRepository;
import blueship.vehicle.service.HolidayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    HolidayRepository holidayRepository;

    @Override
    public List<HolidayDto> getActiveHoliday() {
        List<Holiday> holidays = holidayRepository.findAllByActive(true);
        List<HolidayDto> rtv = new ArrayList<>();
        if (holidays != null) {
            holidays.stream().forEach(holiday -> {
                HolidayDto holidayDto = new HolidayDto();
                BeanUtils.copyProperties(holiday, holidayDto);
                rtv.add(holidayDto);
            });
        }
        logger.info("HolidayServiceImpl#getActiveHoliday --- return data size:{}", rtv.size());
        return rtv;
    }

    @Override
    public List<HolidayDto> getActiveHolidayAfter(LocalDate date) {
        List<Holiday> holidays = holidayRepository.getHolidayByActiveAndHolidayDateAfter(true, date);
        List<HolidayDto> rtv = new ArrayList<>();
        if (holidays != null) {
            holidays.stream().forEach(holiday -> {
                HolidayDto holidayDto = new HolidayDto();
                BeanUtils.copyProperties(holiday, holidayDto);
                rtv.add(holidayDto);
            });
        }
        logger.info("HolidayServiceImpl#getActiveHolidayAfter --- Date: {} --- return data size:{}", date.toString(), rtv.size());
        return rtv;
    }

    @Override
    public HolidayDto saveHoliday(HolidayDto holidayDto) {
        logger.info("HolidayServiceImpl#saveHoliday --- Before save: holidayDto: {}", holidayDto);
        Holiday holiday = new Holiday();
        BeanUtils.copyProperties(holidayDto, holiday);
        holiday = holidayRepository.save(holiday);
        BeanUtils.copyProperties(holiday, holidayDto);
        logger.info("HolidayServiceImpl#saveHoliday --- After save: holidayDto: {}", holidayDto);
        return holidayDto;
    }
}
