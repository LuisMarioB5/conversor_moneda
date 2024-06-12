package com.conversor_moneda.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Adaptador para serializar y deserializar objetos {@link LocalDate} con Gson.
 * Este adaptador convierte {@link LocalDate} a su representación en cadena de texto y viceversa,
 * utilizando el formato "yyyy-MM-dd".
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    // Formato para LocalDate
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Serializa un objeto {@link LocalDate} a su representación en cadena de texto.
     *
     * @param date                     El objeto {@link LocalDate} a serializar.
     * @param type                     El tipo del objeto fuente.
     * @param jsonSerializationContext El contexto de la serialización.
     * @return Un elemento JSON que representa la fecha como una cadena en formato "yyyy-MM-dd".
     */
    @Override
    public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(LOCAL_DATE_FORMATTER.format(date));
    }

    /**
     * Deserializa un elemento JSON a un objeto {@link LocalDate}.
     *
     * @param jsonElement              El elemento JSON que contiene la fecha en formato de cadena "yyyy-MM-dd".
     * @param type                     El tipo del objeto destino.
     * @param jsonDeserializationContext El contexto de la deserialización.
     * @return El objeto {@link LocalDate} deserializado.
     * @throws JsonParseException si el elemento JSON no puede ser convertido a una fecha.
     */
    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        return LocalDate.parse(jsonElement.getAsString(), LOCAL_DATE_FORMATTER);
    }
}
