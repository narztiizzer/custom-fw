package plnr.custom.framework.core.base.ui.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import plnr.custom.framework.core.BaseApplication;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;

/**
 * Created by ponlavitlarpeampaisarl on 2/3/15 AD.
 */
public class BaseListViewController extends ListView {

    private BaseListViewController(Context context) {
        super(BaseApplication.getContext());
    }

    private BaseListViewController(Context context, AttributeSet attrs) {
        super(BaseApplication.getContext(), attrs);
    }

    private BaseListViewController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(BaseApplication.getContext(), attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private BaseListViewController(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(BaseApplication.getContext(), attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setAdapter(ListAdapter adapter) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void setDataSource(ArrayList<BaseCellDataSource> dataSource) {

    }

    public void reload() {
        this.setAdapter(getAdapter());
    }
}
