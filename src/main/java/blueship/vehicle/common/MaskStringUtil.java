package blueship.vehicle.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MaskStringUtil {
  public static final String MASK_CHAR = "*";
  public static final String MOBILE_MASK_PATTERN = "\\d{1,4}$";
  public static final String TRANSACTION_CODE_MASK_PATTERN = ".{1,8}$";
  public static final String HEADER_MASK_PATTERN = "(?<= ).*$";
  public static final String COUNTRY_SPECIAL_PATTERN = "[,() ]*";

  public static String maskString(String s, String pattern) {
    if (s == null) return s;
    return s.replaceAll(pattern, MASK_CHAR);
  }

}
