package blueship.vehicle.exception;

import blueship.vehicle.common.IErrorCode;
import blueship.vehicle.common.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

import java.util.List;

public class VmException extends BaseException {
  private static final long serialVersionUID = 8823356956725033191L;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public VmException(Throwable cause, IErrorCode tcbsErrorCode) {
    super(tcbsErrorCode.getCode() + "-" + (null == cause ? tcbsErrorCode.getMessageCode() : cause.getMessage()), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(tcbsErrorCode.getMessageCode());
    if (cause != null) {
      this.setTraceMessage(cause.getMessage());
    }

  }

  public VmException(Throwable cause, IErrorCode tcbsErrorCode, StringBuilder traceMessage) {
    super((new StringBuilder(tcbsErrorCode.getCode())).append("-").append(traceMessage).toString(), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setTraceMessage(traceMessage.toString());
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(tcbsErrorCode.getMessageCode());
  }

  public VmException(Throwable cause, IErrorCode tcbsErrorCode, StringBuilder traceMessage, Object... arguments) {
    super((new StringBuilder(tcbsErrorCode.getCode())).append("-").append(traceMessage).toString(), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setTraceMessage(traceMessage.toString());
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(this.messageCode);
    this.setArguments(arguments);
  }

  public VmException(Throwable cause, IErrorCode tcbsErrorCode, Object... arguments) {
    super(tcbsErrorCode.getCode() + "-" + (null == cause ? tcbsErrorCode.getMessageCode() : cause.getMessage()), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(this.messageCode);
    this.setArguments(arguments);
    if (cause != null) {
      this.setTraceMessage(cause.getMessage());
    }

  }

  public VmException(Throwable cause, IErrorCode tcbsErrorCode, List<VmFieldError> fieldErrors) {
    super(tcbsErrorCode.getCode() + "-" + (null == cause ? tcbsErrorCode.getMessageCode() : cause.getMessage()), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setFieldErrors(fieldErrors);
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(tcbsErrorCode.getMessageCode());
    if (cause != null) {
      this.setTraceMessage(cause.getMessage());
    }

  }

  public VmException(Throwable cause, IErrorCode tcbsErrorCode, List<VmFieldError> fieldErrors, StringBuilder traceMessage) {
    super((new StringBuilder(tcbsErrorCode.getCode())).append("-").append(traceMessage).toString(), cause);
    this.setErrorCode(tcbsErrorCode);
    this.setFieldErrors(fieldErrors);
    this.setTraceMessage(traceMessage.toString());
    this.setHttpStatus(tcbsErrorCode.getHttpStatus());
    this.setMessageCode(tcbsErrorCode.getMessageCode());
    this.setArguments(this.arguments);
  }

  public VmException(final VmRestError restError, final int httpStatus) {
    super(restError.getTraceMessage(), new RestClientException(restError.getMessage()));
    this.logger.info("Create TcbsException from restError:[{}] httpStatus:[{}]", LoggingUtils.objToStringIgnoreEx(restError), httpStatus);
    this.setErrorCode(new IErrorCode() {
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
