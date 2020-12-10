package blueship.vehicle.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoggingUtils {
  public LoggingUtils() {
  }

  public static String objToStringIgnoreEx(Object prm) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.writeValueAsString(prm);
    } catch (JsonProcessingException var3) {
      var3.printStackTrace();
      return "";
    }
  }
}
