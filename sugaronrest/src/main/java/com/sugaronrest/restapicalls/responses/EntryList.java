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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@JsonRootName(value = "entry_list")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntryList
{
    /**
     * Gets or sets the entity identifier
     */
    @JsonProperty("id")
    public String id;

    /**
     * Gets or sets the entity module name
     */
    @JsonProperty("module_name")
    public String moduleName;

    /**
     * Gets or sets the returned entity name value list
     */
    @JsonProperty("name_value_list")
    public Object nameValueList;

    @JsonIgnore
    public Object getEntity() throws IOException {
        if (nameValueList == null) {
            return new LinkedHashMap<String,Object>();
        }

        Map<String, Object> entity = new HashMap<String,Object>();
        ObjectMapper mapper = JsonObjectMapper.getMapper();

        Map<String, Object> nameValueMap = (Map<String, Object>)nameValueList;
        for (Map.Entry<String, Object> entry : nameValueMap.entrySet()) {
            String key = entry.getKey();
            String jsonValue = mapper.writeValueAsString(entry.getValue());
            EnityItem entryItem = mapper.readValue(jsonValue, EnityItem.class);

            entity.put(entryItem.name, entryItem.value);
        }

        return entity;
    }
}


