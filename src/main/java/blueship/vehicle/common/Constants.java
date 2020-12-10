package blueship.vehicle.common;

import blueship.vehicle.exception.TcbsException;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Constants {
  public static final String POST = "POST";
  public static final String POST_JSON = "POST_JSON";
  public static final String GET = "GET";
  public static final String AMQP = "AMQP";
  public static final String SUCCESS = "SUCCESS";
  public static final String ERROR = "ERROR";
  public static final String START = "START";

  public static final String EVENT_CLAZZ = "event-clazz";
  public static final String EVENT_DATA = "event-data";
  public final static String DEFAULT_TIMEZONE_GMT7 = "GMT+7";
  public final static String DEFAULT_SHORT_TIME_PATTERN = "yyyy-MM-dd";
  public final static String DEFAULT_FULL_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
  public final static String DEFAULT_EMAIL_SMS_PATTERN = "dd/MM/yyyy";
  public final static String BANK_DATETIME_PATTERN = "yyyyMMdd";
  public final static String EQUALS = "=";
  // plz write your log with this tag then kibana maybe send a warning SMS to U.
  // Thanks god!
  public static String KIBANA_ALERT_TAG = "action=ops-alert";
  public static String DEADLINE_OTC_SATURDAY = "DeadlineOTCSaturday";
  public static String ATTRIBUTE_DEADLINE_PAYMENT = "DeadlinePayment";
  public static String ATTRIBUTE_MARKET_DEADLINE_PAYMENT = "MarketDeadlinePayment";
  public static String ATTRIBUTE_DEADLINE_SIGN_SELL = "DeadlineSignSell";
  public static String FUND_CODE = "TCBF";
  public static String CBF_PLAN_CODE = "CBF";
  public static String CBF_PLAN_NAME = "Trai tuc sinh loi";
  public static String WEB = "web";
  public static String MOBILE = "mobile";
  public static String COMBO_BF = "CBF";
  public static String BOND_Listed = "Listed";
  public static String BOND_OTC = "OTC";
  public static String BOND_ALL_STATUS = "ALL";
  public static String CONFIRM = "CONFIRM";
  public static String PERSONAL_ACCOUNT_TYPE = "CN";
  public static String CORPORATE_ACCOUNT_TYPE = "TC";
  protected Boolean isCallInternalMethod = false;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT+7")
  private Date createdDate;
  public enum PRICING {
    TYPE_SELL("2"), TYPE_BUY("1"), PURPOSE_MARKET("MARKET"), PURPOSE_IBOND("NORMAL"), PURPOSE_SALEKIT("SALEKIT"),
    CUSTOMER_TYPE_INDIVIDUAL("INDIVIDUAL"), CUSTOMER_TYPE_CORPORATE("CORPORATE"), ACTIVE_COMBO_BOND_FUND("1"),
    CUSTOMER_TYPE_PERSONAL("PERSONAL"), INACTIVE_COMBO_BOND_FUND("0");
    private String content;

    PRICING(String content) {
      this.content = content;
    }

    public String content() {
      return content;
    }

    @Override
    public String toString() {
      return content;
    }
  }
  public enum SERVED_TYPE {
    RMS("RM-served", "rms", 1), CUS("Self-served", "cus", 0);
    private String content;
    private String code;
    private int value;

    SERVED_TYPE(String content, String code, int value) {
      this.content = content;
      this.code = code;
      this.value = value;
    }

    public String content() {
      return content;
    }

    public String code() {
      return code;
    }

    public int value() {
      return value;
    }

    @Override
    public String toString() {
      return content;
    }

  }
  public enum TIME_FORMAT {
    DATE_TIME_FULL("yyyy-MM-dd'T'hh:mm:ss'Z'"), DATE_FULL("yyyy-dd-MM HH:mm:ss"), DATE_TIME("yyyy-MM-dd"),
    DD_MM_YYYY("dd/MM/yyyy");

    public String content;

    TIME_FORMAT(String content) {
      this.content = content;
    }

    public String content() {
      return content;
    }
  }
  public enum PRODUCT_CATEGORY {
    // bundle
    PRIX("Prix", 0), PRO90("Pro90", 1), PRO180("Pro180", 2), PRO360("Pro360", 3), PLUS("Plus", 4),
    OTHER("OTHER", 5);

    // lookup table to be used to find enum for conversion
    private static final Map<String, PRODUCT_CATEGORY> lookupLabel = new HashMap<String, PRODUCT_CATEGORY>();
    private static final Map<Integer, PRODUCT_CATEGORY> lookupValue = new HashMap<Integer, PRODUCT_CATEGORY>();

    static {
      for (PRODUCT_CATEGORY e : EnumSet.allOf(PRODUCT_CATEGORY.class)) {
        lookupLabel.put(e.getLabel(), e);
        lookupValue.put(e.getIndex(), e);
      }
    }

    private final Integer index;
    private final String label;

    private PRODUCT_CATEGORY(String label, Integer index) {
      this.label = label;
      this.index = index;
    }

    public static PRODUCT_CATEGORY lookup(String key) {
      PRODUCT_CATEGORY ms = lookupLabel.get(key);
      if (ms == null) {
        return OTHER;
      } else {
        return ms;
      }
    }

    public static PRODUCT_CATEGORY lookup(Integer value) {
      PRODUCT_CATEGORY ms = lookupValue.get(value);
      if (ms == null) {
        return OTHER;
      } else {
        return ms;
      }
    }

    public Integer getIndex() {
      return index;
    }

    public String getLabel() {
      return label;
    }
  }
  public enum ACTION_TYPE {
    BUY("buy", 5), SELL("sell", 7);

    // lookup table to be used to find enum for conversion
    private static final Map<String, ACTION_TYPE> lookupLabel = new HashMap<String, ACTION_TYPE>();
    private static final Map<Integer, ACTION_TYPE> lookupValue = new HashMap<Integer, ACTION_TYPE>();

    static {
      for (ACTION_TYPE e : EnumSet.allOf(ACTION_TYPE.class)) {
        lookupLabel.put(e.getLabel(), e);
        lookupValue.put(e.getValue(), e);
      }
    }

    private final Integer value;
    private final String label;

    private ACTION_TYPE(String label, Integer value) {
      this.label = label;
      this.value = value;
    }

    public static ACTION_TYPE lookup(String key) {
      ACTION_TYPE ms = lookupLabel.get(key.toLowerCase());
      if (ms == null) {
        throw new TcbsException(null, ErrorCode.ACTION_TYPE_NOT_FOUND,
          new StringBuilder("Action type by key:").append(key).append(" not found"));
      } else {
        return ms;
      }
    }

    public static ACTION_TYPE lookup(Integer value) {
      ACTION_TYPE ms = lookupValue.get(value);
      if (ms == null) {
        throw new TcbsException(null, ErrorCode.ACTION_TYPE_NOT_FOUND,
          new StringBuilder("Action type by value:").append(value).append(" not found"));
      } else {
        return ms;
      }
    }

    public Integer getValue() {
      return value;
    }

    public String getLabel() {
      return label;
    }
  }
  public enum REFERENCE_STATUS {
    CUSTOMER_SELF_SERVED("0"),
    RM_REFER("1"),
    CUSTOMER_CONFIRM("2"),
    CAMPAIN_REFER("3"),
    BOND_CONVERSION("4");

    private final String value;

    private REFERENCE_STATUS(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public Integer toInt() {
      return Integer.valueOf(value);
    }
  }
  public interface LISTED_STATUS {
    public static final Integer LISTED = 1;
    public static final Integer OTC = 0;
  }

  public interface TRANSACTION_STATUS {
    public static final String WaitingForPaymentBuy = "WaitingForPaymentBuy";
    public static final String Done = "Done";
    public static final String InQueue = "InQueue";
    public static final String OrderNotSuccessful = "OrderNotSuccessful";
    public static final String WaitingForConfirmed = "WaitingForConfirmed";
    public static final String HoldMoneySuccessful = "HoldMoneySuccessful";
    public static final String ToBeSigned = "ToBeSigned";
    public static final String WaitingForChecking = "WaitingForChecking";
    public static final String WaitingForPaymentSell = "WaitingForPaymentSell";
    public static final String WaitingForPayment = "WaitingForPayment";
    public static final String Failure = "Failure";
    public static final String WaitingForTransfer = "WaitingForTransfer";
    public static final String WaitingForConfirmRMServed = "WaitingForConfirmRMServed";
    public static final String MoneyTransferredToIssuer = "MoneyTransferredToIssuer";
    public static final String WaitingForHoldMoney = "WaitingForHoldMoney";
  }

  public interface PRE_ORDER_TRANSACTION_STATUS {
    public static final String active = "active";
    public static final String deactive = "deactive";
  }

  public interface SIGN_CONTRACT_STATUS {
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
  }

  public interface BUILDBOOK {
    public static final Integer NO = 0;
    public static final Integer YES = 1;
  }

  public interface ACTION_ID_MARKET {
    public Integer BUY = 1;
    public Integer SELL = 2;
  }
}
