package blueship.vehicle.exception;

public interface ITcbsErrorCode {
    String getCode();

    String getMessageCode();

    Integer getHttpStatus();
}