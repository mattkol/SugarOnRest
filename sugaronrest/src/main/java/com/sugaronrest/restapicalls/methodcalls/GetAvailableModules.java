package com.sugaronrest.restapicalls.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sugaronrest.ErrorResponse;
import com.sugaronrest.restapicalls.responses.ReadAvailableModulesResponse;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by kolao_000 on 2016-12-22.
 */
public class GetAvailableModules {

    /**
     * Login to SugarCrm via REST API call
     *
     *  @param sessionId LoginRequest object
     *  @return LoginResponse object
     */
    public static ReadAvailableModulesResponse run(String url, String sessionId, String moduleName) throws Exception {

        ReadAvailableModulesResponse readAvailableModulesResponse = new ReadAvailableModulesResponse();
        ObjectMapper mapper = JsonObjectMapper.getMapper();

        try {
            Map<String, Object> requestData = new LinkedHashMap<String, Object>();
            requestData.put("session", sessionId);
            requestData.put("filter", "all");

            String jsonRequestData = mapper.writeValueAsString(requestData);

            Map<String, Object> request = new LinkedHashMap<String, Object>();
            request.put("method", "get_available_modules");
            request.put("input_type", "json");
            request.put("response_type", "json");
            request.put("rest_data", requestData);

            String jsonRequest = mapper.writeValueAsString(request);

            request.put("rest_data", jsonRequestData);

            HttpResponse response = Unirest.post(url)
                    .fields(request)
                    .asJson();

            String jsonResponse = response.getBody().toString();
            if (StringUtils.isNotBlank(jsonResponse)) {
                try {
                    readAvailableModulesResponse = mapper.readValue(jsonResponse, ReadAvailableModulesResponse.class);
                }
                catch (Exception exception)
                {
                    ErrorResponse errorResponse = mapper.readValue(jsonResponse, ErrorResponse.class);
                    errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    errorResponse.setTrace(exception);
                    readAvailableModulesResponse = new ReadAvailableModulesResponse();
                    readAvailableModulesResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                    readAvailableModulesResponse.setError(errorResponse);
                }
            }

            if (readAvailableModulesResponse == null) {
                readAvailableModulesResponse = new ReadAvailableModulesResponse();
                String message = String.format("Object type %S to json conversion failed - response data is invalid!", readAvailableModulesResponse.getClass().getSimpleName());
                ErrorResponse errorResponse = ErrorResponse.format("An error has occurred!", message);
                readAvailableModulesResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                readAvailableModulesResponse.setError(errorResponse);
            }

        }
        catch (Exception exception) {
            readAvailableModulesResponse = new ReadAvailableModulesResponse();
            ErrorResponse errorResponse = ErrorResponse.format(exception, exception.getMessage());
            readAvailableModulesResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            readAvailableModulesResponse.setError(errorResponse);
        }

        return readAvailableModulesResponse;
    }
}
