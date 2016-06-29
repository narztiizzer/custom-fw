package plnr.custom.framework.repository.struct;

import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.repository.db.Table;
import plnr.custom.framework.repository.intf.IObjectRepository;

/**
 * Created by ponlavitlarpeampaisarl on 3/6/15 AD.
 */
public class InsertObjectStruct {
    IObjectRepository repository;
    Class classStruct;

    public InsertObjectStruct(Class classStruct, IObjectRepository repository) {
        this.classStruct = classStruct;
        this.repository = repository;
    }

    public long asNewItem(BaseModel target) {
        return repository.executeInsert(target, Table.Builder(classStruct));
    }

}
