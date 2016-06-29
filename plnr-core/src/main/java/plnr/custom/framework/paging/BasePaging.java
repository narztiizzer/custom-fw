package plnr.custom.framework.paging;

import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.paging.intf.iPagingNotification;

/**
 * Created by nattapongr on 10/26/15 AD.
 */
public class BasePaging {
    private static BasePaging ourInstance;
    private iPagingNotification callBack;
    private BaseArrayList<BaseModel> listPaging;
    private int numberPerGet;
    private boolean isFinish;
    private boolean isInitialPagingSuccess;
    private int count = 0;

    public static BasePaging getInstance() {
        if (ourInstance == null) {
            ourInstance = new BasePaging();
        }
        return ourInstance;
    }

    public boolean initialPaging(iPagingNotification callBack, int numberPerGet, BaseArrayList<BaseModel> listPaging) {
        this.listPaging = listPaging;
        this.callBack = callBack;
        this.numberPerGet = numberPerGet;
        this.isFinish = false;
        if (callBack != null && this.listPaging != null && numberPerGet > 0)
            isInitialPagingSuccess = true;
        return isInitialPagingSuccess;
    }

    public void loadLogUsingPaging() {
        if (isInitialPagingSuccess) {
            BaseArrayList<BaseModel> list = BaseArrayList.Builder(BaseModel.class);
            int i = 0;
            for (; i < numberPerGet; i++) {
                if (count + i < listPaging.size())
                    list.add(listPaging.get(count + i));
                else this.isFinish = true;
            }
            this.count += i;
            callBack.fetchedList(list, isFinish);
        }
    }
}
