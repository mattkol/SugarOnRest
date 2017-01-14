package com.sugaronrest.restapicalls;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents ModuleInfo class.
 */
public class ModuleInfo {

    final static String PackageName = "com.sugaronrest.modules";
    final static String PackagePath = "com/sugaronrest/modules";

    /**
     * Gets or sets the SugarCrm module name.
     */
    public String name;

    /**
     * Gets or sets the SugarCrm json module name.
     * This maps to the tablename in module annotation.
     */
    public String jsonName;

    /**
     * Gets or sets model C# object type.
     */
    public Class type;

    /**
     * Gets or sets model properties.
     */
    public List<ModelProperty> modelProperties;

    public static ModuleInfo read(Type type, String moduleName) throws Exception {
        ModuleInfo moduleInfo = null;
        if (type != null) {
            moduleInfo = readByType(type);
        }
        if ((moduleInfo == null) && StringUtils.isNotBlank(moduleName)) {
            moduleInfo = readByName(moduleName);
        }

        return moduleInfo;
    }

    public static String readNameFromType(Type type) throws IOException, ClassNotFoundException {

        String refClassName = getClassName(type);
        String concateNamedClassNames = new String();

        ClassLoader  classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(PackagePath);
        refClassName = refClassName.trim();

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();

            String className = new Scanner((InputStream)url.getContent()).useDelimiter("\\A").next();
            className = className.replace(".class", StringUtils.EMPTY);
            className = getClassName(className);
            concateNamedClassNames = className.trim();
        }

        String classNames[] = concateNamedClassNames.split("\\r\\n|\\n|\\r");
        if ((classNames != null) && (classNames.length > 0)) {
            for(String item : classNames){
                if (refClassName.equalsIgnoreCase(item.trim())) {
                    Class moduleClass = Class.forName(PackageName + "." + refClassName, false, classLoader);
                    Annotation[] moduleAnnotations = moduleClass.getAnnotations();
                    for(Annotation annotation : moduleAnnotations){
                        if(annotation instanceof Module){
                            Module module = (Module)annotation;
                            return module.name();
                        }
                    }
                    break;
                }
            }
        }

        return StringUtils.EMPTY;
    }

    public static Class readClassFromName(String moduleName) throws IOException, ClassNotFoundException {

        String concateNamedClassNames = new String();

        ClassLoader  classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(PackagePath);

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();

            String className = new Scanner((InputStream)url.getContent()).useDelimiter("\\A").next();
            className = className.replace(".class", StringUtils.EMPTY);
            className = getClassName(className);
            concateNamedClassNames = className.trim();
        }

        String classNames[] = concateNamedClassNames.split("\\r\\n|\\n|\\r");
        if ((classNames != null) && (classNames.length > 0)) {
            for(String item : classNames){
                Class moduleClass = Class.forName(PackageName + "." + item.trim(), false, classLoader);
                Annotation[] moduleAnnotations = moduleClass.getAnnotations();

                String currentName = StringUtils.EMPTY;
                for(Annotation annotation : moduleAnnotations){
                    if(annotation instanceof Module){
                        Module module = (Module)annotation;
                        currentName = module.name();
                    }
                }

                if (currentName.equalsIgnoreCase(moduleName)) {
                    return moduleClass;
                }
            }
        }

        return null;
    }

    public List<String> getJsonPropertyNames() {
        List<String> jsonNames = new ArrayList<String>();

        if ((modelProperties == null) || (modelProperties.size() == 0)) {
            return jsonNames;
        }

        for (ModelProperty property : modelProperties) {
            jsonNames.add(property.jsonName);
        }

        return jsonNames;
    }

    /**
     * Gets the module model info object.
     *
     *  @param type The module model type.
     *  @return The model info object.
     */
    private static ModuleInfo readByType(Type type) throws Exception {
        ModuleInfo moduleInfo = new ModuleInfo();

        String className = getClassName(type);
        ClassLoader  classLoader = Thread.currentThread().getContextClassLoader();
        Class moduleClass = Class.forName(PackageName + "." + className, false, classLoader);

        String moduleName = null;
        String jsonModuleName = null;



        Annotation[] moduleAnnotations = moduleClass.getAnnotations();
        for(Annotation annotation : moduleAnnotations){
            if(annotation instanceof Module){
                Module module = (Module)annotation;
                moduleName = module.name();
                jsonModuleName = module.tablename();
            }
        }


        if ((moduleName == null) || (jsonModuleName == null)) {
            return null;
        }

        moduleInfo.name = moduleName;
        moduleInfo.jsonName = jsonModuleName;
        moduleInfo.type = moduleClass;

        List<ModelProperty> modelProperties = getFieldAnnotations(moduleClass);
        moduleInfo.modelProperties = modelProperties;

        return moduleInfo;
    }

    /**
     * Gets the module model info object.
     *
     *  @param moduleName The module model name.
     *  @return The model info object.
     */
    private static ModuleInfo readByName(String moduleName) throws Exception {
        ModuleInfo moduleInfo = new ModuleInfo();
        Class moduleClass = readClassFromName(moduleName);
        String jsonModuleName = null;

        if (moduleClass ==null) {
            moduleInfo.name = moduleName;
            return moduleInfo;
        }

        Annotation[] moduleAnnotations = moduleClass.getAnnotations();
        for(Annotation annotation : moduleAnnotations){
            if(annotation instanceof Module){
                Module module = (Module)annotation;
                moduleName = module.name();
                jsonModuleName = module.tablename();
            }
        }

        if ((moduleName == null) || (jsonModuleName == null)) {
            return null;
        }

        moduleInfo.name = moduleName;
        moduleInfo.jsonName = jsonModuleName;
        moduleInfo.type = moduleClass;

        List<ModelProperty> modelProperties = getFieldAnnotations(moduleClass);
        moduleInfo.modelProperties = modelProperties;

        return moduleInfo;
    }

    private static List<ModelProperty> getFieldAnnotations(Class type) {
        List<ModelProperty> modelProperties = new ArrayList<ModelProperty>();
        Field[] fields = type.getDeclaredFields();
         for(Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();

            for(Annotation annotation : annotations){
                if(annotation instanceof JsonProperty){
                    JsonProperty property = (JsonProperty) annotation;
                    ModelProperty modelProperty = new ModelProperty();
                    modelProperty.name = field.getName();
                    modelProperty.jsonName = property.value();
                    modelProperty.type = field.getType();
                    modelProperty.isNumeric = isTypeNumeric(field.getType());
                    modelProperties.add(modelProperty);
                }
            }
        }

        return modelProperties;
    }

    private static String getClassName(Type type) {
        if (type == null) {
            return StringUtils.EMPTY;
        }
        String typeToString =  type.toString();
        typeToString = typeToString.trim();
        String[] splitArray = typeToString.split("\\.");
        if (splitArray.length > 0) {
            return splitArray[splitArray.length - 1];
        }

        return StringUtils.EMPTY;
    }

    private static String getClassName(String moduleName) {
        if (!StringUtils.isNotBlank(moduleName)) {
            return StringUtils.EMPTY;
        }

        moduleName = moduleName.trim();
        String[] splitArray = moduleName.split("\\.");
        if (splitArray.length > 0) {
            return splitArray[splitArray.length - 1];
        }

        return moduleName;
    }

    public static boolean isTypeNumeric(Class type) {
        if (type == null)
        {
            return false;
        }

        if (type.getSuperclass() == null)
        {
            return false;
        }

        return  type.getSuperclass().equals(Number.class);
    }
}


