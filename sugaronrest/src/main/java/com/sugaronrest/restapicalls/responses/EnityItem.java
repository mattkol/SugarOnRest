package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created by kolao_000 on 2016-12-24.
 */
public class EnityItem {

    private String name = new String();

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String value) {
        name = value;
    }

    private Object valueObj = new Object();

    @JsonGetter("value")
    public Object getValue() {
        return valueObj;
    }

    @JsonSetter("value")
    public void setValue(Object value) {
        valueObj = value;
    }
}
