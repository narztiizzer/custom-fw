package plnr.custom.framework.repository.struct;

import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.repository.db.Table;
import plnr.custom.framework.repository.intf.IObjectRepository;

/**
 * Created by ponlavitlarpeampaisarl on 3/10/15 AD.
 */
public class DeleteObjectStruct {
    IObjectRepository repository;
    Class classStruct;

    public DeleteObjectStruct(Class classStruct, IObjectRepository repository) {
        this.classStruct = classStruct;
        this.repository = repository;
    }

    public boolean delete(BaseModel target) {
        return repository.executeDelete(target, Table.Builder(target.getClass()));
    }

    public boolean deleteTable() {
        return repository.executeDeleteTable(Table.Builder(this.classStruct));
    }

    public boolean deleteObject(BaseModel target) {
        return repository.executeDelete(target, Table.Builder(classStruct));
    }
}
