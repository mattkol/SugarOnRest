/**
 MIT License

 Copyright (c) 2017 Kola Oyewumi

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.sugarcrm.pojogen;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a database table.
 */
public class Table
{
    /**
     * Gets or sets list of columns.
     */
    public List<Column> getColumns() {
        return columns;
    }
    public void setColumns(List<Column> value) {
        columns = value;
    }

    /**
     * Gets or sets the extra packing info for the Java module class (created based on the table).
     */
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

    private String schema;
    private String name;
    private String cleanName;
    private String className;
    private boolean isView;
    private boolean ignore;
    private List<Column> columns;
    private List<String> extraPackages;
}


