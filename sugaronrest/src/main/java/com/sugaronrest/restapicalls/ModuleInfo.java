/**
 MIT License

 Copyright (c) 2017 Kola Oyewumi

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.sugaronrest.restapicalls;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ModuleInfo {

    final static String PackageName = "com.sugaronrest.modules";
    final static String PackagePath = "com/sugaronrest/modules";

    /**
     * Gets or sets the SugarCRM module name.
     */
    public String name;

    /**
     * Gets or sets the SugarCRM json module name.
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
    public List<ModuleProperty> modelProperties;

    /**
     * Creates ModuleInfo object based or module type or SugarCRM module name.
     * 
     * @param type The Java Pojo module type (e.g Accounts.class).
     * @param moduleName The SugarCRM module name.
     * @return The created ModuleInfo object.
     * @throws Exception
     */
    public static ModuleInfo create(Type type, String moduleName) throws Exception {
        ModuleInfo moduleInfo = null;
        if (type != null) {
            moduleInfo = readByType(type);
        }
        if ((moduleInfo == null) && StringUtils.isNotBlank(moduleName)) {
            moduleInfo = readByName(moduleName);
        }

        return moduleInfo;
    }

    /**
     * Gets Java module class from SugarCRM module name.
     *
     * @param moduleName The SugarCRM module name.
     * @return The Java class type.
     */
    public static Class getClassFromName(String moduleName) {
        try {

            ClassLoader classLoader = ModuleInfo.class.getClassLoader();
            return Class.forName(PackageName + "." + moduleName, false, classLoader);

        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Gets json property names of Java module.
     *
     * @return List of json property names.
     */
    public List<String> getJsonPropertyNames() {
        List<String> jsonNames = new ArrayList<String>();

        if ((modelProperties == null) || (modelProperties.size() == 0)) {
            return jsonNames;
        }

        for (ModuleProperty property : modelProperties) {
            jsonNames.add(property.jsonName);
        }

        return jsonNames;
    }

    /**
     *  Gets class name (module name) from Java class type.
     *
     * @param type The Java type.
     * @return Class name.
     */
    public static String getClassName(Type type) {
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

    /**
     * Gets the module model info object.
     *
     *  @param type The module model type.
     *  @return The model info object.
     */
    private static ModuleInfo readByType(Type type) throws Exception {
        ModuleInfo moduleInfo = new ModuleInfo();

        Class<?> moduleClass = (Class<?>) type;

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

        List<ModuleProperty> modelProperties = getFieldAnnotations(moduleClass);
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
        Class moduleClass = getClassFromName(moduleName);
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

        List<ModuleProperty> modelProperties = getFieldAnnotations(moduleClass);
        moduleInfo.modelProperties = modelProperties;

        return moduleInfo;
    }

    /**
     * Gets json names from module fields annotations.
     *
     * @param type The Java module type.
     * @return List of json property names.
     */
    private static List<ModuleProperty> getFieldAnnotations(Class type) {
        List<ModuleProperty> modelProperties = new ArrayList<ModuleProperty>();
        Field[] fields = type.getDeclaredFields();
         for(Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();

            for(Annotation annotation : annotations){
                if(annotation instanceof JsonProperty){
                    JsonProperty property = (JsonProperty) annotation;
                    ModuleProperty moduleProperty = new ModuleProperty();
                    moduleProperty.name = field.getName();
                    moduleProperty.jsonName = property.value();
                    moduleProperty.type = field.getType();
                    moduleProperty.isNumeric = isTypeNumeric(field.getType());
                    modelProperties.add(moduleProperty);
                }
            }
        }

        return modelProperties;
    }

    /**
     *  Checks if field is numeric or not.
     *
     * @param type Field type.
     * @return True or false.
     */
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


