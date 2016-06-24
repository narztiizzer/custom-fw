package plnr.custom.framework.logger.intf;

import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.logger.model.LogModel;

/**
 * Created by nattapongr on 10/29/15 AD.
 */
public interface iLoggerNotification {
    public void fetchedLogList(BaseArrayList<LogModel> list);
}
