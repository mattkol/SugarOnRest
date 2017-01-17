/**
 MIT License

 Copyright (c) 2017 Kola Oyewumi

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.utils.JsonObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@JsonPropertyOrder({
        "entry_list",
        "relationship_list"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadLinkedEntryResponse extends BaseResponse {

    /**
     *  Gets or sets the entry list.
     */
    @JsonProperty("entry_list")
    public List<EntryList> entryList;

    /**
     *  Gets or sets the relationship list.
     */
    @JsonProperty("relationship_list")
    public List<List<LinkedModuleData>> relationshipList;

    /**
     * Gets the formatted entity object.
     *
     * @return The entity object.
     * @throws IOException
     */
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

