package plnr.custom.framework.authentication.intf;

/**
 * Created by nattapongr on 5/14/15.
 */
public interface LoginListener {
    public void didSucess();

    public void didFailed(String error);
}
