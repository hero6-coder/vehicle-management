package blueship.vehicle.exception;

import blueship.vehicle.common.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import java.util.List;

public class TcbsException extends BaseException {
  private static final long serialVersionUID = 8823356956725033191L;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public TcbsException(Throwable cause, ITcbsErrorCode tcbsErrorCode) {
    super(tcbsErrorCode.getCode() + "-" + (null == cause ? tcbsErrorCode.getMessageCode() : cause.getMessage()), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(tcbsErrorCode.getMessageCode());
    if (cause != null) {
      this.setTraceMessage(cause.getMessage());
    }

  }

  public TcbsException(Throwable cause, ITcbsErrorCode tcbsErrorCode, StringBuilder traceMessage) {
    super((new StringBuilder(tcbsErrorCode.getCode())).append("-").append(traceMessage).toString(), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setTraceMessage(traceMessage.toString());
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(tcbsErrorCode.getMessageCode());
  }

  public TcbsException(Throwable cause, ITcbsErrorCode tcbsErrorCode, StringBuilder traceMessage, Object... arguments) {
    super((new StringBuilder(tcbsErrorCode.getCode())).append("-").append(traceMessage).toString(), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setTraceMessage(traceMessage.toString());
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(this.messageCode);
    this.setArguments(arguments);
  }

  public TcbsException(Throwable cause, ITcbsErrorCode tcbsErrorCode, Object... arguments) {
    super(tcbsErrorCode.getCode() + "-" + (null == cause ? tcbsErrorCode.getMessageCode() : cause.getMessage()), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(this.messageCode);
    this.setArguments(arguments);
    if (cause != null) {
      this.setTraceMessage(cause.getMessage());
    }

  }

  public TcbsException(Throwable cause, ITcbsErrorCode tcbsErrorCode, List<TcbsFieldError> fieldErrors) {
    super(tcbsErrorCode.getCode() + "-" + (null == cause ? tcbsErrorCode.getMessageCode() : cause.getMessage()), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setFieldErrors(fieldErrors);
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(tcbsErrorCode.getMessageCode());
    if (cause != null) {
      this.setTraceMessage(cause.getMessage());
    }

  }

  public TcbsException(Throwable cause, ITcbsErrorCode tcbsErrorCode, List<TcbsFieldError> fieldErrors, StringBuilder traceMessage) {
    super((new StringBuilder(tcbsErrorCode.getCode())).append("-").append(traceMessage).toString(), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setFieldErrors(fieldErrors);
    this.setTraceMessage(traceMessage.toString());
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(tcbsErrorCode.getMessageCode());
    this.setArguments(this.arguments);
  }

  public TcbsException(final TcbsRestError restError, final int httpStatus) {
    super(restError.getTraceMessage(), new RestClientException(restError.getMessage()));
    this.logger.info("Create TcbsException from restError:{} httpStatus:{}", LoggingUtils.objToStringIgnoreEx(restError), httpStatus);
    this.setErrorCode(new ITcbsErrorCode() {
      public String getMessageCode() {
        return restError.getMessage();
      }

      public Integer getHttpStatus() {
        return httpStatus;
      }

      public String getCode() {
        return restError.getCode();
      }
    });
    this.setFieldErrors(restError.getFieldErrors());
    this.setTraceMessage(restError.getTraceMessage());
  }
}
