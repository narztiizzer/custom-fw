package plnr.custom.framework.paging.intf;

import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseModel;

/**
 * Created by nattapongr on 10/26/15 AD.
 */
public interface iPagingNotification {
    public void fetchedList(BaseArrayList<BaseModel> list, boolean isFinish);
}
