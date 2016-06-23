package plnr.custom.framework.notification;

/**
 * Created by administartor on 5/12/15.
 */
public class sendNotification {
    private static sendNotification ourInstance = new sendNotification();

    private sendNotification() {
    }

    public static sendNotification getInstance() {
        return ourInstance;
    }
}
