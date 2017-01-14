package com.sugaronrest;

/**
 * Represents QueryPredicate class.
 */
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
     *  @param propertyName The property name. This can be a C# model property name or json property name.
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


