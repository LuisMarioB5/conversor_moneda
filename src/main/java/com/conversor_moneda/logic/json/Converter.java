package com.conversor_moneda.logic.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.LinkedHashMap;
import java.util.Map;

public class Converter {

    /**
     * Función que convierte un JSON en String a un Map, el JSON debe contener valores numéricos (enteros o decimales).
     *
     * @param json         Un String que contiene el JSON a convertir.
     * @param claveABuscar Un String opcional que especifica una clave en el JSON para extraer solo ese objeto.
     * @return Un Map<String, Float> que contiene los datos convertidos si todo está bien.
     * @throws IllegalArgumentException Si no se encuentra la clave especificada o si el JSON tiene un formato incorrecto.
     */
    public Map<String, Float> ConvertirJsonMap(String json, String claveABuscar) throws IllegalArgumentException {
        // Crear instancia de Gson
        Gson gson = new Gson();

        try {
            // Deserializar el JSON completo en un Map<String, Object>
            Map<String, Object> jsonData = gson.fromJson(json, Map.class);

            // Almacenar el objeto JSON según la claveABuscar
            Object objetoJson;
            if (claveABuscar != null) {
                objetoJson = jsonData.get(claveABuscar);
                if (objetoJson == null) {
                    throw new IllegalArgumentException("La clave '" + claveABuscar + "' no se encontró en el JSON.");
                }
            } else {
                objetoJson = jsonData;
            }

            // Verificar que objetoJson sea un Map
            if (objetoJson instanceof Map) {
                // Crear un nuevo Map para almacenar los valores convertidos
                Map<String, Float> mapaConvertido = new LinkedHashMap<>();

                // Verificar y convertir los valores a Float
                Map<String, Object> objetoJsonMap = (Map<String, Object>) objetoJson;
                for (Map.Entry<String, Object> entry : objetoJsonMap.entrySet()) {
                    String clave = entry.getKey();
                    Object valor = entry.getValue();
                    if (valor instanceof Number) {
                        // Convertir el valor a Float
                        mapaConvertido.put(clave, ((Number) valor).floatValue());
                    } else {
                        throw new IllegalArgumentException("El valor asociado a la clave '" + clave + "' no es numérico.");
                    }
                }
                return mapaConvertido;
            } else {
                throw new IllegalArgumentException("El JSON o la clave especificada no contiene un objeto JSON.");
            }
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Error de formato JSON: " + e.getMessage());
        }
    }

    /**
     * Función que muestra el contenido de un mapa.
     *
     * @param mapa Un Map<String, Float> cuyo contenido se desea mostrar.
     */
    public static void mostrarContenidoMapa(Map<String, Float> mapa) {
        // Verificar si el mapa no es nulo o está vacío
        if (mapa == null) {
            System.out.println("El mapa es nulo.");
            return;
        }
        if (mapa.isEmpty()) {
            System.out.println("El mapa está vacío.");
            return;
        }

        // Iterar sobre las entradas del mapa
        for (Map.Entry<String, Float> entry : mapa.entrySet()) {
            // Obtener clave y valor
            String clave = entry.getKey();
            Float valor = entry.getValue();

            // Imprimir clave y valor
            System.out.println('"' + clave + "\": " + valor);
        }
    }

    /**
     * Método principal que puede ser usado para pruebas estáticas o pruebas directas a la API.
     *
     * @param args Argumentos de línea de comando.
     */
    public static void main(String[] args) {
//        Variables para pruebas estáticas
        String jsonString = "{ \"convertionRate\": { \"USD\": 1, \"DOP\": 59.19 }, \"anotherConvertionRate\": { \"USD\": 1, \"DOP\": 59.19, \"INVALID\": \"success\" } }";
        String claveABuscar = "convertionRate";

//        Variables para pruebas directas a la API
//        String jsonString = Api.getResponse();
//        String claveABuscar = "conversion_rates";

        Converter converter = new Converter();
        mostrarContenidoMapa(converter.ConvertirJsonMap(jsonString, claveABuscar));
    }
}
