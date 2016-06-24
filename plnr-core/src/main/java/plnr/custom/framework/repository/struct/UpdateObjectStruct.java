package plnr.custom.framework.repository.struct;

import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.repository.db.Table;
import plnr.custom.framework.repository.intf.IObjectRepository;

/**
 * Created by ponlavitlarpeampaisarl on 3/10/15 AD.
 */
public class UpdateObjectStruct {
    IObjectRepository repository;
    Class classStruct;

    public UpdateObjectStruct(Class classStruct, IObjectRepository repository) {
        this.classStruct = classStruct;
        this.repository = repository;
    }

    public boolean asUpdateOrReplaceItem(BaseModel target) {
        return repository.executeUpdate(target, Table.Builder(target.getClass()));
    }
}
