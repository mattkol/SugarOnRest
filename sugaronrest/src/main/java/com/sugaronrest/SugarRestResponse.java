package com.sugaronrest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

/**
 * Represents SugarRestResponse class.
 */
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
    }

    /**
     * Gets or sets the raw json request sent by SugarCrm Rest API.
     */
    private String jsonRawRequest = new String();
    public String getJsonRawRequest() {
        return jsonRawRequest;
    }

    public void setJsonRawRequest(String value) {
        jsonRawRequest = value;
    }

    /**
     * Gets or sets the raw json response sent by SugarCrm Rest API.
     */
    private String jsonRawResponse = new String();
    public String getJsonRawResponse() {
        return jsonRawResponse;
    }

    public void setJsonRawResponse(String value) {
        jsonRawResponse = value;
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
     */
    private String jdata = new String();
    public String getJData() {
        return jdata;
    }

    public void setJData(String value) {
        jdata = value;
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
     * LinkedBulkRead - Entity collection
     */
    private Object data = new Object();
    public Object getData() {
        return data;
    }

    public void setData(Object value) {
        data = value;
    }

    /**
     * Gets or sets status code returned.
     */
    private int statusCode = HttpStatus.SC_OK;
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int value) {
        statusCode = value;
    }

    /**
     * Gets or sets error object.
     */
    private ErrorResponse error = new ErrorResponse();
    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse value) {
        error = value;
    }
}


