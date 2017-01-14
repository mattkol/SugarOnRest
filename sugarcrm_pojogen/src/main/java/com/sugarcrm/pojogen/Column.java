package com.sugarcrm.pojogen;

/**
 * This class represents a table column.
 */
public class Column
{
    /**
     * Gets or sets name of the column name.
     */
    private String name = new String();
    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    /**
     * Gets or sets name of the class property name.
     */
    private String propertyName = new String();
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String value) {
        propertyName = value;
    }

    public String getBackingFieldName() {
        return Character.toLowerCase(propertyName.charAt(0)) + propertyName.substring(1);
    }

    /**
     * Gets or sets name of the class property type.
     */
    private String propertyType = new String();
    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String value) {
        propertyType = value;
    }

    /**
     * Gets or sets a value indicating whether column is a primary key column.
     */
    private boolean isPk = false;
    public boolean getIsPk() {
        return isPk;
    }

    public void setIsPk(boolean value) {
        isPk = value;
    }

    /**
     * Gets or sets a value indicating whether column is nullable.
     */
    private boolean isNullable = false;
    public boolean getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(boolean value) {
        isNullable = value;
    }

    /**
     * Gets or sets a value indicating whether column is an autocrement.
     */
    private boolean isAutoIncrement = false;
    public boolean getIsAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(boolean value) {
        isAutoIncrement = value;
    }

    /**
     * Gets or sets a value indicating whether column should be ignored in mapping.
     */
    private boolean ignore = false;
    public boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(boolean value) {
        ignore = value;
    }
}


