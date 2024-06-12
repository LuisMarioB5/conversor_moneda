package com.conversor_moneda.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Adaptador para serializar y deserializar objetos {@link LocalTime} con Gson.
 * Este adaptador convierte {@link LocalTime} a su representación en cadena de texto y viceversa,
 * utilizando el formato "HH:mm:ss".
 */
public class LocalTimeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
    // Formato para LocalTime
    private static final DateTimeFormatter LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Serializa un objeto {@link LocalTime} a su representación en cadena de texto.
     *
     * @param time                     El objeto {@link LocalTime} a serializar.
     * @param type                     El tipo del objeto fuente.
     * @param jsonSerializationContext El contexto de la serialización.
     * @return Un elemento JSON que representa la hora como una cadena en formato "HH:mm:ss".
     */
    @Override
    public JsonElement serialize(LocalTime time, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(LOCAL_TIME_FORMATTER.format(time));
    }

    /**
     * Deserializa un elemento JSON a un objeto {@link LocalTime}.
     *
     * @param jsonElement              El elemento JSON que contiene la hora en formato de cadena "HH:mm:ss".
     * @param type                     El tipo del objeto destino.
     * @param jsonDeserializationContext El contexto de la deserialización.
     * @return El objeto {@link LocalTime} deserializado.
     * @throws JsonParseException si el elemento JSON no puede ser convertido a una hora.
     */
    @Override
    public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        return LocalTime.parse(jsonElement.getAsString(), LOCAL_TIME_FORMATTER);
    }
}
