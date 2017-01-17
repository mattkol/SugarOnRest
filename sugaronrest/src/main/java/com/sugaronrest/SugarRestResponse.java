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

package com.sugaronrest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SugarRestResponse {

    /**
     * Initializes a new instance of the SugarRestResponse class.
     */
    public SugarRestResponse()  {
        this.setError(new ErrorResponse());
        this.setJsonRawRequest(StringUtils.EMPTY);
        this.setJsonRawResponse(StringUtils.EMPTY);
        this.setJData(StringUtils.EMPTY);
        this.setStatusCode(HttpStatus.SC_OK);
    }

    /**
     * Gets or sets identity, identifiers, entity or entities data returned.
     * Data type returned for the following request type:
     * ReadById - Entity
     * BulkRead - Entity collection
     * PagedRead - Entity collection
     * Create - Identifier (Id)
     * BulkCreate - Identifiers (Ids)
     * Update - Identifier (Id)
     * BulkUpdate - Identifiers (Ids)
     * Delete - Identifier (Id)
     * LinkedReadById - Entity
     * AllModulesRead - List of available modules (String)
     */
    public Object getData() {
        return data;
    }

    public void setData(Object value) {
        data = value;
    }

    /**
     * Gets or sets identity, identifiers, entity or entities data returned in json.
     * Data type returned for the following request type:
     * ReadById - Entity
     * BulkRead - Entity collection
     * PagedRead - Entity collection
     * Create - Identifier (Id)
     * BulkCreate - Identifiers (Ids)
     * Update - Identifier (Id)
     * BulkUpdate - Identifiers (Ids)
     * Delete - Identifier (Id)
     * LinkedReadById - Entity
     * LinkedBulkRead - Entity collection
     * LinkedBulkRead - Entity collection
     */
    public String getJData() {
        return jdata;
    }
    public void setJData(String value) {
        jdata = value;
    }

    /**
     * Gets or sets the raw json request sent by SugarCRM Rest API.
     */
    public String getJsonRawRequest() {
        return jsonRawRequest;
    }
    public void setJsonRawRequest(String value) {
        jsonRawRequest = value;
    }

    /**
     * Gets or sets the raw json response sent by SugarCRM Rest API.
     */
    public String getJsonRawResponse() {
        return jsonRawResponse;
    }
    public void setJsonRawResponse(String value) {
        jsonRawResponse = value;
    }

    /**
     * Gets or sets status code returned.
     */
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int value) {
        statusCode = value;
    }

    /**
     * Gets or sets error object.
     */
    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse value) {
        error = value;
    }

    private Object data;
    private String jdata;
    private String jsonRawRequest;
    private String jsonRawResponse;
    private int statusCode;
    private ErrorResponse error;
}


