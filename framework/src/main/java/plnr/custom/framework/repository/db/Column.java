package plnr.custom.framework.repository.db;

import plnr.custom.framework.core.base.annotation.ColumnType;

/**
 * Created by ponlavitlarpeampaisarl on 3/9/15 AD.
 */
public class Column {
    public ColumnType type;
    public String name;
    public String mappingName;
    public int length;
    public boolean isPrimaryKey;
    public boolean isNullable;
    public boolean isReferenceObject = false;
}
