package blueship.vehicle.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TcbsAPIException implements Serializable {
	private static final long serialVersionUID = 2816364295034386832L;
	private String code;
	private String message;
	private String traceId;
}
