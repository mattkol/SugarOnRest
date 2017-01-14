package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.utils.JsonObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Represents ReadEntryResponse class
 */
@JsonPropertyOrder({
        "entry_list",
        "relationship_list"
})
public class ReadLinkedEntryResponse extends BaseResponse {

    @JsonProperty("entry_list")
    public List<EntryList> entryList;

    @JsonProperty("relationship_list")
    public List<List<LinkedModuleData>> relationshipList;

    @JsonIgnore
    public Object getEntity() throws IOException {
        Object entity = new Object();

        if (entryList == null) {
            return entity;
        }

        ObjectMapper mapper = JsonObjectMapper.getMapper();
        if (entryList.size() > 0) {
            entity = entryList.get(0).getEntity();
        }

        Map<String, Object> mappedEntity = mapper.convertValue(entity, Map.class);
        if (mappedEntity != null) {
            for (List<LinkedModuleData> linkedModuleData : relationshipList) {
                if (linkedModuleData != null) {
                    for (LinkedModuleData moduleData : linkedModuleData) {
                        mappedEntity.put(moduleData.name, moduleData.getFormattedRecords());
                    }
                }
            }
        }
        return mappedEntity;
    }
}

