package plnr.custom.framework.core.base.ui.activity;

import android.app.Fragment;
import android.widget.Toast;

/**
 * Created by ponlavitlarpeampaisarl on 2/17/15 AD.
 */
public class BaseFragment extends Fragment {
    protected void showToast(final String txt) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), txt, Toast.LENGTH_LONG).show();
            }
        });
    }
}
