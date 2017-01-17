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

package com.sugaronrest.restapicalls.responses;

import com.sugaronrest.ErrorResponse;
import org.apache.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


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
        this.statusCode = HttpStatus.SC_OK;
    }

    /**
     * Gets or sets the time the API call was made
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets or sets the raw json request sent by SugarCRM Rest API
     */
    public String getJsonRawRequest() {
        return jsonRawRequest;
    }

    public void setJsonRawRequest(String value) {
        jsonRawRequest = value;
    }

    /**
     * Gets or sets the raw json response sent by SugarCRM Rest API
     */
    public String getJsonRawResponse() {
        return jsonRawResponse;
    }
    public void setJsonRawResponse(String value) {
        jsonRawResponse = value;
    }

    /**
     * Gets or sets the error object
     */
    public ErrorResponse getError() {
        return error;
    }
    public void setError(ErrorResponse value) {
        error = value;
    }

    /**
     * Gets or sets the http status code - either returned from the API call or assigned
     */
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int value) {
        statusCode = value;
    }

    private String time;
    private String jsonRawRequest;
    private String jsonRawResponse;
    private ErrorResponse error;
    private int statusCode;
}
