package plnr.custom.framework.core.base.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;



import java.util.Timer;
import java.util.TimerTask;

import plnr.custom.framework.Config;


/**
 * Created by ponlavitlarpeampaisarl on 2/17/15 AD.
 */
public class BaseFragmentActivity extends FragmentActivity {
    private ProgressDialog dialogLoading;
    private Timer dialogLoadingTimeoutTimer;
    private AlertDialog dialog;

    private boolean isTiming;


    /**
     * User for show loading dialog
     * @param msg message when loading
     * @param timeoutMsg message when timeout
     * @param timeoutPeriod duration for timeout
     */
    public void loadingDialog(final String msg, final String timeoutMsg, final long timeoutPeriod) {
        setupTimeoutForDialog(timeoutMsg, timeoutPeriod);
        this.isTiming = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialogLoading == null) {
                    dialogLoading = new ProgressDialog(getThis());
                    dialogLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialogLoading.setCanceledOnTouchOutside(false);
                    dialogLoading.setIndeterminate(true);
                }
                hideLoadingDialog();
                dialogLoading.setMessage(msg);
                dialogLoading.show();
            }
        });
    }

    public void stopTimoutTimer() {
        if (dialogLoadingTimeoutTimer != null)
            dialogLoadingTimeoutTimer.cancel();
    }

    public void hideMessageDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null)
                    dialog.dismiss();
            }
        });
    }

    public void hideLoadingDialog() {
        this.isTiming = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialogLoading != null) {
                    dialogLoading.dismiss();
                }
                stopTimoutTimer();
            }
        });
    }

    /**
     * User for show loading dialog
     * @param msg message when loading
     */
    public void loadingDialog(final String msg) {
        this.loadingDialog(msg, Config.RequestTimeoutMessage, Config.RequestDefaultTimeout);
    }

    /**
     * User for show loading dialog
     * @param text message to show
     */
    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupTimeoutForDialog(final String timeoutMsg, final long timeoutPeriod) {
        this.dialogLoadingTimeoutTimer = new Timer();

        this.dialogLoadingTimeoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialogLoading.isIndeterminate()) {
                            hideLoadingDialog();
                            if (isTiming)
                                showToast(timeoutMsg);
                        }
                    }
                });
            }
        }, timeoutPeriod);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public BaseFragmentActivity getThis() {
        return this;
    }
}
