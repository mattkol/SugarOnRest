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

public class Column
{
    /**
     * Gets or sets name of the column name.
     */
    public String getName() {
        return name;
    }
    public void setName(String value) {
        name = value;
    }

    /**
     * Gets or sets name of the class property name.
     */
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String value) {
        propertyName = value;
    }

    /**
     * Gets backing field name.
     */
    public String getBackingFieldName() {
        return Character.toLowerCase(propertyName.charAt(0)) + propertyName.substring(1);
    }

    /**
     * Gets or sets name of the class property type.
     */
    public String getPropertyType() {
        return propertyType;
    }
    public void setPropertyType(String value) {
        propertyType = value;
    }

    /**
     * Gets or sets a value indicating whether column is a primary key column.
     */
    public boolean getIsPk() {
        return isPk;
    }
    public void setIsPk(boolean value) {
        isPk = value;
    }

    /**
     * Gets or sets a value indicating whether column is nullable.
     */
    public boolean getIsNullable() {
        return isNullable;
    }
    public void setIsNullable(boolean value) {
        isNullable = value;
    }

    /**
     * Gets or sets a value indicating whether column is an autocrement.
     */
    public boolean getIsAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(boolean value) {
        isAutoIncrement = value;
    }

    /**
     * Gets or sets a value indicating whether column should be ignored in mapping.
     */
    public boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(boolean value) {
        ignore = value;
    }

    private String name;
    private String propertyName;
    private String propertyType;
    private boolean isPk;
    private boolean isNullable;
    private boolean isAutoIncrement;
    private boolean ignore;
}


