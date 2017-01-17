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

import java.util.HashMap;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedRecordItem {

    /**
     * Gets or sets the json link value.
     */
    @JsonProperty("link_value")
    public Map<String, Map<String, Object>> value;

    /**
     * Gets the json formatted link value.
     *
     * @return Formatted object.
     */
    @JsonIgnore
    public Object getFormattedValue() {
        Object entity = new Object();
        if (this.value == null) {
            return entity;
        }

        Map<String, Object> namevalueMap = new HashMap<String, Object>();
        for (Map.Entry<String, Map<String, Object>> listItem: this.value.entrySet()) {
            Map<String, Object> listItemValue = listItem.getValue();

            if (listItemValue != null) {
                for (Map.Entry<String, Object> mapEntry : listItemValue.entrySet()) {
                    namevalueMap.put((String)listItemValue.get("name"), listItemValue.get("value"));
                }
            }

        }

        return namevalueMap;
    }
}


