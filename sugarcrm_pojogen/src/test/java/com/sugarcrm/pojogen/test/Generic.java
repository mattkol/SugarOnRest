package com.sugarcrm.pojogen.test;

import java.beans.Transient;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by kolao_000 on 2017-01-01.
 */
public abstract class Generic<T extends Generic> {
    protected Class<T> entityClass;

    public Generic() {
        Class obtainedClass = this.getClass();
        Type genericSuperclass = null;
        System.out.println(">>>>>>>Before");
        System.out.println(obtainedClass);
        genericSuperclass = obtainedClass.getGenericSuperclass();
        ParameterizedType genericSuperclass_ = (ParameterizedType) genericSuperclass;
        entityClass = ((Class) ((Class) genericSuperclass_.getActualTypeArguments()[0]));
    }
}
