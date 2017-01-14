package com.sugaronrest.restapicalls;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kolao_000 on 2016-12-31.
 */
public class CustomDateSerializer extends StdSerializer<Date> {

    private static SimpleDateFormat formatter =
            new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {

        try {
            gen.writeString(formatter.format(value));
        } catch (Exception e) {
            gen.writeString("");
        }
    }
}