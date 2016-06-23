package plnr.custom.framework.core.component.cell.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import plnr.custom.framework.R;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseCellView;


/**
 * Created by ponlavitlarpeampaisarl on 2/6/15 AD.
 */
public class CellSectionBreak extends BaseCellView {


    /**
     * Simple constructor to use when creating a view from code.
     */
    public CellSectionBreak(BaseCellDataSource dataSource, Context context) {
        super(dataSource, context);
    }

    /**
     * Use for setup inflate resource.
     */
    @Override
    protected View setupResource(LayoutInflater inflater) {
        return inflater.inflate(R.layout.cell_section_break, null);
    }

    /**
     * Use for setup value for each field in view resource.
     */
    @Override
    protected void setupView(BaseCellDataSource dataSource, View holder) {
    }

    /**
     * Use for setup action for this view such as Listener, Button action, etc.
     */
    @Override
    protected void setupAction() {
    }
}
