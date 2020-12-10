package blueship.vehicle.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BaseException extends RuntimeException {
  private static Logger logger = LoggerFactory.getLogger(BaseException.class);
  protected String messageCode;
  protected Object[] arguments;
  protected Integer httpStatus;
  protected ITcbsErrorCode errorCode;
  protected String traceMessage;
  private List<TcbsFieldError> fieldErrors = new ArrayList();

  public BaseException() {
  }

  public BaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public String getMessageCode() {
    return this.messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }

  public Object[] getArguments() {
    return this.arguments;
  }

  public void setArguments(Object[] arguments) {
    this.arguments = arguments;
  }

  public Integer getHttpStatus() {
    return this.httpStatus;
  }

  public void setHttpStatus(Integer httpStatus) {
    this.httpStatus = httpStatus;
  }

  public ITcbsErrorCode getErrorCode() {
    return this.errorCode;
  }

  public void setErrorCode(ITcbsErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public String getTraceMessage() {
    return this.traceMessage;
  }

  public void setTraceMessage(String traceMessage) {
    this.traceMessage = traceMessage;
  }

  public List<TcbsFieldError> getFieldErrors() {
    return this.fieldErrors;
  }

  public void setFieldErrors(List<TcbsFieldError> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }

  public List<TcbsFieldError> addFieldError(String fieldId, String errorCode, String errorMessage) {
    TcbsFieldError fieldError = new TcbsFieldError();
    fieldError.setFieldId(fieldId);
    fieldError.setErrorCode(errorCode);
    fieldError.setErrorMessage(errorMessage);
    this.fieldErrors.add(fieldError);
    return this.fieldErrors;
  }

  public List<TcbsFieldError> addFieldError(String fieldId, String errorCode, String errorMessage, Object[] errorMessageArgs) {
    TcbsFieldError fieldError = new TcbsFieldError();
    fieldError.setFieldId(fieldId);
    fieldError.setErrorCode(errorCode);
    fieldError.setErrorMessage(errorMessage);
    fieldError.setErrorMessageArgs(errorMessageArgs);
    this.fieldErrors.add(fieldError);
    return this.fieldErrors;
  }

  public TcbsRestError transformToRestError() {
    TcbsRestError restError = new TcbsRestError();
    restError.setCode(this.errorCode.getCode());
    restError.setTraceMessage(this.traceMessage);
    restError.setMessage(this.errorCode.getMessageCode());
    restError.setFieldErrors(this.fieldErrors);
    restError.setException(this.getClass().getName());
    return restError;
  }

  public TcbsRestError transformToRestError(MessageSource messageSource, Locale locale) {
    TcbsRestError restError = new TcbsRestError();
    restError.setCode(this.errorCode.getCode());
    restError.setTraceMessage(this.traceMessage);
    String messageCode = this.errorCode.getMessageCode();

    try {
      String message = messageSource.getMessage(messageCode, this.arguments, locale);
      restError.setMessage(message);
    } catch (NoSuchMessageException var6) {
      logger.warn(var6.getMessage());
      restError.setMessage(messageCode);
    }

    restError.setFieldErrors(this.fieldErrors);
    restError.setException(this.getClass().getName());
    return restError;
  }
}
