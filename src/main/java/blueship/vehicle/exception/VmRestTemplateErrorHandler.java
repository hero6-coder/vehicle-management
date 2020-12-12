package blueship.vehicle.exception;

import blueship.vehicle.common.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class VmRestTemplateErrorHandler implements ResponseErrorHandler {
  private static Logger logger = LoggerFactory.getLogger(VmRestTemplateErrorHandler.class);
  private ObjectMapper mapper;

  public VmRestTemplateErrorHandler(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public boolean hasError(ClientHttpResponse response) throws IOException {
    HttpStatus status = response.getStatusCode();
    Series series = status.series();
    return Series.CLIENT_ERROR.equals(series) || Series.SERVER_ERROR.equals(series);
  }

  public void handleError(ClientHttpResponse response) throws IOException {
    InputStream ips = response.getBody();
    StringWriter writer = new StringWriter();
    IOUtils.copy(ips, writer, "UTF-8");
    String restErrorStr = writer.toString();
    logger.info("restErrorStr: [{}]", restErrorStr);

    try {
      VmRestError restError = (VmRestError) this.mapper.readValue(restErrorStr, VmRestError.class);
      if (StringUtils.isEmpty(restError.getCode()) && !StringUtils.isEmpty(restError.getError())) {
        restError.setCode(restError.getError());
      }

      if (StringUtils.isEmpty(restError.getTraceMessage()) && !StringUtils.isEmpty(restError.getMessage())) {
        restError.setTraceMessage(restError.getMessage());
      }

      HttpStatus status = response.getStatusCode();
      Integer httpStatus = status.value();
      throw new VmException(restError, httpStatus);
    } catch (IOException var8) {
      logger.error(var8.getMessage(), var8);
      VmException ex = new VmException(var8, ErrorCode.UNKNOWN_ERROR, new StringBuilder(var8.getMessage()));
      ex.setHttpStatus(response.getRawStatusCode());
      throw ex;
    }
  }
}