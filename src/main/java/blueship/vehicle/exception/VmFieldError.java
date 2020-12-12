package blueship.vehicle.exception;

import java.io.Serializable;

public class VmFieldError implements Serializable {
  private static final long serialVersionUID = 6119763179869680268L;
  private String fieldId;
  private String errorCode;
  private String errorMessage;
  private Object[] errorMessageArgs;

  public VmFieldError() {
  }

  public String getFieldId() {
    return this.fieldId;
  }

  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }

  public String getErrorCode() {
    return this.errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Object[] getErrorMessageArgs() {
    return this.errorMessageArgs;
  }

  public void setErrorMessageArgs(Object[] errorMessageArgs) {
    this.errorMessageArgs = errorMessageArgs;
  }
}
