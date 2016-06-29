package plnr.custom.framework.repository;

import android.util.Log;

import java.util.ArrayList;

import plnr.custom.framework.core.base.annotation.IQueryObject;
import plnr.custom.framework.core.base.exception.UnsupportedClassException;
import plnr.custom.framework.repository.db.Table;
import plnr.custom.framework.repository.intf.IObjectRepository;
import plnr.custom.framework.repository.struct.DeleteObjectStruct;
import plnr.custom.framework.repository.struct.InsertObjectStruct;
import plnr.custom.framework.repository.struct.SelectObjectStruct;
import plnr.custom.framework.repository.struct.UpdateObjectStruct;

/**
 * Created by ponlavitlarpeampaisarl on 3/6/15 AD.
 */
public class ObjectFactory {
    private static ObjectFactory instance;
    private ArrayList<Class> queryObjectList;
    private IObjectRepository repository;

    protected static void checkIQueryObject(Class target) {
        if (!target.isAnnotationPresent(IQueryObject.class))
            throw new UnsupportedClassException(target.getName() + " class must have IQueryObject Annotation");
    }

    public static SelectObjectStruct select(Class object) {
        checkIQueryObject(object);
        isContainClass(object);
        return new SelectObjectStruct(object, getInstance().repository);
    }

    public static SelectObjectStruct select() {
        return new SelectObjectStruct(getInstance().repository);
    }

    public static InsertObjectStruct insert(Class object) {
        checkIQueryObject(object);
        isContainClass(object);
        return new InsertObjectStruct(object, getInstance().repository);
    }

    public static UpdateObjectStruct update(Class object) {
        checkIQueryObject(object);
        isContainClass(object);
        return new UpdateObjectStruct(object, getInstance().repository);
    }

    public static DeleteObjectStruct delete(Class object) {
        checkIQueryObject(object);
        isContainClass(object);
        return new DeleteObjectStruct(object, getInstance().repository);
    }

    public static void register(Class target) {
        checkIQueryObject(target);
        if (getInstance().queryObjectList == null)
            getInstance().queryObjectList = new ArrayList<>();
        if (getInstance().queryObjectList.contains(target)) {
            new RuntimeException("This class " + target.getName() + " already register", null);
        }

        Table table = Table.Builder(target);
        getInstance().repository.registerTable(target, table);
        Log.d("Repository", "------ Register Table : " + table.tableName + "------");
        getInstance().queryObjectList.add(target);
    }


    public synchronized static ObjectFactory getInstance() {
        if (instance == null) {
            instance = new ObjectFactory();
        }
        return instance;
    }

    public static synchronized void initiate(IObjectRepository repo) {
        getInstance().repository = repo;
    }

    public static synchronized void isContainClass(Class target) {
        if (!getInstance().queryObjectList.contains(target))
            new RuntimeException("Table " + target.getName() + " Not found.");
    }
}
