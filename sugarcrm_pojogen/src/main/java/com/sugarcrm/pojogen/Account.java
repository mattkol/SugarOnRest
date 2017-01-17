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

package com.sugarcrm.pojogen;

public class Account {

    /**
     * Gets or sets the username.
     */
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets or sets the password.
     */
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets or sets the url.
     */
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets or sets the port.
     */
    public String getPort() {
        return this.port;
    }
    public void setPort(String value) {
        this.port = value;
    }

    /**
     * Gets or sets the database name.
     */
    public String getDatabaseName() {
        return this.databaseName;
    }
    public void setDatabaseName(String value) {
        this.databaseName = value;
    }

    private String username;
    private String password;
    private String url = "localhost";
    private String port = "3306";
    private String databaseName = "SugarCrm";
}
