package plnr.custom.framework.core.base.ui.listview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Created by NARZTIIZZER on 6/17/2016.
 */
public class BaseScrollList extends ScrollView {
    public BaseScrollList(Context context) {
        super(context);
    }

    public BaseScrollList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseScrollList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseScrollList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollListAdapter(BaseScrollListAdapter adapter) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for(int loopView = 0 ; loopView < adapter.getDataSource().size() ; loopView++) {
            View cellView = adapter.getChildViewAtIndex(loopView);
            linearLayout.addView(cellView);
        }

        this.addView(linearLayout);
    }
}
