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

package com.sugaronrest.restapicalls.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sugaronrest.ErrorResponse;
import com.sugaronrest.restapicalls.responses.ReadLinkedEntryResponse;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class GetLinkedEntry {

    /**
     * Gets linked entry[SugarCRM REST method - get_entry].
     *
     * @param url REST API Url.
     * @param sessionId Session identifier.
     * @param moduleName SugarCRM module name.
     * @param identifier The entity identifier.
     * @param selectFields Selected field list.
     * @param linkedSelectFields Linked field info.
     * @return ReadLinkedEntryResponse object
     */
    public static ReadLinkedEntryResponse run(String url, String sessionId, String moduleName, String identifier, List<String> selectFields, List<Object> linkedSelectFields)  {

        ReadLinkedEntryResponse readLinkedEntryResponse = null;
        ErrorResponse errorResponse = null;

        String jsonRequest = new String();
        String jsonResponse = new String();

        ObjectMapper mapper = JsonObjectMapper.getMapper();

        try {
            Map<String, Object> requestData = new LinkedHashMap<String, Object>();
            requestData.put("session", sessionId);
            requestData.put("module_name", moduleName);
            requestData.put("id", identifier);
            requestData.put("select_fields", selectFields);
            boolean linkedInfoNotSet = ((linkedSelectFields == null) || (linkedSelectFields.size() == 0));
            requestData.put("link_name_to_fields_array",  linkedInfoNotSet ? StringUtils.EMPTY : linkedSelectFields);
            requestData.put("track_view", false);

            String jsonRequestData = mapper.writeValueAsString(requestData);

            Map<String, Object> request = new LinkedHashMap<String, Object>();
            request.put("method", "get_entry");
            request.put("input_type", "json");
            request.put("response_type", "json");
            request.put("rest_data", requestData);

            jsonRequest = mapper.writeValueAsString(request);

            request.put("rest_data", jsonRequestData);

            HttpResponse response = Unirest.post(url)
                    .fields(request)
                    .asString();

            if (response == null) {
                readLinkedEntryResponse = new ReadLinkedEntryResponse();
                errorResponse = ErrorResponse.format("An error has occurred!", "No data returned.");
                readLinkedEntryResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                readLinkedEntryResponse.setError(errorResponse);
            } else {

                jsonResponse = response.getBody().toString();

                if (StringUtils.isNotBlank(jsonResponse)) {
                    // First check if we have an error
                    errorResponse = ErrorResponse.fromJson(jsonResponse);
                    if (errorResponse == null) {
                        readLinkedEntryResponse = mapper.readValue(jsonResponse, ReadLinkedEntryResponse.class);
                    }
                }

                if (readLinkedEntryResponse == null) {
                    readLinkedEntryResponse = new ReadLinkedEntryResponse();
                    readLinkedEntryResponse.setError(errorResponse);

                    readLinkedEntryResponse.setStatusCode(HttpStatus.SC_OK);
                    if (errorResponse != null) {
                        readLinkedEntryResponse.setStatusCode(errorResponse.getStatusCode());
                    }
                } else {
                    readLinkedEntryResponse.setStatusCode(HttpStatus.SC_OK);
                }
            }
        }
        catch (Exception exception) {
            readLinkedEntryResponse = new ReadLinkedEntryResponse();
            errorResponse = ErrorResponse.format(exception, exception.getMessage());
            readLinkedEntryResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            readLinkedEntryResponse.setError(errorResponse);
        }

        readLinkedEntryResponse.setJsonRawRequest(jsonRequest);
        readLinkedEntryResponse.setJsonRawResponse(jsonResponse);

        return readLinkedEntryResponse;
    }
}
