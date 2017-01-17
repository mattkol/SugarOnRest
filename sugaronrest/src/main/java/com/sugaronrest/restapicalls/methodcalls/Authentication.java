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

package com.sugaronrest.restapicalls.methodcalls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sugaronrest.restapicalls.requests.LoginRequest;
import com.sugaronrest.ErrorResponse;
import com.sugaronrest.restapicalls.responses.LoginResponse;
import org.apache.http.HttpStatus;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;


public class Authentication {

    /**
     * Login to SugarCRM via REST API call.
     *
     *  @param loginRequest LoginRequest object.
     *  @return LoginResponse object.
     */
    public static LoginResponse login(LoginRequest loginRequest) throws Exception {

        LoginResponse loginResponse = new LoginResponse();

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String passwordHash = new BigInteger(1, md5.digest(loginRequest.password.getBytes()))
                    .toString(16);

            Map<String, String> userCredentials = new LinkedHashMap<String, String>();
            userCredentials.put("user_name", loginRequest.username);
            userCredentials.put("password", passwordHash);

            Map<String, Object> auth = new LinkedHashMap<String, Object>();
            auth.put("user_auth", userCredentials);
            auth.put("application_name", "RestClient");

            ObjectMapper mapper = new ObjectMapper();
            String jsonAuthData = mapper.writeValueAsString(auth);

            Map<String, Object> request = new LinkedHashMap<String, Object>();
            request.put("method", "login");
            request.put("input_type", "json");
            request.put("response_type", "json");
            request.put("rest_data", auth);

            String jsonRequest = mapper.writeValueAsString(request);

            request.put("rest_data", jsonAuthData);

            HttpResponse response = Unirest.post(loginRequest.url)
                    .fields(request)
                    .asString();

            String jsonResponse = response.getBody().toString();
            loginResponse.setJsonRawRequest(jsonRequest);
            loginResponse.setJsonRawResponse(jsonResponse);

            Map<String,Object> responseObject = mapper.readValue(jsonResponse, Map.class);
            if (responseObject.containsKey("id")) {
                loginResponse.sessionId = (responseObject.get("id").toString());
                loginResponse.setStatusCode(response.getStatus());
                loginResponse.setError(null);
            }
            else
            {
                ErrorResponse errorResponse = mapper.readValue(jsonResponse, ErrorResponse.class);
                errorResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                loginResponse.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                loginResponse.setError(errorResponse);
            }
        }
        catch (Exception exception) {
            ErrorResponse errorResponse = ErrorResponse.format(exception, exception.getMessage());
            loginResponse.setError(errorResponse);
        }

        return loginResponse;
    }

    /**
     * Logs out with the session identifier.
     *
     *  @param url REST API Url.
     *  @param sessionId Session identifier.
     */
    public static void logout(String url, String sessionId) {
        try {
            Map<String, String> session = new LinkedHashMap<String, String>();
            session.put("user_name", sessionId);

            ObjectMapper mapper = new ObjectMapper();
            String jsonSessionData = mapper.writeValueAsString(session);

            Map<String, Object> request = new LinkedHashMap<String, Object>();
            request.put("method", "logout");
            request.put("input_type", "json");
            request.put("response_type", "json");
            request.put("rest_data", jsonSessionData);

            Unirest.post(url)
                    .fields(request)
                    .asString();
        }
        catch (Exception exception) {
        }
    }
}
