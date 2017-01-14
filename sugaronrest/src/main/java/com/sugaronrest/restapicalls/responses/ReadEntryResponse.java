package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents ReadEntryResponse class
 */
public class ReadEntryResponse  extends BaseResponse
{
    @JsonProperty("entry_list")
    public List<EntryList> entryList;

    @JsonProperty("relationship_list")
    public List<Object> relationshipList;

    @JsonIgnore
    public Object getEntity() throws IOException {
        List<Object> entities = new ArrayList<Object>();

        if (entryList == null) {
            return entities;
        }

        for (int i = 0; i < entryList.size(); i++) {
            entities.add(entryList.get(i).getEntity());
        }

        if (entities.size() > 0) {
            return entities.get(0);
        }

        return null;
    }
}


