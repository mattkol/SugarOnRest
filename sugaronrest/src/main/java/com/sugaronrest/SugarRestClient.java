package com.sugaronrest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.restapicalls.ModuleInfo;
import com.sugaronrest.restapicalls.SugarRestClientExt;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

/**
 * Created by kolao_000 on 2016-12-21.
 */
public class SugarRestClient {

    /// <summary>
    /// Initializes a new instance of the SugarRestClient class.
    /// </summary>
    public SugarRestClient() {
    }

    /// <summary>
    /// Initializes a new instance of the SugarRestClient class.
    /// </summary>
    /// <param name="url">SugarCrm REST API url.</param>
    public SugarRestClient(String url) {
        setUrl(url);
    }

    /// <summary>
    /// Initializes a new instance of the SugarRestClient class.
    /// </summary>
    /// <param name="url">SugarCrm REST API Url.</param>
    /// <param name="username">SugarCrm REST API Username.</param>
    /// <param name="password">SugarCrm REST API Password.</param>
    public SugarRestClient(String url, String username, String password)
    {
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    /// <summary>
    /// Execute client.
    /// </summary>
    /// <param name="request">The request object.</param>
    /// <returns>SugarRestResponse object.</returns>
    public SugarRestResponse execute(SugarRestRequest request) {
        SugarRestResponse response = null;

        try {
            response = new SugarRestResponse();

            if (!isRequestValidate(request, response)) {
                setErrorResponse(request, response);
                return response;
            }

            ModuleInfo moduleInfo = ModuleInfo.read(request.getModuleType(), request.getModuleName());
            response = internalExceute(request, moduleInfo);

        } catch (Exception exception) {
            response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
            ErrorResponse errorResponse = ErrorResponse.format(exception, "Error procesing request!");
            response.setError(errorResponse);
        }
        finally {
            if(response == null) {
                response = new SugarRestResponse();
                response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                ErrorResponse errorResponse = ErrorResponse.format("Request type is invalid!");
            }
        }

        return response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String value) {
        url = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password = value;
    }

    /// <summary>
    /// Execute request.
    /// </summary>
    /// <param name="request">The request object.</param>
    /// <param name="modelInfo">The model info for the referenced SugarCrm module.</param>
    /// <returns>SugarRestResponse object.</returns>
    private SugarRestResponse internalExceute(SugarRestRequest request, ModuleInfo modelInfo) throws Exception {
        switch (request.getRequestType())
        {
            case ReadById: {
                return SugarRestClientExt.executeGetById(request, modelInfo);
            }

            case BulkRead: {
                return SugarRestClientExt.executeGetAll(request, modelInfo);
            }

            case PagedRead: {
                return SugarRestClientExt.executeGetPaged(request, modelInfo);
            }

            case Create: {
                return SugarRestClientExt.executeInsert(request, modelInfo);
            }

            case BulkCreate: {
                return SugarRestClientExt.executeInserts(request, modelInfo);
            }

            case Update: {
                return SugarRestClientExt.executeUpdate(request, modelInfo);
            }

            case BulkUpdate: {
                return SugarRestClientExt.executeUpdates(request, modelInfo);
            }

            case Delete: {
                return SugarRestClientExt.executeDelete(request, modelInfo);
            }

            case LinkedReadById: {
                return SugarRestClientExt.executeLinkedGetById(request, modelInfo);
            }

            case LinkedBulkRead: {
                return SugarRestClientExt.executeLinkedGetAll(request, modelInfo);
            }

            case AllModulesRead: {
                return SugarRestClientExt.executeGetAvailableModules(request, modelInfo);
            }
        }

        SugarRestResponse response = new SugarRestResponse();
        response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        ErrorResponse errorResponse = ErrorResponse.format("Request type is invalid!");

        return response;
    }

    /// <summary>
    /// Method checks if request is valid.
    /// </summary>
    /// <param name="request">The request object.</param>
    /// <param name="response">The response object.</param>
    /// <returns>True or false.</returns>
    private boolean isRequestValidate(SugarRestRequest request, SugarRestResponse response)  {
        if (request == null)
        {
            response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse();
            try {
                errorResponse = ErrorResponse.format("Request is invalid.");
            } catch (Exception exception) {
                errorResponse.setTrace(exception);
                response.setError(errorResponse);
                return false;
            }

            response.setError(errorResponse);
            return false;
        }

        if (!StringUtils.isNotBlank(request.getUrl())) {
            request.setUrl(url);
        }

        // Set request credential
        if (!StringUtils.isNotBlank(request.getUsername())) {
            request.setUsername(username);
        }

        if (!StringUtils.isNotBlank(request.getPassword())) {
            request.setPassword(password);
        }

        // Set ensure client credential in case of re-use
        if (!StringUtils.isNotBlank(url) && StringUtils.isNotBlank(request.getUrl())) {
            setUrl(request.getUrl());
        }

        if (!StringUtils.isNotBlank(username) && StringUtils.isNotBlank(request.getUsername())) {
            setUsername(request.getUsername());
        }

        if (!StringUtils.isNotBlank(password) && StringUtils.isNotBlank(request.getPassword())) {
            setPassword(request.getPassword());
        }

        try {
            if (!request.getIsValid())
            {
                response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                ErrorResponse errorResponse = ErrorResponse.format(request.getValidationMessage());
                response.setError(errorResponse);
                return false;
            }
        } catch (Exception exception) {
            ErrorResponse errorResponse = ErrorResponse.format(exception, exception.getMessage());
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            response.setError(errorResponse);
            return false;
        }

        return true;
    }

    private void setErrorResponse(SugarRestRequest request, SugarRestResponse response) {
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        String jsonRequest = StringUtils.EMPTY;
        String jsonResponse = StringUtils.EMPTY;

        try {
            jsonRequest = mapper.writeValueAsString(request);
            jsonResponse = mapper.writeValueAsString(response);
        } catch (JsonProcessingException exception) {
            ErrorResponse errorResponse = ErrorResponse.format(exception, exception.getMessage());
            errorResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            response.setError(errorResponse);
        }

        response.setData(null);
        response.setJData(StringUtils.EMPTY);
        response.setJsonRawRequest(jsonRequest);
        response.setJsonRawResponse(jsonResponse);
    }

    private String url;
    private String username;
    private String password;
}
