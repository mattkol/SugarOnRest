package com.sugaronrest.restapicalls;

import com.sugaronrest.QueryPredicate;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by kolao_000 on 2017-01-07.
 */
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

    /// <summary>
    /// Gets query based on either query predicates or raw query.
    /// </summary>
    /// <param name="modelInfo">SugarCrm module info.</param>
    /// <param name="queryPredicates">The query predicate collection.</param>
    /// <param name="queryString">Formatted query string.</param>
    /// <returns>The formatted query.</returns>
    public static String GetQuery(ModuleInfo moduleInfo, List<QueryPredicate> queryPredicates, String queryString) {
        if (StringUtils.isNotBlank(queryString))
        {
            return " " + queryString.trim() + " ";
        }

        List<JsonPredicate> jsonPredicates = GetJsonPredicates(moduleInfo, queryPredicates);

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

            if (key instanceof String) {
                if ((jsonPropertyNames == null) || (jsonPropertyNames.size() == 0)) {
                    linkedModuleInfo = ModuleInfo.read(null, key.toString());
                    if (linkedModuleInfo == null) {
                        jsonPropertyNames = linkedModuleInfo.getJsonPropertyNames();
                    }
                }
                jsonLinkedInfo.put(key.toString(), jsonPropertyNames);
            } else if (key instanceof Type) {
                // This key is a class type (e.g Accounts.class)
                linkedModuleInfo = ModuleInfo.read((Type)key, null);
                if (linkedModuleInfo != null) {
                    if ((jsonPropertyNames == null) || (jsonPropertyNames.size() == 0)) {
                        jsonPropertyNames = linkedModuleInfo.getJsonPropertyNames();
                    }
                    jsonLinkedInfo.put(linkedModuleInfo.jsonName, jsonPropertyNames);
                }
            } else {
                continue;
            }
        }

        return jsonLinkedInfo;
    }

    /// <summary>
    /// Converts C# query predicate collection to json query predicate collection.
    /// The C# query can have a mixture of both C# property name name json property name.
    /// </summary>
    /// <param name="modelInfo">SugarCrm module info.</param>
    /// <param name="queryPredicates">The query predicate collection.</param>
    /// <returns>The json predicate collection.</returns>
    private static List<JsonPredicate> GetJsonPredicates(ModuleInfo moduleInfo, List<QueryPredicate> queryPredicates) {
        if ((queryPredicates == null) || (queryPredicates.size() == 0))
        {
            return null;
        }

        List<JsonPredicate> jsonPredicates = new ArrayList<JsonPredicate>();
        List<ModelProperty> modelProperties = moduleInfo.modelProperties;

        for (QueryPredicate item : queryPredicates){
            ModelProperty modelProperty = getProperty(modelProperties, item.getPropertyName());
            if (modelProperty != null)
            {
                String jsonName = moduleInfo.jsonName + "." + modelProperty.jsonName;
                boolean isNumeric = modelProperty.isNumeric;
                String value = GetFormattedValue(item.getValue(), isNumeric);
                String fromValue = GetFormattedValue(item.getFromValue(), isNumeric);
                String toValue = GetFormattedValue(item.getToValue(), isNumeric);

                jsonPredicates.add(new JsonPredicate(jsonName, item.getOperator(), value, fromValue, toValue, isNumeric));
            }
        }

        return jsonPredicates;
    }

    /// <summary>
    /// Gets the formatted query value considering whether it is a numeric value or not.
    /// </summary>
    /// <param name="value">The value to format.</param>
    /// <param name="isNumeric">Boolean value to know if it is numeric or not.</param>
    /// <returns>The formatted query value.</returns>
    private static String GetFormattedValue(Object value, boolean isNumeric) {
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

    private static ModelProperty getProperty(List<ModelProperty> modelProperties, String name) {
        if ((modelProperties == null) || (modelProperties.size() ==0)) {
            return null;
        }

        for (ModelProperty property : modelProperties) {
            if (property.jsonName.equalsIgnoreCase(name)) {
                return property;
            }
        }

        return null;
    }

}
