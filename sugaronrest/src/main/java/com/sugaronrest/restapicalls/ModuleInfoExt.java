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

import com.sugaronrest.QueryPredicate;
import com.sugaronrest.utils.ModuleMapper;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.util.*;


public class ModuleInfoExt {

    /**
     * Gets modules linked information.
     *
     *  @param linkedModules The module linked info list.
     *  @return Dictionary map of linked modules.
     */
    public static List<Object> getJsonLinkedInfo(Map<Object, List<String>> linkedModules) throws Exception {
        if ((linkedModules == null) || (linkedModules.size() == 0))
        {
            return null;
        }

        Map<String, List<String>> linkedInfo = getLinkedInfo(linkedModules);

        if ((linkedInfo == null) || (linkedInfo.size() == 0))
        {
            return null;
        }

        List<Object> jsonLinkedInfoList = new ArrayList<Object>();

        for (Map.Entry<String, List<String>> entry : linkedInfo.entrySet()) {

            Map<String, Object> namevalueMap = new HashMap<String, Object>();
            namevalueMap.put("name", entry.getKey());
            namevalueMap.put("value", entry.getValue());

            jsonLinkedInfoList.add(namevalueMap);
        }

        return jsonLinkedInfoList;
    }

    /**
     * Gets query based on either query predicates or raw query.
     *
     * @param moduleInfo SugarCRM module info.
     * @param queryPredicates The query predicate collection.
     * @param queryString Formatted query string.
     * @return The formatted query.
     */
    public static String getQuery(ModuleInfo moduleInfo, List<QueryPredicate> queryPredicates, String queryString) {
        if (StringUtils.isNotBlank(queryString))
        {
            return " " + queryString.trim() + " ";
        }

        List<JsonPredicate> jsonPredicates = getJsonPredicates(moduleInfo, queryPredicates);

        return QueryBuilder.getWhereClause(jsonPredicates);
    }

    /**
     * Gets modules linked information.
     *
     *  @param linkedModules The module linked info list.
     *  @return Dictionary map of linked modules.
     */
    private static Map<String, List<String>> getLinkedInfo(Map<Object, List<String>> linkedModules) throws Exception {
        if ((linkedModules == null) || (linkedModules.size() == 0))
        {
            return null;
        }

        Map<String, List<String>> jsonLinkedInfo = new HashMap<String, List<String>>();
        for (Map.Entry<Object, List<String>> entry : linkedModules.entrySet()) {
            Object key = entry.getKey();
            List<String> jsonPropertyNames = entry.getValue();
            ModuleInfo linkedModuleInfo = null;
            String jsonModuleName = StringUtils.EMPTY;

            if (key instanceof String) {
                linkedModuleInfo = ModuleInfo.create(null, key.toString());
                if (linkedModuleInfo != null) {
                    if ((jsonPropertyNames == null) || (jsonPropertyNames.size() == 0)) {
                        jsonPropertyNames = linkedModuleInfo.getJsonPropertyNames();
                    }
                    jsonModuleName = linkedModuleInfo.jsonName;
                }
                if (!StringUtils.isNotBlank(jsonModuleName)) {
                    jsonModuleName = ModuleMapper.getInstance().getTablename(key.toString());
                }

            } else if (key instanceof Type) {
                // This key is a class type (e.g Accounts.class)
                linkedModuleInfo = ModuleInfo.create((Type)key, null);
                if (linkedModuleInfo != null) {
                    if ((jsonPropertyNames == null) || (jsonPropertyNames.size() == 0)) {
                        jsonPropertyNames = linkedModuleInfo.getJsonPropertyNames();
                    }
                    jsonModuleName = linkedModuleInfo.jsonName;
                }

                if (!StringUtils.isNotBlank(jsonModuleName)) {
                    String className = ModuleInfo.getClassName((Type)key);
                    jsonModuleName = ModuleMapper.getInstance().getTablename(className);
                }
            } else {
                continue;
            }

            jsonLinkedInfo.put(jsonModuleName, jsonPropertyNames);
        }

        return jsonLinkedInfo;
    }

    /**
     * Converts Java query predicate object collection to json query predicate collection.
     *
     * @param moduleInfo SugarCRM module info.
     * @param queryPredicates The Java query predicate object collection.
     * @return The json predicate collection.
     */
    private static List<JsonPredicate> getJsonPredicates(ModuleInfo moduleInfo, List<QueryPredicate> queryPredicates) {
        if ((queryPredicates == null) || (queryPredicates.size() == 0))
        {
            return null;
        }

        List<JsonPredicate> jsonPredicates = new ArrayList<JsonPredicate>();
        List<ModuleProperty> modelProperties = moduleInfo.modelProperties;

        for (QueryPredicate item : queryPredicates){
            ModuleProperty moduleProperty = getProperty(modelProperties, item.getPropertyName());
            if (moduleProperty != null)
            {
                String jsonName = moduleInfo.jsonName + "." + moduleProperty.jsonName;
                boolean isNumeric = moduleProperty.isNumeric;
                String value = getFormattedValue(item.getValue(), isNumeric);
                String fromValue = getFormattedValue(item.getFromValue(), isNumeric);
                String toValue = getFormattedValue(item.getToValue(), isNumeric);

                jsonPredicates.add(new JsonPredicate(jsonName, item.getOperator(), value, fromValue, toValue, isNumeric));
            }
        }

        return jsonPredicates;
    }

    /**
     *  Gets the formatted query value considering whether it is a numeric value or not.
     *
     * @param value The value to format.
     * @param isNumeric Boolean value to know if it is numeric or not.
     * @return The formatted query value.
     */
    private static String getFormattedValue(Object value, boolean isNumeric) {
        if (value == null) {
            return null;
        }

        Object[] objList = null;

        if(value instanceof ArrayList<?>) {
            objList = ((ArrayList<?>)value).toArray();
        } else if (value instanceof Object[]){
            objList = (Object[])value;
        }

        if(objList != null) {
            try {
                List<String> strValueList = new ArrayList<String>();
                String current = StringUtils.EMPTY;

                for (Object obj : objList) {
                    if (isNumeric) {
                        current = obj.toString();
                        strValueList.add(current);
                    }
                    else {
                        current = "\'" + obj.toString() + "\'";
                        strValueList.add(current);
                    }
                }

                return StringUtils.join(strValueList, ",");
            }
            catch (Exception exception) {
            }
        }
        return value.toString();
    }

    /**
     *  Gets the Pojo property object based on json module name.
     *
     * @param modelProperties The json property name collection of module.
     * @param name The json property name parameter.
     * @return Module property object.
     */
    private static ModuleProperty getProperty(List<ModuleProperty> modelProperties, String name) {
        if ((modelProperties == null) || (modelProperties.size() ==0)) {
            return null;
        }

        for (ModuleProperty property : modelProperties) {
            if (property.jsonName.equalsIgnoreCase(name)) {
                return property;
            }
        }

        return null;
    }

}
