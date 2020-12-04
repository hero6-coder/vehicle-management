package blueship.vehicle.dto;

import blueship.vehicle.common.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchListDto implements Serializable {
    private static final long serialVersionUID = -6150677137218483883L;
    private Integer id;

    private String ticker;

    private Boolean active;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_SHORT_TIME_PATTERN, timezone = Constants.DEFAULT_TIMEZONE_GMT7)
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DEFAULT_SHORT_TIME_PATTERN, timezone = Constants.DEFAULT_TIMEZONE_GMT7)
    private LocalDate endDate;
}
