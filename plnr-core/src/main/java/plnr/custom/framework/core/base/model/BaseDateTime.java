package plnr.custom.framework.core.base.model;

import java.util.Date;

import plnr.custom.framework.Config;


/**
 * Created by ponlavitlarpeampaisarl on 2/4/15 AD.
 */
public class BaseDateTime extends Date {
    public BaseDateTime(String unixTime) {
        if (unixTime != null)
            setTime(Long.parseLong(unixTime) * (Config.USE_MILLISECONDS_TIMESTAMP_EXCHANGE ? 1 : 1000));
    }

    public BaseDateTime(long unixTime) {
        this((unixTime + "").length() > 10 ? (unixTime + "").substring(0, 10) : unixTime + "");
    }

    public BaseDateTime() {
        super();
    }

    public String toUnixTimeString() {
        return (getTime() / (Config.USE_MILLISECONDS_TIMESTAMP_EXCHANGE ? 1 : 1000)) + "";
    }

}
