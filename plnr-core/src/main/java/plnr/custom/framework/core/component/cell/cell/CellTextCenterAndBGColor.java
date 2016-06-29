package plnr.custom.framework.core.component.cell.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import plnr.custom.framework.R;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseCellView;
import plnr.custom.framework.core.component.cell.datasource.CellTextCenterAndBGColorDataSource;


/**
 * Created by nattapongr on 18/6/15 AD.
 */
public class CellTextCenterAndBGColor extends BaseCellView {

    private CellTextCenterAndBGColorDataSource datasource;
    private TextView textTitle;
    private FrameLayout cellBackground;

    /**
     * Simple constructor to use when creating a view from code.
     */
    public CellTextCenterAndBGColor(BaseCellDataSource dataSource, Context context) {
        super(dataSource, context);
    }

    /**
     * Use for setup inflate resource.
     */
    @Override
    protected View setupResource(LayoutInflater inflater) {
        return inflater.inflate(R.layout.cell_text_center_and_bg_color, null);
    }

    /**
     * Use for setup value for each field in view resource.
     */
    @Override
    protected void setupView(BaseCellDataSource dataSource, View holder) {
        this.datasource = (CellTextCenterAndBGColorDataSource) dataSource;
        textTitle = (TextView) holder.findViewById(R.id.cell_text_center_and_bg_color_textview);
        cellBackground = (FrameLayout) holder.findViewById(R.id.cell_text_center_and_bg_color_background);

        textTitle.setText(datasource.getTitle());
        textTitle.setText(datasource.getTitleTextColor());

        switch (datasource.getBacgroungColor()) {
            case SOFT_BLUE : cellBackground.setBackground(getResources().getDrawable(R.drawable.round_rectangular_soft_blue));
                break;
            case LIGHT_GREEN: cellBackground.setBackground(getResources().getDrawable(R.drawable.round_rectangular_light_green));
                break;
            case GREEN_SOFT_BLUE: cellBackground.setBackground(getResources().getDrawable(R.drawable.round_rectangular_green_soft_blue));
                break;
            case SOFT_YELLOW: cellBackground.setBackground(getResources().getDrawable(R.drawable.round_rectangular_soft_yellow));
                break;
            case SOFT_RED: cellBackground.setBackground(getResources().getDrawable(R.drawable.round_rectangular_soft_red));
                break;
            default: cellBackground.setBackground(getResources().getDrawable(R.drawable.round_rectangular_default_gray));
        }

    }

    /**
     * Use for setup action for this view such as Listener, Button action, etc.
     */
    @Override
    protected void setupAction() {
        cellBackground.setOnClickListener(datasource.getOnViewClick());
    }
}
