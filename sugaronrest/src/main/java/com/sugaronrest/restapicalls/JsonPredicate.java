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

import com.sugaronrest.QueryOperator;


public class JsonPredicate {

    /**
     * Initializes a new instance of the QueryPredicate class.
     *
     *  @param jsonName The property name. This can be a C# model property name or json property name.
     *  @param queryOperator The query operator.
     *  @param value The predicate value.
     *  @param fromValue The predicate from value.
     *  @param toValue The predicate to value.
     */
    public JsonPredicate(String jsonName, QueryOperator queryOperator, Object value, Object fromValue, Object toValue, boolean isNumeric)  {
        this.jsonName = jsonName;
        this.operator = queryOperator;
        this.value = value;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.isNumeric = isNumeric;
    }

    public String jsonName;
    public QueryOperator operator;
    public Object value;
    public Object fromValue;
    public Object toValue;
    public boolean isNumeric;
}


