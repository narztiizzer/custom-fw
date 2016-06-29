package plnr.custom.framework.api.intf;

/**
 * Created by nattapongr on 5/12/15.
 */
public interface IWebAPINotification {
    public void webAPISuccess(String code, String message, String jsonData, Class Class);

    public void webAPIFailed(String code, String message, Class Class);
}
