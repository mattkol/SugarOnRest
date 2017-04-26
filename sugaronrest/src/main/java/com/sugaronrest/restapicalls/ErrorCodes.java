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

package com.sugaronrest.restapicalls;


public class ErrorCodes {

    /**
     * Invalid url error code
     */
    public static String UrlInvalid = "Url is not valid or not provided.";

    /**
     * Invalid username error code
     */
    public static String UsernameInvalid = "Username is not valid or not provided.";

    /**
     * Invalid password error code
     */
    public static String PasswordInvalid = "Password is not valid or not provided.";

    /**
     * Invalid entity type error code
     */
    public static String ModuleNameOrTypeInvalid = "Module name is provided or module type provided is not a valid SugarCRM module type.";

    /**
     * Invalid entity type error code
     */
    public static String ModuleTypeInvalid = "Module type provided is not a valid SugarCRM module type.";

    /**
     * Invalid identifier error code
     */
    public static String IdInvalid = "Identifier is not valid or not provided.";

    /**
     * Invalid entity or entities data error code
     */
    public static String DataInvalid = "Entity or entities data object provided is not valid.";

    /**
     * Invalid linked field information missing.
     */
    public static String LinkedFieldsInfoMissing = "Entity or entities data object provided is not valid.";
}


