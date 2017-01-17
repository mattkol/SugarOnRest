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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


@JsonRootName(value = "error_response")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    /**
     * Gets or sets the http status code
     */
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int value) {
        statusCode = value;
    }

    /**
     * Gets or sets error title.
     */
    public String getName() {
        return name;
    }
    public void setName(String value) {
        name = value;
    }

    /**
     * Gets or sets the error number returned from SugarCRM.
     */
    public int getNumber() {
        return number;
    }
    public void setNumber(int value) {
        number = value;
    }

    /**
     * Gets or sets the error message description returned from SugarCRM.
     */
    public String getMessage() {
        return message;
    }
    public void setMessage(String value) {
        message = value;
    }

    /**
     * Gets or sets exception type.
     */
    public String getExceptionType() {
        return exceptionType;
    }
    public void setExceptionType(String value) {
        exceptionType = value;
    }

    /**
     * Gets the error stack trace description.
     */
    public String getTrace() {
        return stackTrace;
    }

    /**
     * Sets the error trace based on exception object.
     *
     * @param exception The exception object.
     */
    public void setTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        stackTrace = stringWriter.toString();
    }

    /**
     * Gets formatted error response based on UniRest error
     *
     * @param jsonResponse Response object from UniRest
     * @return ErrorResponse object
     */
    public static ErrorResponse fromJson(String jsonResponse) {
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        try {
            ErrorResponse errorResponse = mapper.readValue(jsonResponse, ErrorResponse.class);
            if (errorResponse != null) {
                if (errorResponse.getNumber() <= 0) {
                    return null;
                }

                errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
            }

            return errorResponse;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets formatted error response based on UniRest error
     *
     * @param name Response object from UniRest
     * @return ErrorResponse object
     */
    public static ErrorResponse format(String name, String message) {
        ErrorResponse errorResponse = new ErrorResponse();

        if (!StringUtils.isNotBlank(name)) {
            name = "Internal server error.";
        }

        errorResponse.setName(name);
        errorResponse.setNumber(HttpStatus.SC_BAD_REQUEST);
        errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        errorResponse.setMessage(message);
        return errorResponse;
    }

    /**
     * Gets formatted error response exception or SugarCRM error message
     *
     * @param exception    Exception from SugarCRM REST API calls or .NET error
     * @param errorContent Error returned from SugarCRM
     * @return ErrorResponse object
     */
    public static ErrorResponse format(Exception exception, String errorContent)  {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setName("An error has occurred!");
        errorResponse.setExceptionType(exception.getClass().getSimpleName());
        errorResponse.setNumber(HttpStatus.SC_BAD_REQUEST);
        errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        if (!StringUtils.isNotBlank(errorContent)) {
            errorResponse.setMessage(exception.getMessage());
        } else {
            errorResponse.setMessage(errorContent);
        }

        errorResponse.setTrace(exception);
        return errorResponse;
    }

    /**
     * Gets formatted SugarCRM error message
     *
     * @param errorContent Error returned from SugarCRM
     * @return ErrorResponse object
     */
    public static ErrorResponse format(String errorContent) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setName("An error has occurred!");
        errorResponse.setNumber((int)HttpStatus.SC_BAD_REQUEST);
        errorResponse.setMessage(errorContent);
        return errorResponse;
    }

    @JsonProperty("status")
    private int statusCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("number")
    private int number = 0;

    @JsonProperty("description")
    private String message;

    @JsonProperty("exception_type")
    private String exceptionType;

    @JsonProperty("stack_trace")
    private String stackTrace;
}
