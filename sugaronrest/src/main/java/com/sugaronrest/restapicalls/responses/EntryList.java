package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.utils.JsonObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents EntryList class
 */
@JsonRootName(value = "entry_list")
public class EntryList
{
    /**
     * Gets or sets the entity identifier
     */
    private String id = new String();

    @JsonGetter("id")
    public String getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(String value) {
        id = value;
    }

    /**
     * Gets or sets the entity module name
     */
    private String moduleName = new String();

    @JsonGetter("module_name")
    public String getModuleName() {
        return moduleName;
    }

    @JsonSetter("module_name")
    public void setModuleName(String value) {
        moduleName = value;
    }

    /**
     * Gets or sets the returned entity name value list
     */
    private Object nameValueList;

    @JsonGetter("name_value_list")
    public Object getNameValueList() {
        return nameValueList;
    }

    @JsonSetter("name_value_list")
    public void setNameValueList(Map<String, Object> value) {
        nameValueList = value;
    }

    @JsonIgnore
    public Object getEntity() throws IOException {
        if (nameValueList == null) {
            return new LinkedHashMap<String,Object>();
        }

        Map<String, Object> entity = new HashMap<String,Object>();
        ObjectMapper mapper = JsonObjectMapper.getMapper();

        Map<String, Object> nameValueMap = (Map<String, Object>)nameValueList;
        for (Map.Entry<String, Object> entry : nameValueMap.entrySet()) {
            String key = entry.getKey();
            String jsonValue = mapper.writeValueAsString(entry.getValue());
            EnityItem entryItem = mapper.readValue(jsonValue, EnityItem.class);

            entity.put(entryItem.getName(), entryItem.getValue());
        }

        return entity;
    }
}


