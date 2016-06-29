package plnr.custom.framework.repository.struct;

import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.logger.model.LogFilter;
import plnr.custom.framework.repository.db.Table;
import plnr.custom.framework.repository.intf.IObjectRepository;

/**
 * Created by ponlavitlarpeampaisarl on 3/6/15 AD.
 */
public class SelectObjectStruct {
    IObjectRepository repository;
    Class target;

    public SelectObjectStruct(Class target, IObjectRepository repository) {
        this.repository = repository;
        this.target = target;
    }

    public SelectObjectStruct(IObjectRepository repository) {
        this.repository = repository;
    }

    public BaseModel getById(long id) {
        return repository.executeSelect(target, Table.Builder(target), id);
    }

    public BaseArrayList<BaseModel> getAll() {
        return repository.executeSelect(target, Table.Builder(target));
    }

    public BaseArrayList<BaseModel> getAll(String columnToSelect) {
        return repository.executeSelect(target, Table.Builder(target), columnToSelect);
    }

    public BaseArrayList<BaseModel> get(int limit, int offset) {
        return repository.executeSelect(target, Table.Builder(target), offset, limit);
    }

    public BaseArrayList<BaseModel> get(int limit) {
        return repository.executeSelect(target, Table.Builder(target), 0, limit);
    }

    public LogFilter getDistinct(Table[] tablesToSelect, String[] columnToselect) {
        return repository.executeSelectDistinct(tablesToSelect, columnToselect);
    }
}
