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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CustomDateDeserializer extends StdDeserializer<Date> {

    private static SimpleDateFormat formatter =
            new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    /**
     * Initializes a new instance of the CustomDateDeserializer class.
     */
    public CustomDateDeserializer() {
        this(null);
    }

    /**
     * Initializes a new instance of the CustomDateDeserializer class.
     *
     * @param clazz Class to deserialize.
     */
    public CustomDateDeserializer(Class<?> clazz) {
        super(clazz);
    }

    /**
     *  Deserialize date.
     *
     * @param jsonparser
     * @param context
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, JsonProcessingException {

        String date = jsonparser.getText();
        try {
            return formatter.parse(date);
        } catch (Exception e) {
        }

        return null;
    }
}