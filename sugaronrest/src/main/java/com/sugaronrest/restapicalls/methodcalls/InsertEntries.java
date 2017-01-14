package com.sugaronrest.restapicalls.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sugaronrest.ErrorResponse;
import com.sugaronrest.restapicalls.responses.InsertEntriesResponse;
import com.sugaronrest.restapicalls.responses.InsertEntryResponse;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.util.*;

/**
 * Created by kolao_000 on 2016-12-22.
 */
public class InsertEntries {
    /**
     * Creates entries [SugarCrm REST method - set_entries]
     *
     *  @param url REST API Url
     *  @param sessionId Session identifier
     *  @param moduleName SugarCrm module name
     *  @param entities The entity objects collection to create
     *  @param selectFields Selected field list
     *  @return InsertEntriesResponse object
     */
    public static InsertEntriesResponse run(String url, String sessionId, String moduleName, List<Object> entities, List<String> selectFields)  {

        InsertEntriesResponse insertEntriesResponse = null;
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

            if (response == null)
            {
                insertEntriesResponse = new InsertEntriesResponse();
                ErrorResponse errorResponse = ErrorResponse.format("An error has occurred!", "No data returned.");
                insertEntriesResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                insertEntriesResponse.setError(errorResponse);
            }

            jsonResponse = response.getBody().toString();

            if (StringUtils.isNotBlank(jsonResponse)) {
                try {
                    insertEntriesResponse = mapper.readValue(jsonResponse, InsertEntriesResponse.class);
                }
                catch (Exception exception)
                {
                    ErrorResponse errorResponse = mapper.readValue(jsonResponse, ErrorResponse.class);
                    errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    errorResponse.setTrace(exception);
                    insertEntriesResponse = new InsertEntriesResponse();
                    insertEntriesResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    insertEntriesResponse.setError(errorResponse);
                }
            }

            if (insertEntriesResponse == null) {
                insertEntriesResponse = new InsertEntriesResponse();
                String message = String.format("Object type %S to json conversion failed - response data is invalid!", insertEntriesResponse.getClass().getSimpleName());
                ErrorResponse errorResponse = ErrorResponse.format("An error has occurred!", message);
                insertEntriesResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                insertEntriesResponse.setError(errorResponse);
            }
        }
        catch (Exception exception) {
            insertEntriesResponse = new InsertEntriesResponse();
            ErrorResponse errorResponse = ErrorResponse.format(exception, exception.getMessage());
            insertEntriesResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            insertEntriesResponse.setError(errorResponse);
        }

        insertEntriesResponse.setJsonRawRequest(jsonRequest);
        insertEntriesResponse.setJsonRawResponse(jsonResponse);

        return insertEntriesResponse;
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

            mappedEntities.add(mappedEntity);
        }

        return mappedEntities;
    }
}
