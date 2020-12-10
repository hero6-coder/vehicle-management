package blueship.vehicle.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * catching VmException, other exception will be caught by default exception
 * handler
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(annotations = RestController.class)
public class VmControllerHandlerAdvice extends ResponseEntityExceptionHandler {
  private static Logger logger = LoggerFactory.getLogger(VmControllerHandlerAdvice.class);
  private final String BLANK = "BLANK";
  @Value("${info.app.id}")
  private String appId;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private Locale defaultLocale;

  @ExceptionHandler(VmException.class)
  public @ResponseBody
  VmAPIException handleCustomException(VmException tcbsex, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
    // write error
    logger.warn(
      "ExceptionHandelerAdvice:handle controller exception:{}<=>{}<=>{} {}",
      tcbsex.getErrorCode(), tcbsex.getMessage(), tcbsex.getTraceMessage(),
      LoggingUtils.objToStringIgnoreEx(tcbsex.transformToRestError(messageSource, defaultLocale)));

    logger.warn(tcbsex.getMessage(), tcbsex);

    // response status
    response.setStatus(
      tcbsex.getHttpStatus() == null ? HttpServletResponse.SC_BAD_REQUEST : tcbsex.getHttpStatus());

    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

    // transform Exception to rest api
    VmRestError restError = tcbsex.transformToRestError(messageSource, defaultLocale);
    VmAPIException rtv = new VmAPIException();

    String errorCode = appId + restError.getCode();
    rtv.setCode(errorCode);

    // convert error code to message
    if (tcbsex.getErrorCode().getCode().equals(VmHandlerErrorCode.FORM_ERROR.getCode())
      && restError.getFieldErrors().size() > 0) {
      VmFieldError vmFieldError = restError.getFieldErrors().get(0);
      String messageCode = vmFieldError.getErrorMessage();
      try {
        messageCode = StringUtils.replace(messageCode, "{", "");
        messageCode = StringUtils.replace(messageCode, "}", "");
        String message = messageSource.getMessage(messageCode, vmFieldError.getErrorMessageArgs(),
          defaultLocale);
        rtv.setMessage(vmFieldError.getFieldId() + ": " + message);
      } catch (NoSuchMessageException e) {
        logger.warn(e.getMessage());
        rtv.setMessage(messageCode);
      }
    } else {
      rtv.setMessage(restError.getMessage());
    }

    // return to client
    return rtv;

  }

  @ExceptionHandler(Exception.class)
  public @ResponseBody
  VmAPIException handleCustomException(Exception ex, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
    // write to file logs
    logger.error(
      "ExceptionHandelerAdvice:handle controller exception:{}", ex.getMessage());

    logger.error(ex.getMessage(), ex);

    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

    VmAPIException rtv = new VmAPIException();

    String errorCode = appId + VmHandlerErrorCode.UNKNOWN_ERROR.getCode();
    rtv.setCode(errorCode);

    // convert error code to message
    String message = messageSource.getMessage(VmHandlerErrorCode.UNKNOWN_ERROR.getMessageCode(), null, defaultLocale);
    rtv.setMessage(message);

    // return to client
    return rtv;

  }
}