package plnr.custom.framework;

/**
 * Created by ponlavitlarpeampaisarl on 2/4/15 AD.
 * Framework configuration
 */
public class Config {

    //========= FORMATTING =========

    /**
     * Default date format
     */
    public final static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Default date time format
     */
    public final static String DEFAULT_DATETIME_FORMAT = "dd/MM/yyyy mm:HH";

    //========= REQUEST =========

    /**
     * Default request timeout for request data from web services.
     */
    public final static int RequestDefaultTimeout = 180000;


    /**
     * Default request timeout message for web services.
     */
    public final static String RequestTimeoutMessage = "Request Timeout!";
    /**
     * Default data entries for JSON Service using with IJSONEnable Interface
     */
    public static final String RESPONSE_ENTRIES = "entries";

    /**
     * Default server response message for JSON Service using with IJSONEnable Interface
     */
    public static final String RESPONSE_MESSAGE = "message";

    /**
     * Default status code for JSON Service using with IJSONEnable Interface
     */
    public static final String RESPONSE_STATUS = "status";

    /**
     * Set is the web service using milliseconds as time stamp exchange.
     */
    public final static boolean USE_MILLISECONDS_TIMESTAMP_EXCHANGE = false;

    public final static String NETWORK_ERROR = "Network is not available";

    public static String DEFAULT_REPLACE_NULL = "-";
    public static String DEFAULT_SPLIT_STRING = ".;.";
    public static long DAY = 86400;

    public static boolean FOR_TESTING = false;

}

