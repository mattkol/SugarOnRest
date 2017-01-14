package com.sugarcrm.pojogen.test;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kolao_000 on 2017-01-01.
 */
public class GenericTemplateTest {

    @Test
    public void generateModuleTest() throws InstantiationException, IllegalAccessException {

        List<Vehicle> books = new ArrayList<Vehicle>();
        books.add(new Vehicle());
        books.add(new Vehicle());
        books.add(new Vehicle());

        Library<Vehicle> bookLibrary = new Library<Vehicle>(books);

        for (Vehicle vehicle :  bookLibrary.items()){
            vehicle.service();
        }

        GenericsMethods genericsMethods = new GenericsMethods();
        boolean isEqual = genericsMethods.<String>isEqual(10, 10);

        try {
            boolean isEqual2 = genericsMethods.<String>isEqual();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        System.out.println(">>>>>Testing");
        String name = genericsMethods.execute(Vehicle.class);
        System.out.println("Name:" + name);
        System.out.println(genericsMethods.execute(Vehicle.class));
        try {
            System.out.println(bookLibrary.getType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public class GenericsMethods {

        //Java Generic Method
        public <T> boolean isEqual(int g1, int g2){
            return g1 == g2;
        }

        public <T> boolean isEqual() throws NoSuchMethodException {

            Method method = GenericsMethods.class.getMethod("isEqual");
            Type[] returnType = method.getTypeParameters();
            System.out.println("typeArgClass = " + returnType);
/*

            if(returnType instanceof ParameterizedType){
                ParameterizedType type = (ParameterizedType) returnType;
                Type[] typeArguments = type.getActualTypeArguments();
                for(Type typeArgument : typeArguments){
                    Class typeArgClass = (Class) typeArgument;
                    System.out.println("typeArgClass = " + typeArgClass);
                }
            }
*/
            return true;
        }

        public String execute(Type type){


            String typeToString =  type.toString();
            System.out.println(">>>>>Class");
            System.out.println("Name 111:" + typeToString);

            typeToString = typeToString.trim();
            String[] splitArray =  typeToString.split("\\.");
            System.out.println(">>>>>Class");
            System.out.println("Count 111:" + splitArray.length);
            if (splitArray.length > 0) {
                System.out.println(">>>>>Class");
                System.out.println("Name 222:" + splitArray[splitArray.length - 1]);
            }

            return type.getClass().getCanonicalName();
        }

    }

    public static <T> T addAndReturn(T element, Collection<T> collection){
        collection.add(element);
        return element;
    }

    public class Foo {
        Foo (String value) {
            name = value;
        }
        private String name;
        public String getName() {
            return name;
        }

        public void setName(String value) {
            name = value;
        }
    }

    @Test
    public void referenceTest() {
        Foo f = new Foo("Start");
        changeReference(f); // It won't change the reference!
        System.out.println("changeReference");
        System.out.println(f.getName());
        modifyReference(f); // It will modify the object that the reference variable "f" refers to!
        System.out.println("modifyReference");
        System.out.println(f.getName());
    }
    public   void changeReference(Foo a)
    {
        Foo b = new Foo("Change");
        a = b;
    }
    public  void modifyReference(Foo c)
    {
        c.setName("Modified");
    }
}
