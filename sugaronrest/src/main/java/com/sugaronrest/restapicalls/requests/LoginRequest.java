package com.sugaronrest.restapicalls.requests;

/**
 * Created by kolao_000 on 2016-12-22.
 */
public class LoginRequest {

    public LoginRequest() {
    }

    public LoginRequest(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets or sets REST API Url
     */
    private String url = new String();
    public String getUrl() {
        return url;
    }

    public void setUrl(String value) {
        url = value;
    }

    /**
     * Gets or sets REST API SessionId
     */
    private String sessionId = new String();
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String value) {
        sessionId = value;
    }

    /**
     * Gets or sets REST API Username
     */
    private String username = new String();
    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username = value;
    }

    /**
     * Gets or sets REST API Password
     */
    private String password = new String();
    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password = value;
    }
}
