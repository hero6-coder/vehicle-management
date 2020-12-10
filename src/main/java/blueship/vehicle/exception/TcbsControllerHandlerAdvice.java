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
 * catching TcbsException, other exception will be caught by default exception
 * handler
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(annotations = RestController.class)
public class TcbsControllerHandlerAdvice extends ResponseEntityExceptionHandler {
  private static Logger logger = LoggerFactory.getLogger(TcbsControllerHandlerAdvice.class);
  private final String TCBS_ERROR_CODE = "TcbsErrorCode";
  private final String TCBS_ERROR_MESSAGE = "TcbsErrorMessage";
  private final String BLANK = "BLANK";
  @Value("${info.app.id}")
  private String appId;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private Locale defaultLocale;

  @ExceptionHandler(TcbsException.class)
  public @ResponseBody
  TcbsAPIException handleCustomException(TcbsException tcbsex, HttpServletRequest request,
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
    TcbsRestError restError = tcbsex.transformToRestError(messageSource, defaultLocale);
    TcbsAPIException rtv = new TcbsAPIException();

    String errorCode = appId + restError.getCode();
    rtv.setCode(errorCode);

    // convert error code to message
    if (tcbsex.getErrorCode().getCode().equals(TcbsHandlerErrorCode.FORM_ERROR.getCode())
      && restError.getFieldErrors().size() > 0) {
      TcbsFieldError tcbsFieldError = restError.getFieldErrors().get(0);
      String messageCode = tcbsFieldError.getErrorMessage();
      try {
        messageCode = StringUtils.replace(messageCode, "{", "");
        messageCode = StringUtils.replace(messageCode, "}", "");
        String message = messageSource.getMessage(messageCode, tcbsFieldError.getErrorMessageArgs(),
          defaultLocale);
        rtv.setMessage(tcbsFieldError.getFieldId() + ": " + message);
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
//
//	@ExceptionHandler(ThrottlingException.class)
//	public @ResponseBody
//    TcbsAPIException handleThrottlingException(ThrottlingException ex, HttpServletRequest request,
//                                               HttpServletResponse response) throws IOException {
//
//		// Elastic apm
//		Transaction transaction = ElasticApm.currentTransaction();
//		Span span = ElasticApm.currentSpan();
//		span.captureException(ex);
//
//		// write to file logs
//		logger.warn(
//				"ExceptionHandelerAdvice:handle controller exception:{} ElasticAPM transaction:{} traceId {} span {} traceId {}",
//				ex.getMessage(), transaction.getId(), transaction.getTraceId(), span.getId(), span.getTraceId());
//
//		logger.warn(ex.getMessage(), ex);
//
//		response.setStatus(HttpServletResponse.SC_CONFLICT);
//		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//
//		TcbsAPIException rtv = new TcbsAPIException();
//
//		String errorCode = appId + TcbsHandlerErrorCode.THROTTLING_RATTING_LIMITED.getCode();
//		rtv.setCode(errorCode);
//
//		// add error code to Elastic
//		transaction.setResult(errorCode);
//		transaction.addTag(TCBS_ERROR_CODE, errorCode);
//		span.addTag(TCBS_ERROR_CODE, errorCode);
//		rtv.setTraceId(transaction.getTraceId());
//		// convert error code to message
//		String message = messageSource.getMessage(TcbsHandlerErrorCode.THROTTLING_RATTING_LIMITED.getMessageCode(), null,
//				defaultLocale);
//		rtv.setMessage(message);
//
//		Throwable cz = ex.getCause();
//		if (cz != null && cz.getMessage() != null) {
//			span.addTag(TCBS_ERROR_MESSAGE, cz.getMessage());
//		} else {
//			span.addTag(TCBS_ERROR_MESSAGE, BLANK);
//		}
//
//		// return to client
//		return rtv;
//
//	}

  @ExceptionHandler(Exception.class)
  public @ResponseBody
  TcbsAPIException handleCustomException(Exception ex, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
    // write to file logs
    logger.error(
      "ExceptionHandelerAdvice:handle controller exception:{}", ex.getMessage());

    logger.error(ex.getMessage(), ex);

    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

    TcbsAPIException rtv = new TcbsAPIException();

    String errorCode = appId + TcbsHandlerErrorCode.UNKNOWN_ERROR.getCode();
    rtv.setCode(errorCode);

    // convert error code to message
    String message = messageSource.getMessage(TcbsHandlerErrorCode.UNKNOWN_ERROR.getMessageCode(), null, defaultLocale);
    rtv.setMessage(message);

    // return to client
    return rtv;

  }
}