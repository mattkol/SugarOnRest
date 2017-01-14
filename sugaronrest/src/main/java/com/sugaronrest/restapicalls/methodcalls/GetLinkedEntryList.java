package com.sugaronrest.restapicalls.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sugaronrest.ErrorResponse;
import com.sugaronrest.restapicalls.responses.ReadEntryListResponse;
import com.sugaronrest.restapicalls.responses.ReadLinkedEntryListResponse;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kolao_000 on 2016-12-22.
 */
public class GetLinkedEntryList {

    /**
     * Login to SugarCrm via REST API call
     *
     *  @param sessionId LoginRequest object
     *  @return LoginResponse object
     */
    public static ReadLinkedEntryListResponse run(String url, String sessionId, String moduleName, List<String> selectFields, List<Object> linkedSelectFields, String queryString, int maxCountResult) throws Exception {

        ReadLinkedEntryListResponse readLinkedEntryListResponse = null;
        String jsonRequest = new String();
        String jsonResponse = new String();

        ObjectMapper mapper = JsonObjectMapper.getMapper();

        try {
            Map<String, Object> requestData = new LinkedHashMap<String, Object>();
            requestData.put("session", sessionId);
            requestData.put("module_name", moduleName);
            requestData.put("query", queryString);
            requestData.put("order_by", StringUtils.EMPTY);
            requestData.put("offset", 0);
            requestData.put("select_fields", selectFields);
            boolean linkedInfoNotSet = ((linkedSelectFields == null) || (linkedSelectFields.size() == 0));
            requestData.put("link_name_to_fields_array",  linkedInfoNotSet ? StringUtils.EMPTY : linkedSelectFields);
            requestData.put("max_results", maxCountResult);
            requestData.put("deleted", 0);
            requestData.put("favorites", false);

            String jsonRequestData = mapper.writeValueAsString(requestData);

            Map<String, Object> request = new LinkedHashMap<String, Object>();
            request.put("method", "get_entry_list");
            request.put("input_type", "json");
            request.put("response_type", "json");
            request.put("rest_data", requestData);

            jsonRequest = mapper.writeValueAsString(request);

            request.put("rest_data", jsonRequestData);

            HttpResponse response = Unirest.post(url)
                    .fields(request)
                    .asJson();

            if (response == null)
            {
                readLinkedEntryListResponse = new ReadLinkedEntryListResponse();
                ErrorResponse errorResponse = ErrorResponse.format("An error has occurred!", "No data returned.");
                readLinkedEntryListResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                readLinkedEntryListResponse.setError(errorResponse);
            }

            jsonResponse = response.getBody().toString();

            if (StringUtils.isNotBlank(jsonResponse)) {
                try {
                    readLinkedEntryListResponse = mapper.readValue(jsonResponse, ReadLinkedEntryListResponse.class);
                }
                catch (Exception exception)
                {
                    ErrorResponse errorResponse = mapper.readValue(jsonResponse, ErrorResponse.class);
                    errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    errorResponse.setTrace(exception);
                    readLinkedEntryListResponse = new ReadLinkedEntryListResponse();
                    readLinkedEntryListResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    readLinkedEntryListResponse.setError(errorResponse);
                }
            }

            if (readLinkedEntryListResponse == null) {
                readLinkedEntryListResponse = new ReadLinkedEntryListResponse();
                String message = String.format("Object type %S to json conversion failed - response data is invalid!", readLinkedEntryListResponse.getClass().getSimpleName());
                ErrorResponse errorResponse = ErrorResponse.format("An error has occurred!", message);
                readLinkedEntryListResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                readLinkedEntryListResponse.setError(errorResponse);
            }
        }
        catch (Exception exception) {
            readLinkedEntryListResponse = new ReadLinkedEntryListResponse();
            ErrorResponse errorResponse = ErrorResponse.format(exception, exception.getMessage());
            readLinkedEntryListResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            readLinkedEntryListResponse.setError(errorResponse);
        }

        readLinkedEntryListResponse.setJsonRawRequest(jsonRequest);
        readLinkedEntryListResponse.setJsonRawResponse(jsonResponse);

        return readLinkedEntryListResponse;
    }
}

