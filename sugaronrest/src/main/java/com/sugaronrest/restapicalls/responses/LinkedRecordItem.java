package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kolao_000 on 2017-01-09.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedRecordItem {
    /// <summary>
    /// Gets or sets the json link value.
    /// </summary>
    @JsonProperty("link_value")
    public Map<String, Map<String, Object>> value;

    /// <summary>
    /// Gets the json formatted link value.
    /// </summary>
    @JsonIgnore
    public Object getFormattedValue() {
        Object entity = new Object();
        if (this.value == null) {
            return entity;
        }

        Map<String, Object> namevalueMap = new HashMap<String, Object>();
        for (Map.Entry<String, Map<String, Object>> listItem: this.value.entrySet()) {
            Map<String, Object> listItemValue = listItem.getValue();

            if (listItemValue != null) {
                for (Map.Entry<String, Object> mapEntry : listItemValue.entrySet()) {
                    namevalueMap.put((String)listItemValue.get("name"), listItemValue.get("value"));
                }
            }

        }

        //entities.add(namevalueMap);
        return namevalueMap;
    }
}


