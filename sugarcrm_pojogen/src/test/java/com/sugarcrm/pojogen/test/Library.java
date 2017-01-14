package com.sugarcrm.pojogen.test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolao_000 on 2017-01-01.
 */
public class Library<T> { // "T" is the Type parameter. We can create Library of any Type

    private Class<T> type;
    private List<T> items ; // represents the list of items of type we will pass

    public Library(List<T> items){

        this.items = items;
    }

    public List<T> items(){
        // write code to issue item.
        return items;
    }

    public T issueItem(){
        // write code to issue item.
        return items.get(0);
    }

    public void returnItem(T item){ // again pass the same "T" type to issueItem method
        items.add(item);
    }
    T propertyClass = null;


    public class T1<T0> {
        private Class classt;

        public T1() {
            ParameterizedType type=(ParameterizedType)this.getClass().getGenericSuperclass();
            this.classt = (Class) type.getActualTypeArguments()[0];
            System.out.println(this.classt);
        }
    }


    public class T2<T0>  extends T1<Vehicle> {
    }


    @SuppressWarnings("unchecked")
    public Object getType() throws NoSuchMethodException, NoSuchFieldException {

        GenericType<Vehicle> obj = new GenericType<Vehicle>(Vehicle.class);
        return obj.getType();
      //return "Done";
    }

}