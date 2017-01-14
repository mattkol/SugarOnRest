package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents LinkedModuleData class
 */
@JsonPropertyOrder({
        "name",
        "records"
})
public class LinkedModuleData {

    @JsonProperty("name")
    public String name;

    @JsonProperty("records")
    public List<Map<String, Map<String, Object>>> records;

    @JsonIgnore
    public List<Object> getFormattedRecords() {
        List<Object> entities = new ArrayList<Object>();
        if (this.records == null) {
            return new ArrayList<Object>();
        }

        for (Map<String, Map<String, Object>> listItem: this.records) {
            Map<String, Object> namevalueMap = new HashMap<String, Object>();
            for (Map.Entry<String, Map<String, Object>> mapEntry : listItem.entrySet()) {
                Map<String, Object> mapEntryValue = mapEntry.getValue();
                namevalueMap.put((String)mapEntryValue.get("name"), mapEntryValue.get("value"));
            }

            entities.add(namevalueMap);
        }


        return entities;
    }
 }


