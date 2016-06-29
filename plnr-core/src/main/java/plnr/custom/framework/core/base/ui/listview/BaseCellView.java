package plnr.custom.framework.core.base.ui.listview;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by ponlavitlarpeampaisarl on 2/6/15 AD.
 */
public abstract class BaseCellView extends LinearLayout {

    private BaseCellDataSource dataSource;

    public BaseCellView(BaseCellDataSource dataSource, Context context) {
        super(context);
        this.dataSource = dataSource;
        LayoutInflater inflater = LayoutInflater.from(context);
        View holder = this.setupResource(inflater);
        this.setupView(getDataSource(), holder);
        this.setupAction();
        addView(holder);
    }


    public static BaseCellView Builder(Class objClass, BaseCellDataSource dataSource, Context context) {
        try {
            Constructor ct = objClass.getDeclaredConstructor(BaseCellDataSource.class, Context.class);
            Object obj = ct.newInstance(dataSource, context);
            BaseCellView view = (BaseCellView) obj;
            return view;
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public BaseCellDataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * Use for setup inflate resource.
     */
    protected abstract View setupResource(LayoutInflater inflater);

    /**
     * Use for setup value for each field in view resource.
     */
    protected abstract void setupView(BaseCellDataSource dataSource, View holder);

    /**
     * Use for setup action for this view such as Listener, Button action, etc.
     */
    protected abstract void setupAction();

    @Override
    public void setOnClickListener(OnClickListener l) {
        throw new UnsupportedOperationException("Don't set it directly, override \"performCellClickedAction\" instead.");
    }

    /**
     * Use for setup on click action for this view such as Listener, Button action, etc.
     */
    public void performCellClickedAction(AdapterView<?> parent, View view, int position, long id) {

    }
}
