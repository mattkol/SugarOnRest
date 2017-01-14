package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents ReadEntryListResponse class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadAvailableModulesResponse  extends BaseResponse
{
    public List<String> getModules() {
        List<String> modules = new ArrayList<String>();

        if (modules != null) {
            for (AvailableModule moduleKey : availableModules) {
                modules.add(moduleKey.getModuleKey());
            }
        }
        return modules;
    }

    @JsonProperty("modules")
    public List<AvailableModule> availableModules;
}


