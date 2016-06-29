package plnr.custom.framework.authentication;

import plnr.custom.framework.authentication.models.User;
import plnr.custom.framework.core.base.enumulation.UpdatePolicy;

/**
 * Created by nattapongr on 5/15/15.
 */
public class CurrentUser {
    private static CurrentUser ourInstance;

    private User CurrentUser;

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if (ourInstance == null)
            ourInstance = new CurrentUser();
        return ourInstance;
    }

    public User getCurrentUser() {
        return this.CurrentUser;
    }

    public void setCurrentUser(String json) {
        if (this.CurrentUser == null)
            this.CurrentUser = new User();
        this.CurrentUser.updateFromJson(json, UpdatePolicy.ForceUpdate);

    }

    public void clearUser() {
        ourInstance = null;
    }
}
