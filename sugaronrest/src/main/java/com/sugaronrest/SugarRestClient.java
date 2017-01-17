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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.restapicalls.ModuleInfo;
import com.sugaronrest.restapicalls.SugarRestClientExt;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;


public class SugarRestClient {

    /**
     * Initializes a new instance of the SugarRestClient class.
     */
    public SugarRestClient() {
    }

    /**
     * Initializes a new instance of the SugarRestClient class.
     *
     * @param url The SugarCRM REST base url.
     */
    public SugarRestClient(String url) {
        setUrl(url);
    }

    /**
     * Initializes a new instance of the SugarRestClient class.
     *
     * @param url The SugarCRM REST base url.
     * @param username The SugarCRM REST username.
     * @param password The SugarCRM REST passwprd.
     */
    public SugarRestClient(String url, String username, String password)
    {
        setUrl(url);
        setUsername(username);
        setPassword(password);
    }

    /**
     * The main entry point for requesting data from SugarCRM.
     *
     * @param request The request object.
     * @return Response object.
     */
    public SugarRestResponse execute(SugarRestRequest request) {
        SugarRestResponse response = null;

        try {
            response = new SugarRestResponse();

            if (!isRequestValidate(request, response)) {
                setErrorResponse(request, response);
                return response;
            }

            ModuleInfo moduleInfo = ModuleInfo.create(request.getModuleType(), request.getModuleName());
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

    /**
     * Gets or sets the SugarCRM base url.
     */
    public String getUrl() {
        return url;
    }
    public void setUrl(String value) {
        url = value;
    }

    /**
     * Gets or sets the SugarCRM username.
     */
    public String getUsername() {
        return username;
    }
    public void setUsername(String value) {
        username = value;
    }

    /**
     * Gets or sets the SugarCRM password.
     */
    public String getPassword() {
        return password;
    }
    public void setPassword(String value) {
        password = value;
    }

    /**
     * Execute the request based on the request type.
     *
     * @param request The request type.
     * @param moduleInfo Java Pojo module info object created based on the module name or module Pojo class requested.
     * @return The response object.
     * @throws Exception
     */
    private SugarRestResponse internalExceute(SugarRestRequest request, ModuleInfo moduleInfo) throws Exception {
        switch (request.getRequestType()) {
            case ReadById: {
                return SugarRestClientExt.executeGetById(request, moduleInfo);
            }

            case BulkRead: {
                return SugarRestClientExt.executeGetAll(request, moduleInfo);
            }

            case PagedRead: {
                return SugarRestClientExt.executeGetPaged(request, moduleInfo);
            }

            case Create: {
                return SugarRestClientExt.executeInsert(request, moduleInfo);
            }

            case BulkCreate: {
                return SugarRestClientExt.executeInserts(request, moduleInfo);
            }

            case Update: {
                return SugarRestClientExt.executeUpdate(request, moduleInfo);
            }

            case BulkUpdate: {
                return SugarRestClientExt.executeUpdates(request, moduleInfo);
            }

            case Delete: {
                return SugarRestClientExt.executeDelete(request, moduleInfo);
            }

            case LinkedReadById: {
                return SugarRestClientExt.executeLinkedGetById(request, moduleInfo);
            }

            case LinkedBulkRead: {
                return SugarRestClientExt.executeLinkedGetAll(request, moduleInfo);
            }

            case AllModulesRead: {
                return SugarRestClientExt.executeGetAvailableModules(request, moduleInfo);
            }
        }

        SugarRestResponse response = new SugarRestResponse();
        response.setStatusCode(HttpStatus.SC_BAD_REQUEST);

        return response;
    }

    /**
     * Validates if request is valid.
     *
     * @param request The request object.
     * @param response The reference response.
     * @return True or false based on whether request is valid or not.
     */
    private boolean isRequestValidate(SugarRestRequest request, SugarRestResponse response)  {
        if (request == null) {
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

    /**
     * Sets response if an error occurs.
     *
     * @param request The request object.
     * @param response The response object.
     */
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
