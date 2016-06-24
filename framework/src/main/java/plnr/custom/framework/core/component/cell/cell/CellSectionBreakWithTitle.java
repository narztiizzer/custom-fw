package plnr.custom.framework.core.component.cell.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import plnr.custom.framework.R;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseCellView;
import plnr.custom.framework.core.component.cell.datasource.CellSectionBreakWithTitleDataSource;
import plnr.custom.framework.helper.ScreenSize;

/**
 * Created by ponlavitlarpeampaisarl on 2/6/15 AD.
 */
public class CellSectionBreakWithTitle extends BaseCellView {

    private TextView tv;
    private LinearLayout ll;

    /**
     * Simple constructor to use when creating a view from code.
     * @param dataSource model for cell
     * @param context context from adapter
     */
    public CellSectionBreakWithTitle(BaseCellDataSource dataSource, Context context) {
        super(dataSource, context);
    }

    /**
     * Use for setup inflate resource.
     */
    @Override
    protected View setupResource(LayoutInflater inflater) {
        return inflater.inflate(R.layout.cell_section_with_title, null);
    }

    /**
     * Use for setup value for each field in view resource.
     */
    @Override
    protected void setupView(BaseCellDataSource dataSource, View holder) {
        CellSectionBreakWithTitleDataSource ds = (CellSectionBreakWithTitleDataSource) getDataSource();
        this.tv = (TextView) holder.findViewById(R.id.cell_section_with_title__text_view__title);
        this.ll = (LinearLayout) holder.findViewById(R.id.cell_section_with_title__ll);
        this.ll.getLayoutParams().width = ScreenSize.getScreenWidth();
        this.tv.setText(ds.title);
        this.tv.setTextColor(ds.titleTextColor);
    }

    /**
     * Use for setup action for this view such as Listener, Button action, etc.
     */
    @Override
    protected void setupAction() {
        this.ll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
