package com.sugaronrest.restapicalls.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sugaronrest.ErrorResponse;
import com.sugaronrest.restapicalls.responses.InsertEntryResponse;
import com.sugaronrest.restapicalls.responses.UpdateEntryResponse;
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
public class UpdateEntry {
    /**
     * Updates entry [SugarCrm REST method - set_entry]
     *
     *  @param url REST API Url
     *  @param sessionId Session identifier
     *  @param moduleName SugarCrm module name
     *  @param entity The entity object to update
     *  @param selectFields Selected field list
     *  @return ReadEntryResponse object
     */
    public static UpdateEntryResponse run(String url, String sessionId, String moduleName, Object entity, List<String> selectFields)  {

        UpdateEntryResponse updateEntryResponse = null;
        ErrorResponse errorResponse = null;

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

            if (response == null) {
                updateEntryResponse = new UpdateEntryResponse();
                errorResponse = ErrorResponse.format("An error has occurred!", "No data returned.");
                updateEntryResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                updateEntryResponse.setError(errorResponse);
            } else {

                jsonResponse = response.getBody().toString();

                if (StringUtils.isNotBlank(jsonResponse)) {
                    // First check if we have an error
                    errorResponse = ErrorResponse.fromJson(jsonResponse);
                    if (errorResponse == null) {
                        updateEntryResponse = mapper.readValue(jsonResponse, UpdateEntryResponse.class);
                    }
                }

                if (updateEntryResponse == null) {
                    updateEntryResponse = new UpdateEntryResponse();
                    updateEntryResponse.setError(errorResponse);

                    updateEntryResponse.setStatusCode(HttpStatus.SC_OK);
                    if (errorResponse != null) {
                        updateEntryResponse.setStatusCode(errorResponse.getStatusCode());
                    }
                } else {
                    updateEntryResponse.setStatusCode(HttpStatus.SC_OK);
                }
            }
        }
        catch (Exception exception) {
            updateEntryResponse = new UpdateEntryResponse();
            errorResponse = ErrorResponse.format(exception, exception.getMessage());
            updateEntryResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            updateEntryResponse.setError(errorResponse);
        }

        updateEntryResponse.setJsonRawRequest(jsonRequest);
        updateEntryResponse.setJsonRawResponse(jsonResponse);

        return updateEntryResponse;
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

            // The identifier must always be added.
            if (!key.equalsIgnoreCase("id")) {
                if (useSelectedFields) {
                    if (!selectFields.contains(key)) {
                        continue;
                    }
                }
            }

            Map<String, Object> namevalueDic = new HashMap<String, Object>();
            namevalueDic.put("name", key);
            namevalueDic.put("value", mapEntry.getValue());

            mappedEntity.put(key, namevalueDic);
        }

        return mappedEntity;
    }
}
