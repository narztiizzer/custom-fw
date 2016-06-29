package plnr.custom.framework.core.base.intf;


import plnr.custom.framework.core.base.model.BaseDateTime;

/**
 * Created by ponlavitlarpeampaisarl on 2/4/15 AD.
 */
public interface IJSONEnable<T> {

    public String toJSONLocalString();

    public String toJSONString();

    public Object toJSONObject(boolean isLocaleOnly);

    public void updateTimeStamp();

    public BaseDateTime getUpdateDate();
}
