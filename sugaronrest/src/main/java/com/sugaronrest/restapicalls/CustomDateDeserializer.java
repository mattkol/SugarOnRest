package com.sugaronrest.restapicalls;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kolao_000 on 2016-12-31.
 */
public class CustomDateDeserializer extends StdDeserializer<Date> {

    private static SimpleDateFormat formatter =
            new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

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