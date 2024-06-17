package love.pangteen;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/16 15:25
 **/
public class Utils {

    /**
     * line separator
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * The separator of property name
     */
    public static final String PROPERTY_NAME_SEPARATOR = ".";

    /**
     * The prefix of property name of Dubbo
     */
    public static final String HTRPC_PREFIX = "htrpc";

    /**
     * The prefix of property name for Dubbo scan
     */
    public static final String HTRPC_SCAN_PREFIX =
            HTRPC_PREFIX + PROPERTY_NAME_SEPARATOR + "scan" + PROPERTY_NAME_SEPARATOR;

    public static final String HTRPC_SCAN_PROPERTIES = "htrpcScanProperties";

    /**
     * The prefix of property name for Dubbo Config
     */
    public static final String HTRPC_CONFIG_PREFIX =
            HTRPC_PREFIX + PROPERTY_NAME_SEPARATOR + "config" + PROPERTY_NAME_SEPARATOR;


    public static final String BASE_PACKAGES_PROPERTY_NAME = "base-packages";

    public static final String BASE_PACKAGES_BEAN_NAME = "htrpc-service-class-base-packages";
}
