package io.hexbit.core.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DefaultLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String input = p.getText().replace(" ", "T");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(input, formatter);
    }

}
