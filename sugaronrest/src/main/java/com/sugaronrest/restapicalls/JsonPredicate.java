package com.sugaronrest.restapicalls;

import com.sugaronrest.QueryOperator;

import java.lang.reflect.Type;



/**
 * Represents JsonPredicate class.
 */
public class JsonPredicate {
    /**
     * Initializes a new instance of the QueryPredicate class.
     *
     *  @param jsonName The property name. This can be a C# model property name or json property name.
     *  @param queryOperator The query operator.
     *  @param value The predicate value.
     *  @param fromValue The predicate from value.
     *  @param toValue The predicate to value.
     */
    public JsonPredicate(String jsonName, QueryOperator queryOperator, Object value, Object fromValue, Object toValue, boolean isNumeric)  {
        this.jsonName = jsonName;
        this.operator = queryOperator;
        this.value = value;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.isNumeric = isNumeric;
    }

    public String jsonName;
    public QueryOperator operator;
    public Object value;
    public Object fromValue;
    public Object toValue;
    public boolean isNumeric;
}


