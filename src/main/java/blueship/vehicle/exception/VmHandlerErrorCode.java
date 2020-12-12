package blueship.vehicle.exception;

import blueship.vehicle.common.IErrorCode;

import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum VmHandlerErrorCode implements IErrorCode {
  UNKNOWN_ERROR("000", "unknown.error"),
  FORM_ERROR("003", "form.error"),
  THROTTLING_RATTING_LIMITED("133", "throttling.ratting.limited"),
  ;

  // lookup table to be used to find enum for conversion
  private static final Map<String, VmHandlerErrorCode> lookup = new HashMap<String, VmHandlerErrorCode>();

  static {
    for (VmHandlerErrorCode e : EnumSet.allOf(VmHandlerErrorCode.class))
      lookup.put(e.getCode(), e);
  }

  private String code;

  private String messageCode;
  private Integer httpStatus;

  VmHandlerErrorCode(String errorCode, String messageCode) {
    this.code = errorCode;
    this.messageCode = messageCode;
    this.httpStatus = HttpServletResponse.SC_BAD_REQUEST;
  }

  VmHandlerErrorCode(String errorCode, String messageCode, Integer httpStatus) {
    this.code = errorCode;
    this.messageCode = messageCode;
    this.httpStatus = httpStatus;
  }

  public static VmHandlerErrorCode get(String errorCode) {
    return lookup.get(errorCode);
  }

  public String getCode() {
    return this.code;
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
