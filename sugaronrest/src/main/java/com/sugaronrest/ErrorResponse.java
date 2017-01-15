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

/**
 * Created by kolao_000 on 2016-12-22.
 */
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
     * Gets or sets the name of the returned error type
     */

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    /**
     * Gets or sets the number of the error returned
     */
    public int getNumber() {
        return number;
    }

    public void setNumber(int value) {
        number = value;
    }

    /**
     * Gets or sets the error message description
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        message = value;
    }

    /**
     * Gets or sets the error message description
     */
    public String getTrace() {
        return stackTrace;
    }

    public void setTrace(String value) {
        stackTrace = value;
    }

    public void setTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        stackTrace = stringWriter.toString();
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String value) {
        exceptionType = value;
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
            }

            errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
            return errorResponse;
        } catch (IOException e) {
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
     * Gets formatted error response exception or SugarCrm error message
     *
     * @param exception    Exception from SugarCrm REST API calls or .NET error
     * @param errorContent Error returned from SugarCrm
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
     * Gets formatted SugarCrm error message
     *
     * @param errorContent Error returned from SugarCrm
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
