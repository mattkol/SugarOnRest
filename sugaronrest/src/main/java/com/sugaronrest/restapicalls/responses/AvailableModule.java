package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Represents ReadEntryListResponse class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableModule {
    /**
     * Gets or sets the number of entries returned
     */

    public String getModuleKey() {
        return modulekey;
    }

    public void setModuleKey(String value) {
        modulekey = value;
    }

    @JsonProperty("module_key")
    private String modulekey;
}


