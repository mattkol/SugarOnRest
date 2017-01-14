package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents LinkedListModuleData class
 */
public class LinkedListModuleData
{
    /**
     * Gets or sets the linked module name.
     */
    @JsonProperty("name")
    public String moduleName;

    /**
     * Gets or sets the linked module data.
     */
    @JsonProperty("records")
    public List<LinkedRecordItem> records;

    /**
     * Gets the formatted record in json.
     */
    public List<Object> getFormattedRecords() {
        List<Object> formattedRecords = new ArrayList<Object>();

        if ((records == null) || (records.size() ==0)) {
            return formattedRecords;
        }

        for (LinkedRecordItem item : records) {
            formattedRecords.add(item.getFormattedValue());
        }

        return formattedRecords;
    }

}


