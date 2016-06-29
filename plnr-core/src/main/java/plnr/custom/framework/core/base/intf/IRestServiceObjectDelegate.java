package plnr.custom.framework.core.base.intf;

/**
 * Created by ponlavitlarpeampaisarl on 2/5/15 AD.
 */
public interface IRestServiceObjectDelegate {
    public boolean willMapDataFromString(String stringToMap);

    public void didMapDataFromStringSuccess(IJSONEnable object);

    public void didMapDataFromStringFailedWithError(String error);

    public void didFetchFailedWithError(String error, int errorCode);

    public void didPushSuccess();

    public void didPushFailedWithError(String error, int errorCode);
}
