package com.sugaronrest.restapicalls.responses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.utils.JsonObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents ReadLinkedEntryListResponse class
 */
@JsonPropertyOrder({
        "total_count",
        "relationship_list",
        "entry_list",
        "result_count",
        "next_offset"
})
public class ReadLinkedEntryListResponse  extends BaseResponse {
    /**
     * Gets or sets the number of entries returned
     */
    @JsonProperty("result_count")
    public int count;

    /**
     * Gets or sets the total count of entries available
     */
    @JsonProperty("total_count")
    public int totalCount;

    /**
     * Gets or sets the next offset
     */
    @JsonProperty("next_offset")
    private int nextOffset;

    /**
     * Gets or sets the entry list in json
     */
    @JsonProperty("entry_list")
    public List<EntryList> entryList;

    /**
     * Gets or sets the relationship link entry list in json
     */
    @JsonProperty("relationship_list")
    public List<LinkedListModule> relationshipList;

    @JsonIgnore
    public List<Object> getEntities() throws IOException {
        List<Object> entities = new ArrayList<Object>();

        if (entryList == null) {
            return entities;
        }

        ObjectMapper mapper = JsonObjectMapper.getMapper();
        for (int i = 0; i < entryList.size(); i++) {
            Object entity = entryList.get(i).getEntity();

            Map<String, Object> mappedEntity = mapper.convertValue(entity, Map.class);
            if (mappedEntity != null) {
                LinkedListModule linkedListModule = relationshipList.get(i);
                if ((linkedListModule != null) && (linkedListModule.moduleDataList != null)) {
                    for (LinkedListModuleData moduleData : linkedListModule.moduleDataList) {
                        for (LinkedListModuleData item : linkedListModule.moduleDataList) {
                            mappedEntity.put(item.moduleName, item.getFormattedRecords());
                        }
                    }
                }
            }

            entities.add(mappedEntity);
        }

        return entities;
    }
}

