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

package com.sugaronrest.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonObjectMapper {

    private static JsonObjectMapper singleton = new JsonObjectMapper( );
    private static ObjectMapper mapper;

    /**
     * Initializes a new instance of the Singleton JsonObjectMapper class.
     * A private Constructor prevents any other class from instantiating it.
     */
    private JsonObjectMapper() {
        mapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

         // Only one time
        Unirest.setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Gets the static intance of JsonObjectMapper Singleton object.
     */
    public static JsonObjectMapper getInstance() {
        return singleton;
    }

    /**
     * Gets the mapper object.
     */
    public static com.fasterxml.jackson.databind.ObjectMapper getMapper() {
       return mapper;
    }
}
