package com.sugaronrest.utils;

/**
 * Created by kolao_000 on 2016-12-26.
 */
public class Utils {

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
