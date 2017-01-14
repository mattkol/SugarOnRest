package com.sugarcrm.pojogen;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a database table.
 */
public class Table
{
    private String schema = new String();
    private String name = new String();
    private String cleanName = new String();
    private String className = new String();
    private boolean isView = false;
    private boolean ignore = false;
    private List<Column> columns = new ArrayList<Column>();
    private List<String> extraPackages = new ArrayList<String>();

    /**
     * Gets or sets list of columns.
     */
    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> value) {
        columns = value;
    }

    public List<String> getExtraPackages() {
        return extraPackages;
    }

    public void setExtraPackages(List<String> value) {
        extraPackages = value;
    }

    /**
     * Gets or sets the table name.
     */
    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    /**
     * Gets or sets database scheman name.
     */
    public String getSchema() {
        return schema;
    }

    public void setSchema(String value) {
        schema = value;
    }

    /**
     * Gets or sets a value indicating whether table is a view.
     */
    public boolean getIsView() {
        return isView;
    }

    public void setIsView(boolean value) {
        isView = value;
    }

    /**
     * Gets or sets cleaned table name.
     */
    public String getCleanName() {
        return cleanName;
    }

    public void setCleanName(String value) {
        cleanName = value;
    }

    /**
     * Gets or sets class name derived from table name.
     */
    public String getClassName() {
        return className;
    }

    public void setClassName(String value) {
        className = value;
    }

    /**
     * Gets or sets a value indicating whether table is to be ignored in mapping.
     */
    public boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(boolean value) {
        ignore = value;
    }

    /**
     * Gets the primary key column.
     */
    public Column getPk() {
        for (Column column : columns) {
            boolean isPrimaryKey = column.getIsPk();
            if (isPrimaryKey) {
                return column;
            }
        }

        return null;
    }

    /**
     * Gets column object based on column name
     *
     *  @param columnName Table column name
     *  @return Column object
     */
    public Column getColumn(String columnName) {

        for (Column column : columns) {
            String itemColumnName = column.getName();
            if (itemColumnName.equals(columnName)) {
                return column;
            }
        }

        return null;
    }
}


