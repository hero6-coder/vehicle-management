package blueship.vehicle.exception;

import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TcbsHandlerErrorCode implements ITcbsErrorCode {
	UNKNOWN_ERROR("000", "unknown.error"),
	FORM_ERROR("003", "form.error"),
	THROTTLING_RATTING_LIMITED("133", "throttling.ratting.limited"),
	;

	// lookup table to be used to find enum for conversion
	private static final Map<String, TcbsHandlerErrorCode> lookup = new HashMap<String, TcbsHandlerErrorCode>();
	static {
		for (TcbsHandlerErrorCode e : EnumSet.allOf(TcbsHandlerErrorCode.class))
			lookup.put(e.getCode(), e);
	}

	private String code;

	private String messageCode;
	private Integer httpStatus;

	TcbsHandlerErrorCode(String errorCode, String messageCode) {
		this.code = errorCode;
		this.messageCode = messageCode;
		this.httpStatus = HttpServletResponse.SC_BAD_REQUEST;
	}

	TcbsHandlerErrorCode(String errorCode, String messageCode, Integer httpStatus) {
		this.code = errorCode;
		this.messageCode = messageCode;
		this.httpStatus = httpStatus;
	}

	public String getCode() {
		return this.code;
	}

	public static TcbsHandlerErrorCode get(String errorCode) {
		return lookup.get(errorCode);
	}

	public void setErrorCode(String errorCode) {
		this.code = errorCode;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}
}
