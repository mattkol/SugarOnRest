package com.sugaronrest.restapicalls.responses;

import com.sugaronrest.ErrorResponse;
import org.apache.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kolao_000 on 2016-12-22.
 */
public class BaseResponse {

    static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Initializes a new instance of the BaseResponse class
     */
    public BaseResponse() {

        final SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date currenTime = Calendar.getInstance().getTime();
        time = format.format(currenTime) + " UTC";
        this.setError(new ErrorResponse());
    }

    /**
     * Gets or sets the time the API call was made
     */
    private String time = new String();
    public String getTime() {
        return time;
    }

    /**
     * Gets or sets the raw json request sent by SugarCrm Rest API
     */
    private String jsonRawRequest = new String();
    public String getJsonRawRequest() {
        return jsonRawRequest;
    }

    public void setJsonRawRequest(String value) {
        jsonRawRequest = value;
    }

    /**
     * Gets or sets the raw json response sent by SugarCrm Rest API
     */
    private String jsonRawResponse = new String();
    public String getJsonRawResponse() {
        return jsonRawResponse;
    }

    public void setJsonRawResponse(String value) {
        jsonRawResponse = value;
    }

    /**
     * Gets or sets the error object
     */
    private ErrorResponse error;
    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse value) {
        error = value;
    }

    /**
     * Gets or sets the http status code - either returned from the API call or assigned
     */
    private int statusCode = HttpStatus.SC_OK;
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int value) {
        statusCode = value;
    }

}
