package blueship.vehicle.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class TcbsRestError implements Serializable {
    private static final long serialVersionUID = 7853373628294414978L;
    private String status = "error";
    private String code;
    private String error;
    private String message;
    protected String traceMessage;
    private Object[] arguments;
    private Long timestamp = System.currentTimeMillis();
    private String exception;
    private List<TcbsFieldError> fieldErrors = new ArrayList();
    private String traceId;

    public TcbsRestError() {
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
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

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getException() {
        return this.exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}

