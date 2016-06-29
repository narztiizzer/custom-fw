package split.tiizzer.narz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseCellView;

/**
 * Created by NARZTIIZZER on 6/23/2016.
 */
public class CellEditText extends BaseCellView {

    private EditText edt;
    private Button btn;

    public CellEditText(BaseCellDataSource dataSource, Context context) {
        super(dataSource, context);
    }

    @Override
    protected View setupResource(LayoutInflater inflater) {
        return inflater.inflate(R.layout.cell_edittext_layout , null);
    }

    @Override
    protected void setupView(BaseCellDataSource dataSource, View holder) {
        edt = (EditText) holder.findViewById(R.id.edtMessage);
        btn = (Button) holder.findViewById(R.id.bthShowMessage);
    }

    @Override
    protected void setupAction() {
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt.getText().length() > 0)
                    Toast.makeText(getContext() , edt.getText().toString() , Toast.LENGTH_LONG).show();
            }
        });
    }
}
