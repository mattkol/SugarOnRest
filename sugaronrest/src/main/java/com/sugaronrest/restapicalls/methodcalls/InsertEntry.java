package com.sugaronrest.restapicalls.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sugaronrest.ErrorResponse;
import com.sugaronrest.restapicalls.responses.InsertEntryResponse;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kolao_000 on 2016-12-22.
 */
public class InsertEntry {
    /**
     * Login to SugarCrm via REST API call
     *
     *  @param sessionId LoginRequest object
     *  @return LoginResponse object
     */
    public static InsertEntryResponse run(String url, String sessionId, String moduleName, Object entity, List<String> selectFields)  {

        InsertEntryResponse insertEntryResponse = null;
        String jsonRequest = new String();
        String jsonResponse = new String();

        ObjectMapper mapper = JsonObjectMapper.getMapper();

        try {
            Map<String, Object> requestData = new LinkedHashMap<String, Object>();
            requestData.put("session", sessionId);
            requestData.put("module_name", moduleName);
            requestData.put("name_value_list", EntityToNameValueList(entity, selectFields));

            String jsonRequestData = mapper.writeValueAsString(requestData);

            Map<String, Object> request = new LinkedHashMap<String, Object>();
            request.put("method", "set_entry");
            request.put("input_type", "json");
            request.put("response_type", "json");
            request.put("rest_data", requestData);

            jsonRequest = mapper.writeValueAsString(request);

            request.put("rest_data", jsonRequestData);

            HttpResponse response = Unirest.post(url)
                    .fields(request)
                    .asString();

            if (response == null)
            {
                insertEntryResponse = new InsertEntryResponse();
                ErrorResponse errorResponse = ErrorResponse.format("An error has occurred!", "No data returned.");
                insertEntryResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                insertEntryResponse.setError(errorResponse);
            }

            jsonResponse = response.getBody().toString();

            if (StringUtils.isNotBlank(jsonResponse)) {
                try {
                    insertEntryResponse = mapper.readValue(jsonResponse, InsertEntryResponse.class);
                }
                catch (Exception exception)
                {
                    ErrorResponse errorResponse = mapper.readValue(jsonResponse, ErrorResponse.class);
                    errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    errorResponse.setTrace(exception);
                    insertEntryResponse = new InsertEntryResponse();
                    insertEntryResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    insertEntryResponse.setError(errorResponse);
                }
            }

            if (insertEntryResponse == null) {
                insertEntryResponse = new InsertEntryResponse();
                String message = String.format("Object type %S to json conversion failed - response data is invalid!", insertEntryResponse.getClass().getSimpleName());
                ErrorResponse errorResponse = ErrorResponse.format("An error has occurred!", message);
                insertEntryResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                insertEntryResponse.setError(errorResponse);
            }
        }
        catch (Exception exception) {
            System.out.println("---------3 - InsertEntry jsonResponse-------------");
            insertEntryResponse = new InsertEntryResponse();
            ErrorResponse errorResponse = ErrorResponse.format(exception, exception.getMessage());
            insertEntryResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            insertEntryResponse.setError(errorResponse);
        }

        insertEntryResponse.setJsonRawRequest(jsonRequest);
        insertEntryResponse.setJsonRawResponse(jsonResponse);

        return insertEntryResponse;
    }

    private static Map<String, Object> EntityToNameValueList(Object entity, List<String> selectFields) {
        if (entity == null) {
            return null;
        }

        ObjectMapper mapper = JsonObjectMapper.getMapper();
        Map<String, Object> tempEntity = mapper.convertValue(entity, Map.class);

        if (tempEntity == null) {
            return null;
        }

        boolean useSelectedFields = (selectFields != null) && (selectFields.size() > 0);
        Map<String, Object> mappedEntity = new HashMap<String, Object>();
        for (Map.Entry<String, Object> mapEntry : tempEntity.entrySet()) {

            String key = mapEntry.getKey();
            if (useSelectedFields) {
                if (!selectFields.contains(key)) {
                    continue;
                }
            }

            if (key.equalsIgnoreCase("id")) {
                continue;
            }

            Map<String, Object> namevalueDic = new HashMap<String, Object>();
            namevalueDic.put("name", key);
            namevalueDic.put("value", mapEntry.getValue());

            mappedEntity.put(key, namevalueDic);
        }

        return mappedEntity;
    }
}
