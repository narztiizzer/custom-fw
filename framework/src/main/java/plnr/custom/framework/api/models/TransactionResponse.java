package plnr.custom.framework.api.models;

import plnr.custom.framework.core.base.annotation.ColumnType;
import plnr.custom.framework.core.base.annotation.IQueryObject;
import plnr.custom.framework.core.base.annotation.JSONVariable;
import plnr.custom.framework.core.base.annotation.NullableType;
import plnr.custom.framework.core.base.annotation.Restriction;
import plnr.custom.framework.core.base.annotation.RestrictionType;
import plnr.custom.framework.core.base.model.BaseDateTime;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by ponlavitlarpeampaisarl on 3/30/15 AD.
 */
@IQueryObject(name = "webapi_transaction_response")
public class TransactionResponse extends BaseModel {

    @JSONVariable
    @IQueryObject(
            nullAble = NullableType.NOT_NULL,
            name = "tran_id",
            restriction = @Restriction(type = RestrictionType.OBJECT_REF),
            type = ColumnType.INTEGER
    )
    private Transaction ref_transaction;
    @JSONVariable
    @IQueryObject(
            nullAble = NullableType.NOT_NULL,
            name = "response_message",
            type = ColumnType.TEXT
    )
    private String responseMessage;
    @JSONVariable
    @IQueryObject(
            nullAble = NullableType.NOT_NULL,
            name = "code",
            type = ColumnType.TEXT
    )
    private String code;
    @JSONVariable
    @IQueryObject(
            nullAble = NullableType.NOT_NULL,
            name = "added_date",
            type = ColumnType.TEXT
    )
    private BaseDateTime addedDate;

    public Transaction getRef_transaction() {
        return ref_transaction;
    }

    public void setRef_transaction(Transaction ref_transaction) {
        this.ref_transaction = ref_transaction;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BaseDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(BaseDateTime addedDate) {
        this.addedDate = addedDate;
    }

    /**
     * This function is use for save the data from cell data source to model object
     *
     * @param list
     * @param row
     * @param dataSource data of the cell  @return is the save process success or not
     */
    @Override
    public boolean saveListHandler(BaseListAdapter list, int row, BaseCellDataSource dataSource) {
        return false;
    }

    @Override
    public boolean saveListHandler(BaseScrollListAdapter list, int row, BaseCellDataSource dataSource) {
        return false;
    }

    /**
     * This function is for validation before the save process
     *
     * @param dataSource data of the cell
     * @return is this save process should start or not
     */
    @Override
    public boolean shouldSaveListHandler(BaseCellDataSource dataSource) {
        return false;
    }

    /**
     * This function will provide the identification for the
     * object that use for fetch from the server
     * <p/>
     * e.g.
     * The mapping service point to www.example.com
     * getKeyForObjectIdentificationRequest = id
     * getObjectIdentificationRequest = 3
     * the final url is
     * www.example.com?id=3
     * for each object
     *
     * @return The identification of this object, null if there is no fetch service support
     */
    @Override
    public String getObjectIdentificationRequest() {
        return null;
    }

    /**
     * This function will provide the key for identification
     * that will send to server to fetch object
     * <p/>
     * e.g.
     * The mapping service point to www.example.com
     * getKeyForObjectIdentificationRequest = id
     * getObjectIdentificationRequest = 3
     * the final url is
     * www.example.com?id=3
     * for each object
     *
     * @return The key for identification, null if there is no fetch service support
     */
    @Override
    public String getKeyForObjectIdentificationRequest() {
        return null;
    }
}
