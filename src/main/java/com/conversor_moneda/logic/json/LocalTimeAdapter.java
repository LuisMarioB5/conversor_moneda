package com.conversor_moneda.logic.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    // Formato para LocalTime
    private static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public JsonElement serialize(LocalTime time, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(LOCAL_TIME_FORMATTER.format(time));
    }

    @Override
    public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        return LocalTime.parse(jsonElement.getAsString(), LOCAL_TIME_FORMATTER);
    }
}
