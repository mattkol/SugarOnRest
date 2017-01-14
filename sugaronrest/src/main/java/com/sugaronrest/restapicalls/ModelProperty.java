package com.sugaronrest.restapicalls;

import java.lang.reflect.Type;

/**
 * This class represents ModelProperty class.
 */
public class ModelProperty {
    /**
     * Gets or sets the Java property name.
     */
    public String name;

    /**
     * Gets or sets the property name in json.
     */
    public String jsonName;

    /**
     * Gets or sets property Java object type.
     */
    public Class type;

    /**
     * Gets or sets a value indicating whether value is numeric or not.
     */
    public boolean isNumeric;
}


