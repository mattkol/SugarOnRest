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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonPropertyOrder({
        "name",
        "records"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedModuleData {

    /**
     * Gets or sets the linked module name.
     */
    @JsonProperty("name")
    public String name;

    /**
     * Gets or sets the linked module data.
     */
    @JsonProperty("records")
    public List<Map<String, Map<String, Object>>> records;

    /**
     * Gets formatted linked records.
     *
     * @return List of formatted linked objects.
     */
    @JsonIgnore
    public List<Object> getFormattedRecords() {
        List<Object> entities = new ArrayList<Object>();
        if (this.records == null) {
            return new ArrayList<Object>();
        }

        for (Map<String, Map<String, Object>> listItem: this.records) {
            Map<String, Object> namevalueMap = new HashMap<String, Object>();
            for (Map.Entry<String, Map<String, Object>> mapEntry : listItem.entrySet()) {
                Map<String, Object> mapEntryValue = mapEntry.getValue();
                namevalueMap.put((String)mapEntryValue.get("name"), mapEntryValue.get("value"));
            }

            entities.add(namevalueMap);
        }

        return entities;
    }
 }


