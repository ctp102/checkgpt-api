package io.hexbit.core.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StringStripJsonDeserializer extends JsonDeserializer<String> {

    /**
     * Json 데이터를 읽을 때마다 문자열 양옆 공백을 제거한다.
     */
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {

        String value = p.getValueAsString();

        if (value == null) {
            return null;
        }

        String valueStripped = value.strip();

        return !valueStripped.isEmpty() ? valueStripped : null;
    }

}