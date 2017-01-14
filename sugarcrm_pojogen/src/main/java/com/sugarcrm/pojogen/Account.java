package com.sugarcrm.pojogen;

/**
 * Created by kolao_000 on 2016-12-29.
 */
public class Account {

    private String username;
    private String password;
    private String url = "localhost";
    private String port = "3306";
    private String databaseName = "SugarCrm";

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String value) {
        this.port = value;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public void setDatabaseName(String value) {
        this.databaseName = value;
    }
}
