package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents ReadEntryListResponse class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadEntryListResponse  extends BaseResponse
{
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
    public List<Object> relationshipList;

    @JsonIgnore
    public List<Object> getEntities() throws IOException {
        List<Object> entities = new ArrayList<Object>();

        if (entryList == null) {
            return entities;
        }

        for (int i = 0; i < entryList.size(); i++) {
            entities.add(entryList.get(i).getEntity());
        }
        return entities;
    }
}


