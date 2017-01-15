package com.sugaronrest.restapicalls.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sugaronrest.ErrorResponse;
import com.sugaronrest.restapicalls.responses.UpdateEntriesResponse;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.util.*;

/**
 * Created by kolao_000 on 2016-12-22.
 */
public class UpdateEntries {
    /**
     * Updates entries [SugarCrm REST method - set_entries]
     *
     *  @param url REST API Url
     *  @param sessionId Session identifier
     *  @param moduleName SugarCrm module name
     *  @param entities The entity objects collection to create
     *  @param selectFields Selected field list
     *  @return UpdateEntriesResponse object
     */
    public static UpdateEntriesResponse run(String url, String sessionId, String moduleName, List<Object> entities, List<String> selectFields)  {

        UpdateEntriesResponse updateEntriesResponse = null;
        ErrorResponse errorResponse = null;

        String jsonRequest = new String();
        String jsonResponse = new String();

        ObjectMapper mapper = JsonObjectMapper.getMapper();

        try {
            Map<String, Object> requestData = new LinkedHashMap<String, Object>();
            requestData.put("session", sessionId);
            requestData.put("module_name", moduleName);
            requestData.put("name_value_list", EntitiesToNameValueList(entities, selectFields));

            String jsonRequestData = mapper.writeValueAsString(requestData);

            Map<String, Object> request = new LinkedHashMap<String, Object>();
            request.put("method", "set_entries");
            request.put("input_type", "json");
            request.put("response_type", "json");
            request.put("rest_data", requestData);

            jsonRequest = mapper.writeValueAsString(request);

            request.put("rest_data", jsonRequestData);

            HttpResponse response = Unirest.post(url)
                    .fields(request)
                    .asString();

            if (response == null) {
                updateEntriesResponse = new UpdateEntriesResponse();
                errorResponse = ErrorResponse.format("An error has occurred!", "No data returned.");
                updateEntriesResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                updateEntriesResponse.setError(errorResponse);
            } else {

                jsonResponse = response.getBody().toString();

                if (StringUtils.isNotBlank(jsonResponse)) {
                    // First check if we have an error
                    errorResponse = ErrorResponse.fromJson(jsonResponse);
                    if (errorResponse == null) {
                        updateEntriesResponse = mapper.readValue(jsonResponse, UpdateEntriesResponse.class);
                    }
                }

                if (updateEntriesResponse == null) {
                    updateEntriesResponse = new UpdateEntriesResponse();
                    updateEntriesResponse.setError(errorResponse);

                    updateEntriesResponse.setStatusCode(HttpStatus.SC_OK);
                    if (errorResponse != null) {
                        updateEntriesResponse.setStatusCode(errorResponse.getStatusCode());
                    }
                } else {
                    updateEntriesResponse.setStatusCode(HttpStatus.SC_OK);
                }
            }
        }
        catch (Exception exception) {
            updateEntriesResponse = new UpdateEntriesResponse();
            errorResponse = ErrorResponse.format(exception, exception.getMessage());
            updateEntriesResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            updateEntriesResponse.setError(errorResponse);
        }

        updateEntriesResponse.setJsonRawRequest(jsonRequest);
        updateEntriesResponse.setJsonRawResponse(jsonResponse);

        return updateEntriesResponse;
    }

    private static List<Map<String, Object>> EntitiesToNameValueList(List<Object> entities, List<String> selectFields) {
        if (entities == null) {
            return null;
        }

        List<Map<String, Object>> mappedEntities = new ArrayList<Map<String, Object>>();
        boolean useSelectedFields = (selectFields != null) && (selectFields.size() > 0);
        ObjectMapper mapper = JsonObjectMapper.getMapper();

        for (Object entity : entities) {
            Map<String, Object> tempEntity = mapper.convertValue(entity, Map.class);
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

            mappedEntities.add(mappedEntity);
        }

        return mappedEntities;
    }
}
