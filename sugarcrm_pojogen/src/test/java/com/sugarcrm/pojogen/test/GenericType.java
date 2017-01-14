package com.sugarcrm.pojogen.test;

/**
 * Created by kolao_000 on 2017-01-01.
 */
public class GenericType<T> {
    private Class<T> type;
    public GenericType(Class<T> value)
    {
        type= value;
    }


    Class<T> getType(){return type;}
}
