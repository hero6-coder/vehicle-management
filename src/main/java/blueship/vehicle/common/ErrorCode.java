package blueship.vehicle.common;

import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * src\test\resources\messages_vi_VN.properties
 *
 * @author TruongNQ
 */
public enum ErrorCode implements IErrorCode {

  UNKNOWN_ERROR("000", "unknown.error"),
  INVALID_PARAMS("001", "invalid.params"),
  USER_NOT_EXIST("002", "user.not.exist"),
  VEHICLE_NOT_EXIST("003", "vehicle.not.exist"),
  MAINTENANCE_NOT_EXIST("004", "maintenance.not.exist"),
  FAILED_PERSIST_DATA("005", "failed.persist.data"),
  // FIXME: add more here
  ;

  // lookup table to be used to find enum for conversion
  private static final Map<String, ErrorCode> lookup = new HashMap<String, ErrorCode>();

  static {
    for (ErrorCode e : EnumSet.allOf(ErrorCode.class))
      lookup.put(e.getCode(), e);
  }

  private String code;

  private String messageCode;
  private Integer httpStatus;

  ErrorCode(String errorCode, String messageCode) {
    this.code = errorCode;
    this.messageCode = messageCode;
    this.httpStatus = HttpServletResponse.SC_BAD_REQUEST;
  }

  ErrorCode(String errorCode, String messageCode, Integer httpStatus) {
    this.code = errorCode;
    this.messageCode = messageCode;
    this.httpStatus = httpStatus;
  }

  public static ErrorCode get(String errorCode) {
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
