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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadEntryResponse  extends BaseResponse
{
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

    /**
     * Gets the formatted entity.
     *
     * @return Entity object.
     * @throws IOException
     */
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


