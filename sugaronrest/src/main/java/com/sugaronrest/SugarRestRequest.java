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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sugaronrest.restapicalls.ErrorCodes;
import com.sugaronrest.restapicalls.ModuleInfo;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;


public class SugarRestRequest {

    /**
     * Initializes a new instance of the SugarRestRequest class.
     */
    public SugarRestRequest() {
        this.setOptions(new Options());
        this.validationMessage = StringUtils.EMPTY;
    }

    /**
     * Initializes a new instance of the SugarRestRequest class.
     *
     *  @param moduleName The SugarCRM module name.
     */
    public SugarRestRequest(String moduleName) throws Exception {
        this.setModuleName(moduleName);
        this.setOptions(new Options());
        this.validationMessage = StringUtils.EMPTY;
    }

    /**
     * Initializes a new instance of the SugarRestRequest class.
     *
     *  @param moduleType The SugarCRM module Java type.
     */
    public SugarRestRequest(Type moduleType) {
        this.setModuleType(moduleType);
        this.setOptions(new Options());
        this.validationMessage = StringUtils.EMPTY;
    }

    /**
     * Initializes a new instance of the SugarRestRequest class.
     *
     *  @param requestType The request type.
     */
    public SugarRestRequest(RequestType requestType) {
        this.setRequestType(requestType);
        this.setOptions(new Options());
        this.validationMessage = StringUtils.EMPTY;
    }

    /**
     * Initializes a new instance of the SugarRestRequest class.
     *
     *  @param moduleName The SugarCRM module name.
     *  @param requestType The request type.
     */
    public SugarRestRequest(String moduleName, RequestType requestType) {
        this.setModuleName(moduleName);
        this.setRequestType(requestType);
        this.setOptions(new Options());
        this.validationMessage = StringUtils.EMPTY;
    }

    /**
     * Initializes a new instance of the SugarRestRequest class.
     *
     *  @param moduleType The SugarCRM module name.
     *  @param requestType The request type.
     */
    public SugarRestRequest(Type moduleType, RequestType requestType) {
        this.setModuleType(moduleType);
        this.setRequestType(requestType);
        this.setOptions(new Options());
        this.validationMessage = StringUtils.EMPTY;
    }

    /**
     * Gets or sets SugarCRM REST API Url.
     */
    public String getUrl() {
        return url;
    }
    public void setUrl(String value) {
        url = value;
    }

    /**
     * Gets or sets REST API Username.
     */
    public String getUsername() {
        return username;
    }
    public void setUsername(String value) {
        username = value;
    }

    /**
     * Gets or sets REST API Password.
     */
    public String getPassword() {
        return password;
    }
    public void setPassword(String value) {
        password = value;
    }

    /**
     * Gets or sets SugarCRM module name
     */
    public String getModuleName() {
        return moduleName;
    }
    public void setModuleName(String value) {
        moduleName = value;
    }

    /**
     * Gets or sets SugarCRM module name
     */
    public Type getModuleType() {
        return moduleType;
    }
    public void setModuleType(Type value) {
        moduleType = value;
    }

    /**
     * Gets or sets SugarCRM module name.
     */
    public RequestType getRequestType() {
        return requestType;
    }
    public void setRequestType(RequestType value) {
        requestType = value;
    }

    /**
     * Gets or sets request parameter - can be identifier, entity or entities data.
     * Parameter type set for the following request type:
     * ReadById - Identifier (Id)
     * BulkRead - null (Set options if needed.)
     * PagedRead - null (Set options if needed.)
     * Create - Entity
     * BulkCreate - Entity collection
     * Update - Entity
     * BulkUpdate - Entity collection
     * Delete - Identifier (Id)
     * LinkedReadById - Identifier (Id) (Linked option value must be set.)
     * LinkedBulkRead - null (Linked option value must be set.)
     * AllModulesRead - null
     */
    public Object getParameter() {
        return parameter;
    }
    public void setParameter(Object value) {
        parameter = value;
    }

    /**
     * Gets or sets options object.
     */
    public Options getOptions() {
        return options;
    }
    public void setOptions(Options value) {
        options = value;
    }

    /**
     * Gets the validation message.
     */
    public String getValidationMessage() {
        return this.validationMessage;
    }

    /**
     * Gets a value indicating whether the request is valid.
     *
     *  @return True or false
     */
    @JsonIgnore
    public boolean getIsValid() throws IOException, ClassNotFoundException {
        StringBuilder builder = new StringBuilder();

        if (!StringUtils.isNotBlank(this.getUrl()))
        {
            builder.append(ErrorCodes.UrlInvalid);
            builder.append("\n");
        }

        if (!StringUtils.isNotBlank(this.getUsername()))
        {
            builder.append(ErrorCodes.UsernameInvalid);
            builder.append("\n");
        }

        if (!StringUtils.isNotBlank(this.getPassword()))
        {
            builder.append(ErrorCodes.PasswordInvalid);
            builder.append("\n");
        }

        validateModuleNameOrType(builder);

        switch(getRequestType())
        {
            case ReadById:
            case Delete:
                if (this.getParameter() == null) {
                    builder.append(ErrorCodes.IdInvalid);
                    builder.append("\n");
                }

                break;
            case Create:
            case BulkCreate:
            case BulkUpdate:
                if (this.getParameter() == null) {
                    builder.append(ErrorCodes.DataInvalid);
                    builder.append("\n");
                }

                break;
            case LinkedReadById:
                if (this.getParameter() == null) {
                    builder.append(ErrorCodes.IdInvalid);
                    builder.append("\n");
                }

                if ((getOptions().getLinkedModules() == null) || (getOptions().getLinkedModules().size() == 0)) {
                    builder.append(ErrorCodes.LinkedFieldsInfoMissing);
                    builder.append("\n");
                }

                break;
            case LinkedBulkRead:
                if ((getOptions().getLinkedModules() == null) || (getOptions().getLinkedModules().size() == 0)) {
                    builder.append(ErrorCodes.LinkedFieldsInfoMissing);
                    builder.append("\n");
                }

                break;
        }

        this.validationMessage = builder.toString();
        return !StringUtils.isNotBlank(this.validationMessage);
    }

    /**
     * Validates module name or module type.
     *
     * @param builder Cummulative error builder reference.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @JsonIgnore
    private void validateModuleNameOrType(StringBuilder builder) throws IOException, ClassNotFoundException {
        if (getRequestType() == RequestType.AllModulesRead) {
            return;
        }

        moduleName = getModuleName();
        if (StringUtils.isNotBlank(this.getModuleName())) {
            return;
        }

        Type type = getModuleType();
        if ((type == null) && !StringUtils.isNotBlank(moduleName) ) {
            builder.append(ErrorCodes.ModuleNameOrTypeInvalid);
            builder.append("\n");
        }

        if (type != null ) {
            String moduleName = ModuleInfo.getClassName(type);
            if (!StringUtils.isNotBlank(moduleName)) {
                builder.append(ErrorCodes.ModuleTypeInvalid);
                builder.append("\n");
                return;
            }

            setModuleName(moduleName);
        }
    }

    private String validationMessage;
    private String url;
    private String username;
    private String password;
    private String moduleName;
    private Type moduleType;
    private RequestType requestType;
    private Object parameter;
    private Options options;
}


