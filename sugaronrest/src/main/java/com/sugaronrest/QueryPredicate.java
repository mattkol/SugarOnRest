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

package com.sugaronrest;


public class QueryPredicate {

    /**
     * Initializes a new instance of the QueryPredicate class.
     *
     *  @param propertyName The property name. This can be a C# model property name or json property name.
     *  @param queryOperator The query operator.
     *  @param value The predicate value.
     */
    public QueryPredicate(String propertyName, QueryOperator queryOperator, Object value)  {
        this.setPropertyName(propertyName);
        this.setOperator(queryOperator);
        this.setValue(value);
        this.setFromValue(null);
        this.setToValue(null);
    }

    /**
     * Initializes a new instance of the QueryPredicate class.
     *
     *  @param propertyName The json property name.
     *  @param queryOperator The query operator.
     *  @param value The predicate value.
     *  @param fromValue The predicate from value.
     *  @param toValue The predicate to value.
     */
    public QueryPredicate(String propertyName, QueryOperator queryOperator, Object value, Object fromValue, Object toValue)  {
        this.setPropertyName(propertyName);
        this.setOperator(queryOperator);
        this.setValue(value);
        this.setFromValue(fromValue);
        this.setToValue(toValue);
    }

    /**
     * Gets or sets the property name.
     */
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String value) {
        propertyName = value;
    }

    /**
     * Gets or sets the operator.
     */
    public QueryOperator getOperator() {
        return operator;
    }
    public void setOperator(QueryOperator value) {
        operator = value;
    }

    /**
     * Gets or sets the value.
     */
    public Object getValue() {
        return objValue;
    }
    public void setValue(Object value) {
        objValue = value;
    }

    /**
     * Gets or sets the from value.
     */
    public Object getFromValue() {
        return fromValue;
    }
    public void setFromValue(Object value) {
        fromValue = value;
    }

    /**
     * Gets or sets the to value.
     */
    public Object getToValue() {
        return toValue;
    }
    public void setToValue(Object value) {
        toValue = value;
    }

    private String propertyName;
    private QueryOperator operator;
    private Object objValue;
    private Object fromValue;
    private Object toValue;
}


