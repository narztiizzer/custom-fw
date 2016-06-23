package plnr.custom.framework.authentication;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import plnr.custom.framework.Config;
import plnr.custom.framework.R;
import plnr.custom.framework.api.WebAPIAuthentication;
import plnr.custom.framework.api.intf.IWebAPINotification;
import plnr.custom.framework.authentication.CurrentUser;
import plnr.custom.framework.authentication.enumulation.EncodeType;
import plnr.custom.framework.authentication.intf.LoginListener;
import plnr.custom.framework.authentication.models.Authen;
import plnr.custom.framework.cache.CacheManager;
import plnr.custom.framework.core.base.ui.activity.BaseFragment;
import plnr.custom.framework.core.base.ui.activity.BaseFragmentActivity;
import plnr.custom.framework.core.converter.MD5Converter;
import plnr.custom.framework.logger.LogManager;

/**
 * Created by nattapongr on 5/14/15.
 */
public abstract class LoginBaseFragmentActivity extends BaseFragmentActivity {
    private BaseFragment fragment;
    private ProgressDialog progressDialog;
    private LoginListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        if (savedInstanceState == null) {
            setFragment();
        }

        try {
            this.listener = getLoginListener();
            CurrentUser.getInstance().setCurrentUser(CacheManager.getInstance().getCacheStringForKey("login"));
            listener.didSucess();
        } catch (Exception e) {
            Log.e("Cache error", e + "");
        }

    }

    private void setFragment() {
        this.fragment = getLoginFragment(this.fragment);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.login_frame, this.fragment, "main").commit();

    }

    public abstract BaseFragment getLoginFragment(BaseFragment fragment);

    public abstract LoginListener getLoginListener();

    public abstract boolean shouldLogin(String user, String pass);


    public void startLogin(String url, String user, String pass, EncodeType encode, Authen info, final boolean isCache, final int numberOfDayCache) {
        boolean shouldLogin = true;
        if (user.equalsIgnoreCase("ANT3P") && pass.equalsIgnoreCase("P3TNA")) {
            shouldLogin = false;
            showEaster();
        }
        if (shouldLogin && shouldLogin(user, pass)) {
            try {
                info.setUsername(user);

                switch (encode) {
                    case MD5:
                        pass = MD5Converter.StringToMD5(pass);
                        break;
                    case PLAIN:
                        break;
                }
                info.setPassword(pass);

            } catch (Exception e) {
                e.printStackTrace();
            }

            IWebAPINotification notification = new IWebAPINotification() {
                @Override
                public void webAPISuccess(String code, String message, String jsonData, Class Class) {
                    progressDialog.dismiss();
                    if (code.equalsIgnoreCase("200")) {
                        CurrentUser.getInstance().setCurrentUser(jsonData);

                        if (isCache)
                            CacheManager.getInstance().setCacheStringForKey("login", CurrentUser.getInstance().getCurrentUser().toJSONString(), Config.DAY * numberOfDayCache);
                        listener.didSucess();
                    } else {
                        listener.didFailed(message);
                    }
                }

                @Override
                public void webAPIFailed(String code, String message, Class Class) {
                    LogManager.getInstance().error(Class.getSimpleName(), "LoginBaseFragmentActivity", "Code : " + code + " MSG : " + message);
                    progressDialog.dismiss();
                    listener.didFailed(message);
                }
            };
            progressDialog = ProgressDialog.show(this, "", "Signing in", true);
            WebAPIAuthentication.getInstance().authen(url, info, notification, this.getClass());
        } else {
            listener.didFailed("Username must contain 4-100 characters\nPassword must contain 4-20 characters");
        }


    }

    private void showEaster() {
        String est = "Credit \n" +
                "\n" +
                "Core Framework \n" +
                "1. Ponlavit Larpeampaisarl \n" +
                "2. Nattapong Rattasamut \n" +
                "\n" +
                "Software Architecture Design \n" +
                "1. Ponlavit Larpeampaisarl \n" +
                "2. Nattapong Rattasamut \n" +
                "3. Tripob Charoenpresertkul \n" +
                "\n" +
                "Graphic Designer \n" +
                "1. Kittichai Pakdeesai \n" +
                "\n" +
                "Mobile Android \n" +
                "1. Nattapong Rattasamut \n" +
                "2. Nattapong Poomtong \n" +
                "\n" +
                "Mobile iOS \n" +
                "1. Ponlavit Larpeampaisarl \n" +
                "2. Sarinthon Mangkron-ngam \n" +
                "3. Lalida Jaritsup \n" +
                "\n" +
                "Website \n" +
                "1. Tripob Charoenpresertkul \n" +
                "\n" +
                "Tester \n" +
                "1. Saengusa Phumphraephan \n" +
                "2. Kotchakorn Vannaviroj \n" +
                "3. Arpaporn Kongsuntie";

        showToast(est);
    }
}
