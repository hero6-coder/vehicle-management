package blueship.vehicle.common;

public interface IErrorCode {
  String getCode();

  String getMessageCode();

  Integer getHttpStatus();
}