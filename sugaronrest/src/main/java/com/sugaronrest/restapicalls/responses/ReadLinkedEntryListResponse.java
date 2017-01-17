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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.utils.JsonObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@JsonPropertyOrder({
        "total_count",
        "relationship_list",
        "entry_list",
        "result_count",
        "next_offset"
})
@JsonIgnoreProperties(ignoreUnknown = true)
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

    /**
     * Gets the formatted entities.
     *
     * @return Entity objects.
     * @throws IOException
     */
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

