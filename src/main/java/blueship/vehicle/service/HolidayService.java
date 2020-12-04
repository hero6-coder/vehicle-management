package blueship.vehicle.service;

import blueship.vehicle.dto.HolidayDto;

import java.time.LocalDate;
import java.util.List;

public interface HolidayService {
	List<HolidayDto> getActiveHoliday();
	List<HolidayDto> getActiveHolidayAfter(LocalDate date);
	HolidayDto saveHoliday(HolidayDto holiday);
}
